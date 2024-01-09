package test;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApiRequest {
    public static void main(String[] args) throws IOException {

        HttpClient httpClient = HttpClients.createDefault();

        String apiUrl = "https://library-api.postmanlabs.com/books";

        HttpGet httpGet = new HttpGet(apiUrl);

        HttpResponse response = httpClient.execute(httpGet);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder responseContent = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }

        reader.close();

        System.out.println("API Response: " + responseContent.toString());

    }
}

