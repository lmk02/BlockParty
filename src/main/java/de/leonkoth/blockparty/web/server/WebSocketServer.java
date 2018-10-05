package de.leonkoth.blockparty.web.server;

import de.leonkoth.blockparty.BlockParty;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Leon on 16.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public class WebSocketServer extends org.java_websocket.server.WebSocketServer implements WebServer {

    private Set<WebSocket> socket;
    private BlockParty blockParty;

    public WebSocketServer(InetSocketAddress address, BlockParty blockParty) {
        super(address);
        socket = new HashSet<>();
        this.blockParty = blockParty;
    }

    @Override
    public void onOpen(WebSocket socket, ClientHandshake handshake) {
        this.socket.add(socket);

        if (BlockParty.DEBUG)
            System.out.println("[BlockParty] New connection from " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket socket, int code, String reason, boolean remote) {
        this.socket.remove(socket);

        if (BlockParty.DEBUG)
            System.out.println("[BlockParty] Closed connection to " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket socket, String message) {
        if (BlockParty.DEBUG)
            System.out.println("[BlockParty] Message from client: " + message);

        for (WebSocket sock : this.socket) {
            sock.send(message);
        }
    }

    @Override
    public void onError(WebSocket socket, Exception e) {
        if (socket != null) {
            this.socket.remove(socket);

            if (BlockParty.DEBUG)
                System.err.println("[BlockParty] ERROR from " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
        }

    }

    @Override
    public void onStart() {
        if (BlockParty.DEBUG) {
            System.out.println("[BlockParty] Started music server on " + this.getAddress().getHostString() + ":" + this.getAddress().getPort());
        }


        this.blockParty.logStartMessage(true);
    }

    public void send(String ip, String arena, String song, String play) {
        for (WebSocket ws : this.getConnections()) {
            if (ws.getRemoteSocketAddress().getAddress().getHostAddress().equalsIgnoreCase(ip)) {
                if (ws.isOpen()) {
                    ws.send(arena + ";" + song + ";" + play);
                }
            }
        }
    }
}
