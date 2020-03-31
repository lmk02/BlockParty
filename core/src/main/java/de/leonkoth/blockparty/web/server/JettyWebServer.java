package de.leonkoth.blockparty.web.server;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.*;
import org.eclipse.jetty.servlet.listener.*;
import org.eclipse.jetty.servlet.*;


/**
 * Created by Leon on 19.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */

public class JettyWebServer implements WebServer {

    private Server server;

    private int port;

    public static final String[] webappDefaultConfigurationClasses = new String[]{
            "org.eclipse.jetty.webapp.WebInfConfiguration"
            ,"org.eclipse.jetty.webapp.WebXmlConfiguration"
            ,"org.eclipse.jetty.webapp.MetaInfConfiguration"
            ,"org.eclipse.jetty.webapp.FragmentConfiguration"
            ,"org.eclipse.jetty.annotations.AnnotationConfiguration"
            ,"org.eclipse.jetty.webapp.JettyWebXmlConfiguration"
    };

    public JettyWebServer() {
        this.port = 8080;
    }

    public JettyWebServer(int port) {
        this.port = port;
    }

    @Override
    public void send(String ip, String arena, String song, String play) {

    }

    @Override
    public void start() throws Exception {
        //Log.setLog(new JettyLogger());
        Log.setLog(new StdErrLog());

        this.server = new Server(this.port);


        server.setStopAtShutdown(true);



        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setBaseResource(Resource.newClassPathResource("/webapp"));
        webapp.setConfigurationClasses(webappDefaultConfigurationClasses);
        webapp.setDescriptor("WEB-INF/web.xml");
        webapp.setClassLoader(getClass().getClassLoader());
        Thread.currentThread().setContextClassLoader(JettyWebServer.class.getClassLoader());
        //webapp.addServlet(new ServletHolder(new MusicPlayerServlet()), "/Musicserver");

        server.setHandler(webapp);

        this.server.start();
    }

    @Override
    public void stop() throws Exception {
        this.server.stop();
    }

}
