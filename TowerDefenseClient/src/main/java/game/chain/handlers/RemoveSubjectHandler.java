package game.chain.handlers;

import game.chain.requests.RemoveSubjectRequest;
import game.chain.requests.Request;
import game.net.ISubject;
import game.net.Session;

import java.util.Map;
import java.util.UUID;

public class RemoveSubjectHandler extends Handler {

    @Override
    public void handle(Request request) {
        if (!(request instanceof RemoveSubjectRequest)) {
            this.handleNext(request);
            return;
        }

        UUID uuid = ((RemoveSubjectRequest) request)
                .getUUID();

        Map<UUID, ISubject> subjects = Session.getInstance()
                .getObjects();

        ISubject subject = subjects.get(uuid);
        if (subject == null)
            return;
        subjects.remove(uuid);
        subject.sendDelete();
    }
}
