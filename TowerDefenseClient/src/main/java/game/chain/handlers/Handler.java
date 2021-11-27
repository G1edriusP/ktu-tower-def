package game.chain.handlers;

import game.chain.requests.Request;

public abstract class Handler {
    protected Handler next;

    public abstract void handle(Request request);

    protected void handleNext(Request request) {
        if (this.next != null)
            this.next.handle(request);
    }

    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }


}
