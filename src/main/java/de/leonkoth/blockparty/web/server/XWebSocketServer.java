package de.leonkoth.blockparty.web.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Leon on 20.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */


public class XWebSocketServer implements WebServer {

    private static ArrayList<Socket> connectedClients = new ArrayList<>();
    private int port;
    private boolean ssl;
    private InetAddress bindAddress;
    private Thread serverThread;
    private ServerSocket server;

    public XWebSocketServer(int port) {
        this(port, false, null);
    }

    public XWebSocketServer(int port, boolean ssl) {
        this(port, ssl, null);
    }

    public XWebSocketServer(int port, boolean ssl, InetAddress bindAddress) {
        this.port = port;
        this.ssl = ssl;
        this.bindAddress = bindAddress;
    }

    public void send(String ip, String arena, String song, String play) {
        for (Socket clients : connectedClients) {
            if (clients.getInetAddress().getHostAddress().equalsIgnoreCase(ip)) {
                if (clients.isConnected()) {
                    System.out.println("Sending: " + arena + ";" + song + ";" + play);
                    new ClientThread(clients, arena, song, play).start();
                }
            }
        }
    }

    public void start() throws IOException {

        if (bindAddress == null) {
            server = new ServerSocket(port);
        } else {
            server = new ServerSocket(port, -1, bindAddress);
        }

        System.out.println("Server started on " + server.getInetAddress().getHostAddress() + ":" + port);

        serverThread = new Thread(() -> {
            while (true) {
                try {
                    Socket client = server.accept();
                    connectedClients.add(client);
                    new ServerThread(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        serverThread.start();

    }

    public void stop() throws InterruptedException {
        serverThread.join();
    }

    public class ServerThread extends Thread {

        private Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            System.out.println("Client connected! " + socket.getInetAddress().getHostAddress());
        }

    }

    public class ClientThread extends Thread {

        private Socket socket;
        private String arena, song, play;

        public ClientThread(Socket socket, String arena, String song, String play) {
            this.socket = socket;
            this.arena = arena;
            this.song = song;
            this.play = play;
        }

        public void run() {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()))) {
                writer.write(arena + ";" + song + ";" + play);
                writer.flush();
            } catch (IOException e) {
                System.out.println("Connection to " + socket.getInetAddress().getHostAddress() + " is closed!");
            }
        }

    }

}
