package de.leonkoth.blockparty.web.server.handler;

import de.leonkoth.blockparty.player.PlayerInfo;
import org.bukkit.Bukkit;

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
            System.out.println(playerName);
            response.setContentType("text/html");
            if(PlayerInfo.getFromPlayer(playerName) == null || Bukkit.getPlayer(playerName) == null)
            {
                response.getWriter().write("_false_");
            } else {
                response.getWriter().write("_true_");
                request.getSession().setAttribute("name", playerName);
            }
        }

    }

}
