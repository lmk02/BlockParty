package de.leonkoth.utils.web.GitHub;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Package de.leonkoth.utils.web
 *
 * @author Leon Koth
 * © 2020
 */
public class Issue {

    private String apiAddress;

    private String gitHubRepo;

    private HttpClient client;

    public Issue(String apiAddress, String gitHubRepo, HttpClient client) {
        this.apiAddress = apiAddress;
        this.gitHubRepo = gitHubRepo;
        this.client = client;
    }

    public int post(String body) throws InterruptedException, ExecutionException, TimeoutException {
        return post(null, body, null, -1, null);
    }

    public int post(String title, String body, String[] assignees, int milestone, String[] labels) throws InterruptedException, ExecutionException, TimeoutException {
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

        ContentResponse response = this.client.POST(this.apiAddress).header(HttpHeader.CONTENT_TYPE, "application/json").content(new StringContentProvider(json, "utf-8")).send();

        return response.getStatus();
    }


}
