package game.chain.requests;

import game.net.ISubject;

import java.util.UUID;

public class RemoveSubjectRequest implements Request {
    private ISubject subject;
    private UUID uuid;

    public RemoveSubjectRequest(ISubject subject) {
        this.subject = subject;
    }

    public RemoveSubjectRequest(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        if (uuid == null)
            return subject.getUUID();
        return uuid;
    }
}
