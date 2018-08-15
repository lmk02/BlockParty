package de.leonkoth.blockparty.web.server;

import java.io.IOException;

/**
 * Created by Leon on 20.03.2018.
 * Project Blockparty2
 * © 2016 - Leon Koth
 */
public interface WebServer {

    void send(String ip, String arena, String song, String play);

    void start() throws IOException;

    void stop() throws IOException, InterruptedException;

}
