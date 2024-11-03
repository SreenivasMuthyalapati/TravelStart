package testClasses.B2CEndToEnd;

import org.json.JSONObject;
import testMethods.*;
import utils.TestReport;

import java.io.IOException;
import java.util.*;

import static org.testng.Assert.assertNotNull;

public class CodeTestClass {

    static Method m = new Method();
    static SendEmail sendEmail = new SendEmail();
    static DeeplinksMethods deeplinksMethods = new DeeplinksMethods();

    public static void main(String[] args) throws IOException {

        // Create an instance of the class to test
        WapiMethods wapiMethods = new WapiMethods();

        // Define test parameters
        String environment = "preprod";
        String tripType = "oneway";
        String cabinClass = "Economy";
        String originIATACode = "CPT";
        String destinationIATACode = "DXB";
        String departureDate = "2025-01-17";
        String returnDate = "2025-01-31"; // This will be ignored since tripType is "oneway"
        String adultCount = "1";
        String youngAdultCount = "0";
        String childCount = "0";
        String infantCount = "0";

        // Call the method
        JSONObject response = wapiMethods.hitSearchEndPoint(
                environment, tripType, cabinClass, originIATACode, destinationIATACode,
                departureDate, returnDate, adultCount, youngAdultCount, childCount, infantCount
        );

        // Assert that a response is received
        assertNotNull(response, "The API response should not be null.");

        // Print the response to inspect
        System.out.println("Response from API: " + response.toString(2));



    }


    }


