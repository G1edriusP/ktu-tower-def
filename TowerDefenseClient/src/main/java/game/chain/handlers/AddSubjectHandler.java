package game.chain.handlers;

import game.chain.requests.AddSubjectRequest;
import game.chain.requests.Request;
import game.net.ISubject;
import game.net.Session;

import java.util.Map;
import java.util.UUID;

public class AddSubjectHandler extends Handler {

    @Override
    public void handle(Request request) {
        if (!(request instanceof AddSubjectRequest)) {
            this.handleNext(request);
            return;
        }

        ISubject subject = ((AddSubjectRequest) request)
                .getSubject();

        Map<UUID, ISubject> subjects = Session.getInstance()
                .getObjects();

        if (subjects.containsKey(subject.getUUID())) {
            System.out.println("trying to register existing: " + subject.getUUID());
            return;
        }
        subjects.put(subject.getUUID(), subject);
    }
}
