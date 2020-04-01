package de.leonkoth.blockparty.web.server.handler;

import de.leonkoth.blockparty.BlockParty;
import org.bukkit.configuration.MemorySection;
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
import java.util.TreeMap;

/**
 * Package de.leonkoth.blockparty.web.server.handler
 *
 * @author Leon Koth
 * © 2020
 */
@WebServlet(urlPatterns = "/api/config")
public class ConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().write(new JSONObject(getMap(BlockParty.getInstance().getPlugin().getConfig().getValues(true))).toString());
        resp.setStatus(200);
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
                JSONObject json = new JSONObject(jsonString.toString());
                FileConfiguration config = BlockParty.getInstance().getPlugin().getConfig();
                Map<String, Object> map = config.getValues(true);
                for(String key : map.keySet())
                {
                    if(json.has(key)){
                        Object val = json.get(key);
                        if(val instanceof JSONArray)
                        {
                            List<Object> list = new ArrayList<>();
                            for(int i = 0; i < ((JSONArray) val).length(); i++)
                            {
                                list.add(((JSONArray) val).get(i));
                            }
                            config.set(key, list);
                        } else config.set(key, json.get(key));
                    }
                }
                config.save(BlockParty.PLUGIN_FOLDER + "config.yml");
                resp.setStatus(200);
                response.put("success", "The Config file got updated");
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

    private Map<String, Object> getMap(Map<String, Object> map)
    {
        Map<String, Object> m = new TreeMap<>();
        for(String s : map.keySet())
        {
            Object value = map.get(s);
            if( !(value instanceof MemorySection) ){
                m.put(s, value);
            }
        }
        return m;
    }

}
