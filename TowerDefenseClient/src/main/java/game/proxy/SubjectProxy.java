package game.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.net.ISubject;

import java.util.UUID;

public class SubjectProxy implements ISubject {
    private final ISubject realSubject;

    public SubjectProxy(ISubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void send() {
        System.out.println("Send | UUID : " + realSubject.getUUID());
        realSubject.send();
    }

    @Override
    public void sendDelete() {
        System.out.println("Send delete | UUID : " + realSubject.getUUID());
        realSubject.sendDelete();
    }

    @Override
    public void register() {
        System.out.println("Register | UUID : " + realSubject.getUUID());
        realSubject.register();
    }

    @Override
    public void receive(String json) throws JsonProcessingException {
        System.out.println("Receive : " + json + " | UUID : " + realSubject.getUUID());
        realSubject.receive(json);
    }

    @Override
    public UUID getUUID() {
        return realSubject.getUUID();
    }

    @Override
    public String getType() {
        return realSubject.getType();
    }
}
