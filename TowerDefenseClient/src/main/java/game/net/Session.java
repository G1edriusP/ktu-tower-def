package game.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.entity.blue.*;
import game.entity.red.*;
import game.prototype.DirtTile;
import game.prototype.GrassTile;
import game.prototype.SandTile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
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

        objects = FXCollections.synchronizedObservableMap(FXCollections.observableMap(new HashMap<>()));
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
    public void register(ISubject object)  {
        if (objects.containsKey(object.getUUID())) {
            System.out.println("trying to register existing: " + object.getUUID());
            return;
        }
        objects.put(object.getUUID(), object);
        System.out.println("register: " + object.getUUID());
    }

    public void unregister(UUID uuid) {
        objects.remove(uuid);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("connection opened"); // Good.
    }

    @Override
    public void onMessage(String message)  {
        // Try extracting UUID from the message.
        try {
            ObjectNode node = new ObjectMapper().readValue(message, ObjectNode.class);

            if (node.has("action")) {
                switch (node.get("action").asText("")) {
                    case "sessionStart":
                        this.red = node.get("red").asBoolean();
                        this.started.setValue(true);
                        System.out.println("start " + (this.red?"red":"blue"));
                        break;

                    case "winner":
                        this.red = node.get("red").asBoolean();
                        this.started.setValue(false);
                        System.out.println("stop");
                        break;

                    case "delete":
                        UUID uuid = UUID.fromString(node.get("uuid").asText());
                        unregister(uuid);
                        break;
                }
                return;
            }

            if (!node.has("uuid")) {
                return;
            }
            UUID uuid = UUID.fromString(node.get("uuid").asText());

            synchronized (this) {
                ISubject object = this.objects.get(uuid);
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

                    case "grass-tile":
                        object = new GrassTile(uuid);
                        break;
                    case "dirt-tile":
                        object = new DirtTile(uuid);
                        break;
                    case "sand-tile":
                        object = new SandTile(uuid);
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

    public ObservableBooleanValue getStarted() {
        return this.started;
    }
}
