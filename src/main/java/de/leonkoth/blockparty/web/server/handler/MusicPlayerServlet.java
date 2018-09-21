package de.leonkoth.blockparty.web.server.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Leon on 19.09.2018.
 * Project BlockParty-2.0
 * © 2016 - Leon Koth
 */

@WebServlet(urlPatterns = "/Musicplayer")
public class MusicPlayerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String playerName = (String)request.getSession(true).getAttribute("name");
        if(playerName != null && !playerName.equals(""))
        {
            response.getWriter().write(playerName);
        }
        System.out.println("Name = " + playerName);

    }

}
