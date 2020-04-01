package de.leonkoth.blockparty.web.server.handler;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import org.bukkit.configuration.file.FileConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Package de.leonkoth.blockparty.web.server.handler
 *
 * @author Leon Koth
 * © 2020
 */
@WebServlet(urlPatterns = "/api/config/arena")
public class ArenaConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        JSONObject response = new JSONObject();

        String name = req.getParameter("name");

        if(name != null && !name.equals(""))
        {
            Arena arena = Arena.getByName(name);
            if(arena != null)
            {
                resp.setStatus(200);
                response = arena.getData().toJson();
            } else {
                resp.setStatus(400);
                response.put("error", "Arena " + name + " does not exist");
            }
        } else
        {
            JSONObject json = new JSONObject();
            List<String> names = new ArrayList<>();
            for(Arena arena : BlockParty.getInstance().getArenas())
                names.add(arena.getName());
            json.put("names", new JSONArray(names));
            resp.setStatus(200);
            response = json;
        }

        resp.getWriter().write(response.toString());

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        JSONObject response = new JSONObject();
        if ("application/json".equals(req.getContentType()))
        {
            BufferedReader br = req.getReader();
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                jsonString.append(line);
            }

            try {
                String name = req.getParameter("name");
                JSONObject json = new JSONObject(jsonString.toString());
                if(name == null || name.equals("")) {
                    name = json.getString("name");
                }
                Arena arena = Arena.getByName(name);
                if(arena != null) {
                    arena.getData().insert(json, arena);
                    arena.saveData();

                    resp.setStatus(200);
                    response.put("success", "Arena " + name + " got updated");
                } else {
                    resp.setStatus(400);
                    response.put("error", "Arena " + name + " does not exist");
                }

            } catch (JSONException e)
            {
                resp.setStatus(500);
                response.put("error", "There was an error parsing the json message");
            }
        } else {
            resp.setStatus(400);
            response.put("error", "Content-Type has to be application/json");
        }
        resp.getWriter().write(response.toString());
    }
}
