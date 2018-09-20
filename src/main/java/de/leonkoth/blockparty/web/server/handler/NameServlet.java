package de.leonkoth.blockparty.web.server.handler;

import javax.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = "/NameRequest")
public class NameServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String playerName = request.getParameter("playerName");

        if(playerName != null && !playerName.equals(""))
        {
            //TODO Check if player is online
            System.out.println(playerName);
            response.setContentType("text/html");
            response.getWriter().write("_true_");
            //System.out.println(request.getSession(true).getId());
            request.getSession().setAttribute("name", playerName);
        }

    }

}
