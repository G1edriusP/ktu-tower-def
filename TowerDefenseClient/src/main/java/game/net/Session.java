package game.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.chain.ArmyManager;
import game.chain.requests.AddSubjectRequest;
import game.chain.requests.RemoveSubjectRequest;
import game.entity.blue.*;
import game.entity.red.*;
import game.interpreter.*;
import game.prototype.DirtTile;
import game.prototype.GrassTile;
import game.prototype.SandTile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Session is a Singleton that is used for communications with the Server.
 */
public class Session extends WebSocketClient {
    /**
     * instance is a single instance of the Session.
     */
    private static Session instance = null;

    private boolean red = false;

    private BooleanProperty started;

    /**
     * objects holds all the objects that are shared with a Server by their
     * UUID.
     */
    private final ObservableMap<UUID, ISubject> objects;

    public ObservableMap<UUID, ISubject> getObjects() {
        return this.objects;
    }

    private Session() throws URISyntaxException {
        super(new URI("ws://localhost:8887"));

        objects = FXCollections.observableMap(new HashMap<>());
        started = new SimpleBooleanProperty(false);

        try {
            connectBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Session getInstance() {
        if (instance == null) {
            try {
                instance = new Session();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public boolean isRed() {
        return this.red;
    }

    public boolean isBlue() {
        return !this.red;
    }

    /**
     * register registers a new object about which to listen from the Server.
     *
     * @param object Object to register.
     */
    public void register(ISubject object) {
        System.out.println("register: " + object.getUUID());
        ArmyManager armyManager = ArmyManager.getInstance();
        armyManager.add(new AddSubjectRequest(object));
        armyManager.handle();
    }

    public void unregister(UUID uuid) {
        ArmyManager armyManager = ArmyManager.getInstance();
        armyManager.add(new RemoveSubjectRequest(uuid));
        armyManager.handle();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("connection opened"); // Good.
    }

    @Override
    public void onMessage(String message)  {
        // Try extracting UUID from the message.
        try {
            ObjectNode node = new ObjectMapper().
                    readValue(message, ObjectNode.class);

            Expression expr = Parser.parseNode(node);
            if (expr == null) {
                return;
            }

            if (!(expr instanceof UUIDExpression)) {
                System.out.println(expr.execute());
                return;
            }

            UUID uuid = UUID.fromString(expr.execute());
            synchronized (this) {
                ISubject object;
                ArmyManager armyManager = ArmyManager.getInstance();

                armyManager.lock();
                try {
                    object = this.objects.get(uuid);
                } catch (Exception e) {
                    throw e;
                } finally {
                    armyManager.unlock();
                }

                if (object != null) {
                    // Update the object
                    object.receive(message);
                    return;
                }
                // New object
                // Check for a recognizable type
                switch (node.get("type").asText()) {
                    case "blue-ghost":
                        object = new BlueGhost(uuid);
                        break;
                    case "blue-archer":
                        object = new BlueArcher(uuid);
                        break;
                    case "blue-skeleton":
                        object = new BlueSkeleton(uuid);
                        break;
                    case "blue-barbarian":
                        object = new BlueBarbarian(uuid);
                        break;
                    case "blue-tower":
                        object = new BlueTower(uuid);
                        break;

                    case "red-ghost":
                        object = new RedGhost(uuid);
                        break;
                    case "red-archer":
                        object = new RedArcher(uuid);
                        break;
                    case "red-skeleton":
                        object = new RedSkeleton(uuid);
                        break;
                    case "red-barbarian":
                        object = new RedBarbarian(uuid);
                        break;
                    case "red-tower":
                        object = new RedTower(uuid);
                        break;

                    default:
                        System.out.println("unregistered unknown object: " +
                                uuid);
                        return;
                }
                object.receive(message);
                object.register();
            }

        } catch (JsonProcessingException e) {
            System.out.println("exception on msg: "+ message);
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("connection closed");
        System.exit(0);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("an error occurred: " + ex);
        // System.exit(1);
    }

    public void setTeam(boolean red) {
        this.red = red;
    }

    public BooleanProperty getStarted() {
        return this.started;
    }
}
