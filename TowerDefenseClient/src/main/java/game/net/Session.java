package game.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.entity.blue.*;
import game.entity.red.*;
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

    private boolean started;

    /**
     * objects holds all the objects that are shared with a Server by their
     * UUID.
     */
    private final Map<UUID, IObserver> objects;

    public Map<UUID, IObserver> getObjects() {
        return this.objects;
    }

    private Session() throws InterruptedException, URISyntaxException {
        super(new URI("ws://localhost:8887"));
        objects = new HashMap<>();
        started = false;
        connectBlocking();
    }

    public static synchronized Session getInstance() throws URISyntaxException,
            InterruptedException {
        if (instance == null)
            instance = new Session();
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
    public void register(IObserver object) throws URISyntaxException,
            InterruptedException, JsonProcessingException {
        if (objects.containsKey(object.getUUID())) {
            System.out.println("trying to register existing: " + object.getUUID());
            return;
        }
        objects.put(object.getUUID(), object);
        System.out.println("register: " + object.getUUID());
    }

    /**
     * unregister unregisters the object.
     * <p>
     * TODO: deletion is lacking ATM. Deleted objects may be deleted locally but
     * will re-appear the second they are updated by the Server.
     *
     * @param object Object to unregister.
     */
    public void unregister(IObserver object) {
        objects.remove(object.getUUID());
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("connection opened"); // Good.
    }

    @Override
    public void onMessage(String message) {
        try {
            // Try extracting UUID from the message.
            ObjectNode node = new ObjectMapper().readValue(message, ObjectNode.class);

            if (node.has("action")) {
                switch (node.get("action").asText("")) {
                case "sessionStart":
                    this.red = node.get("red").asBoolean();
                    this.started = true;
                    System.out.println("start");
                    break;
                }
                return;
            }

            if (!node.has("uuid")) {
                return;
            }
            UUID uuid = UUID.fromString(node.get("uuid").asText());

            synchronized (this) {
                IObserver object = this.objects.get(uuid);
                if (object == null) {
                    // New object
                    // Or a deleted one.. and we are re-creating it..
                    // TODO: handle deletion

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
                    object.register();
                }
                // Update the object
                object.receive(message);
            }
        } catch (JsonProcessingException | URISyntaxException |
                InterruptedException e) {
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

    public boolean isStarted() {
        return this.started;
    }
}
