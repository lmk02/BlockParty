package de.leonkoth.blockparty.web.server;

import de.leonkoth.blockparty.web.server.handler.NameServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;


/**
 * Created by Leon on 19.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */

public class JettyWebServer {

    private Server server;

    public static void main(String[] args) throws Exception {

        JettyWebServer jws = new JettyWebServer();
        jws.loadServer();
        jws.startServer();

        /*ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new NameServlet()), "/player");*/

    }

    public void loadServer()
    {
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

        sch.addServlet(holder, "/*");
        sch.addServlet(NameServlet.class, "/Musicplayer");

        server.setHandler(sch);
    }



    public void startServer()
    {
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

}
