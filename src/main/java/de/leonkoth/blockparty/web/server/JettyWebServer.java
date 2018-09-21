package de.leonkoth.blockparty.web.server;

import de.leonkoth.blockparty.web.server.handler.MusicPlayerServlet;
import de.leonkoth.blockparty.web.server.handler.NameServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;

import java.io.IOException;


/**
 * Created by Leon on 19.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */

public class JettyWebServer implements WebServer{

    private Server server;

    private int port;

    public static void main(String[] args) throws Exception {

        JettyWebServer jws = new JettyWebServer();
        jws.start();

    }

    public JettyWebServer()
    {
        this.port = 8080;
    }

    public JettyWebServer(int port)
    {
        this.port = port;
    }

    @Override
    public void send(String ip, String arena, String song, String play) {

    }

    @Override
    public void start() throws IOException {
        Log.setLog(new JettyLogger());

        this.server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        server.setStopAtShutdown(true);

        ServletContextHandler sch = new ServletContextHandler();
        ServletHolder holder = new ServletHolder("default", new DefaultServlet());
        holder.setInitParameter("resourceBase", "./src/main/resources/web/");
        holder.setInitParameter("directoriesListed", "true");

        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setHandler(sch);

        sch.addServlet(holder, "/*");
        sch.addServlet(NameServlet.class, "/NameRequest");
        sch.addServlet(MusicPlayerServlet.class, "/Musicplayer");

        server.setHandler(sessionHandler);

        try
        {
            if (this.server != null)
            {
                this.server.start();
                this.server.join();
            }
        }
        catch (Exception e)
        {
        }
    }

    @Override
    public void stop() throws Exception {
        this.server.stop();
    }
}
