package game.chain.requests;

import game.net.ISubject;

public class AddSubjectRequest implements Request {
    private ISubject subject;

    public AddSubjectRequest(ISubject subject) {
        this.subject = subject;
    }

    public ISubject getSubject() {
        return this.subject;
    }
}
