package game.net;

import java.net.URISyntaxException;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public interface IObserver {
    /**
     * send sends this Object over to the Server.
     */
    void send();

    void register() throws URISyntaxException, InterruptedException, JsonProcessingException;

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
