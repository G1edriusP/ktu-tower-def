package game.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.entity.Soldier;
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

    /**
     * objects holds all the objects that are shared with a Server by their
     * UUID.
     */
    private final Map<UUID, Serverside> objects;

    public Map<UUID, Serverside> getObjects() {
        return this.objects;
    }

    private Session() throws InterruptedException, URISyntaxException {
        super(new URI("ws://localhost:8887"));
        objects = new HashMap<>();
        connectBlocking();
    }

    public static synchronized Session getInstance() throws URISyntaxException,
            InterruptedException {
        if (instance == null)
            instance = new Session();
        return instance;
    }

    /**
     * register registers a new object about which to listen from the Server.
     *
     * @param object Object to register.
     */
    public void register(Serverside object) throws URISyntaxException,
            InterruptedException, JsonProcessingException {
        if (objects.containsKey(object.uuid)) {
            System.out.println("trying to register existing: " + object.uuid);
            return;
        }
        objects.put(object.uuid, object);
        System.out.println("register: " + object.uuid);
    }

    /**
     * unregister unregisters the object.
     * <p>
     * TODO: deletion is lacking ATM. Deleted objects may be deleted locally but
     * will re-appear the second they are updated by the Server.
     *
     * @param object Object to unregister.
     */
    public void unregister(Serverside object) {
        objects.remove(object.uuid);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("connection opened"); // Good.
    }

    @Override
    public void onMessage(String message) {
        try {
            // Try extracting UUID from the message.
            ObjectNode node = new ObjectMapper().readValue(message,
                    ObjectNode.class);
            UUID uuid = UUID.fromString(node.get("uuid").asText());

            synchronized (this) {
                Serverside object = this.objects.get(uuid);
                if (object == null) {
                    // New object
                    // Or a deleted one.. and we are re-creating it..
                    // TODO: handle deletion

                    // Check for a recognizable type
                    switch (node.get("type").asText()) {
                        case "troop":
                            Soldier troop = new Soldier(uuid);
                            register(troop);
                            object = troop;
                            break;

                        default:
                            System.out.println("unregistered unknown object: " +
                                    uuid);
                            return;
                    }
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
        System.exit(1);
    }
}
