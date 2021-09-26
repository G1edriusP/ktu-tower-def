package net;

import java.net.URISyntaxException;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Serverside {
    /**
     * uuid is used to safely identify each Object uniquely.
     */
    protected UUID uuid;

    public Serverside() {
        this(UUID.randomUUID());
    }

    public Serverside(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * send sends this Object over to the Server.
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    public void send() throws URISyntaxException, InterruptedException,
            JsonProcessingException {
        Session.getInstance().send(new ObjectMapper().writeValueAsString(this));
    }

    /**
     * receive parses the message for us and updates this Object.
     * FIXME: I'm not actually certain whether it updates, needs testing to
     * confirm.
     * @param json
     * @throws JsonProcessingException
     */
    public void receive(String json) throws JsonProcessingException {
        new ObjectMapper().readValue(json, this.getClass());
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * getType retrieves the object type. Needed to identify what kind of object
     * we are sending to a server (or retrieving in that matter).
     * @return Object type literal.
     */
    abstract public String getType();
}
