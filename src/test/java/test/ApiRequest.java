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
        // Create an HTTP client
        HttpClient httpClient = HttpClients.createDefault();

        // Define the API endpoint URL
        String apiUrl = "https://library-api.postmanlabs.com/books";

        // Create an HTTP GET request
        HttpGet httpGet = new HttpGet(apiUrl);

        // Execute the request and get the response
        HttpResponse response = httpClient.execute(httpGet);

        // Read and print the API response
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line;
        StringBuilder responseContent = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            responseContent.append(line);
        }

        // Close the reader
        reader.close();

        // Print the API response
        System.out.println("API Response: " + responseContent.toString());

        // You can now parse and process the API response as needed
    }
}

