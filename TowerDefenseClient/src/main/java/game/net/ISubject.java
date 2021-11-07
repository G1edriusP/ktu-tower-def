package game.net;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISubject {
    /**
     * send sends this Object over to the Server.
     */
    void send();
    void sendDelete();

    void register();
    void receive(String json) throws JsonProcessingException;

    UUID getUUID();

    /**
     * getType retrieves the object type. Needed to identify what kind of object
     * we are sending to a server (or retrieving in that matter).
     *
     * @return Object type literal.
     */
    String getType();
}
