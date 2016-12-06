package handlers;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by goutham on 06/12/16.
 */

@WebSocket
public class GraphWSHandler {
    private static Set<Session> activeSessions = new HashSet<Session>();

    @OnWebSocketConnect
    public void onConnect(Session s) throws Exception {
        activeSessions.add(s);
        broadCast("Somebody Joined\n");
    }

    @OnWebSocketClose
    public void  onClose(Session s, int statusCode, String reason) {
        activeSessions.remove(s);
        broadCast("Somebody Left\n");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        System.out.println("Got Message: " + message);

        broadCast("Message: " + message);
    }

    public static void broadCast(String message) {
        activeSessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
