package testMethods;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v116.network.Network;
import org.openqa.selenium.devtools.v116.network.model.RequestId;

import java.util.Optional;

public class NetworkTabReader {
    private final DevTools devTools;

    public NetworkTabReader(ChromeDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        this.devTools = driver.getDevTools();
    }

    public void enableNetworkTracking() {
        // Create a session
        devTools.createSession();

        // Enable the network domain to start capturing network activity
        // devTools.send(Network.enable());
        devTools.send(Network.enable(Optional.of(1000000), Optional.of(1000000), Optional.of(1000000)));
        // Optionally, you can add a listener to get all requests
        devTools.addListener(Network.requestWillBeSent(), request -> {
            RequestId requestId = request.getRequestId();
            String url = request.getRequest().getUrl();
            System.out.println("Request sent: " + url);
        });

        // Optionally, you can also add a listener for responses
        devTools.addListener(Network.responseReceived(), response -> {
            String requestId = String.valueOf(response.getRequestId());
            String url = response.getResponse().getUrl();
            System.out.println("Response received: " + url);
        });
    }

    public void getNetworkLogs() {
        // Here you can fetch and process the logs as needed.
        // Currently, this method only prints requests and responses as they happen.
    }

    public void stopNetworkTracking() {
        // You can stop tracking if needed.
        devTools.send(Network.disable());
    }
}
