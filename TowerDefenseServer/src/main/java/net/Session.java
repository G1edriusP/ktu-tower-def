package net;

import org.java_websocket.WebSocket;

/**
 * Session represents a two player connection session.
 */
public class Session {
    private WebSocket a;
    private WebSocket b;

    public Session(WebSocket first) {
        this.a = first;
        this.b = null;
    }

    public Session(WebSocket first, WebSocket second) {
        this.a = first;
        this.b = second;
    }

    /**
     * getOther retrieves other associated connection.
     *
     * @param socket This connection.
     * @return Other associated connection.
     */
    public WebSocket getOther(WebSocket socket) {
        if (this.a == socket)
            return this.b;
        return this.a;
    }

    /**
     * add adds a socket to a Session. If session is full, add is no-op.
     *
     * @param socket WebSocket to add.
     */
    public void add(WebSocket socket) {
        if (this.a == null) {
            this.a = socket;
            return;
        }
        if (this.b == null) {
            this.b = socket;
            return;
        }
        return;
    }

    /**
     * @return number of associated connections.
     */
    public int count() {
        int c = 0;
        if (a != null) c++;
        if (b != null) c++;
        return c;
    }

    /**
     * close closes associated WebSocket connections.
     */
    public void close() {
        if (this.a != null) this.a.close();
        if (this.b != null) this.b.close();
    }
}
