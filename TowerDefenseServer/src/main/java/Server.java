import java.lang.invoke.SerializedLambda;
import java.net.InetSocketAddress;
import java.util.*;

import net.Session;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class Server extends WebSocketServer {
    /**
     * sessionList holds all currently active sessions (includes sessions that
     * are not full, i.e., 1 out of 2 are connected).
     * Main motivation was to keep track and provide easy way to search for
     * sessions that are not full.
     */
    protected List<Session> sessionList;

    /**
     * sessionMap holds WebSockets, pointing to their assigned session for easy
     * retrieval.
     */
    protected Map<WebSocket, Session> sessionMap;

    public Server(InetSocketAddress address) {
        super(address);
        sessionList = new ArrayList<>();
        sessionMap = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("connection: new: " + conn.getRemoteSocketAddress());
        synchronized (this) {
            // Try to find a session that has only 1 connection (aka not full)
            for (Session session : sessionList) {
                if (session.count() >= 2)
                    continue;

                // Add the connection
                session.add(conn);
                sessionMap.put(conn, session);

                conn.send("{\"action\":\"sessionStart\",\"red\":true}");
                session.getOther(conn).send("{\"action\":\"sessionStart\",\"red\":false}");
                return;
            }

            // Else create a new session with 1 connection
            System.out.println("red");
            Session session = new Session(conn);
            sessionList.add(session);
            sessionMap.put(conn, session);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason,
                        boolean remote) {
        System.out.println("connection: close: " +
                conn.getRemoteSocketAddress());
        synchronized (this) {
            // Retrieve associated session
            Session session = sessionMap.get(conn);
            if (session == null)
                return; // No idea if this can happen but better safe than sorry

            // Remove associations
            sessionMap.remove(session.getOther(conn));
            sessionMap.remove(conn);
            sessionList.remove(session);

            // Close the connections
            session.close();
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(conn.getRemoteSocketAddress() + ": " + message);

        // Forward the message to the other connection
        Session session = sessionMap.get(conn);
        if (session == null) {
            System.out.println("session is null!!");
            return; // Should never occur...
        }

        WebSocket other = session.getOther(conn);
        if (other == null) {
            return; // Only 1 connection on this session and it's us
        }

        other.send(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occurred on connection " +
                conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887;

        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
    }
}
