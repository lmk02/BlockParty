package de.leonkoth.utils.web.GitHub;

import okhttp3.*;

import java.io.IOException;
import java.util.StringJoiner;

/**
 * Package de.leonkoth.utils.web
 *
 * @author Leon Koth
 * © 2020
 */
public class Issue {

    private String apiAddress;

    private String gitHubRepo;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client;

    public Issue (String apiAddress, String gitHubRepo)
    {
        this.apiAddress = apiAddress;
        this.gitHubRepo = gitHubRepo;
        this.client = new OkHttpClient();
    }

    public int post(String body) throws IOException
    {
        return post(null, body, null, -1, null);
    }

    public int post(String title, String body, String[] assignees, int milestone, String[] labels) throws IOException {
        StringJoiner assigneesFormatted = new StringJoiner(",", "[", "]");
        if(assignees != null)
        {
            for (String assignee : assignees) {
                assigneesFormatted.add("\"" + assignee + "\"");
            }
        }
        StringJoiner labelsFormatted = new StringJoiner(",", "[", "]");
        if(assignees != null)
        {
            for (String label : labels) {
                labelsFormatted.add("\"" + label + "\"");
            }
        }

        String json = "{" +
                "\"content\":" +
                "{" +
                    "\"title\": \"" + title + "\"," +
                    "\"body\": \"" + body + "\", " +
                    "\"assignees\": " + assigneesFormatted.toString() + ", "
                    + (milestone > 0 ? "\"milestone\": " + milestone + ", " : "") + "" +
                    "\"labels\": " + labelsFormatted.toString() + "" +
                "}, " +
                "\"gitHubRepo\": \"" + this.gitHubRepo + "\"}";

        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(this.apiAddress)
                .post(requestBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }


}
