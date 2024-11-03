package testMethods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class WapiMethods {

    Method m = new Method();

    public String baseURL(String environment){

        String baseURL = null;

        if (environment.equalsIgnoreCase("Preprod")) {
            baseURL = "https://preprod-wapi.travelstart.com/website-services/api/";
        } else if (environment.equalsIgnoreCase("live")) {
            baseURL = "https://wapi.travelstart.com/website-services/api/";
        } else if (environment.equalsIgnoreCase("beta")) {
            baseURL = "https://beta-wapi.travelstart.com/website-services/api/";
        } else if (environment.equalsIgnoreCase("alpha")) {
            baseURL = "https://alpha-wapi.travelstart.com/website-services/api/";
        }


        return baseURL;
    }

    public JSONObject hitSearchEndPoint(String environment, String tripType, String cabinClass,
                                        String originIATACode, String destinationIATACode,
                                        String departureDate, String returnDate,
                                        String adultCount, String youngAdultCount,
                                        String childCount, String infantCount) {

        JSONObject searchResponse = null;
        String CID = Method.generateCID();

        // Determine API endpoint based on environment
        String apiUrl;
        switch (environment.toLowerCase()) {
            case "preprod":
                apiUrl = "https://preprod-wapi.travelstart.com/website-services/api/search";
                break;
            case "live":
                apiUrl = "https://wapi.travelstart.com/website-services/api/search";
                break;
            case "beta":
                apiUrl = "https://beta-wapi.travelstart.com/website-services/api/search";
                break;
            default:
                throw new IllegalArgumentException("Invalid environment specified.");
        }

        // Create the request body as JSON
        JSONObject requestBody = new JSONObject();
        requestBody.put("tripType", tripType);
        requestBody.put("isNewSession", true);

        JSONObject travellers = new JSONObject();
        travellers.put("adults", Integer.parseInt(adultCount));
        travellers.put("youngAdults", Integer.parseInt(youngAdultCount));
        travellers.put("children", Integer.parseInt(childCount));
        travellers.put("infants", Integer.parseInt(infantCount));
        requestBody.put("travellers", travellers);

        JSONObject moreOptions = new JSONObject();
        JSONObject preferredCabins = new JSONObject();
        preferredCabins.put("display", cabinClass);
        preferredCabins.put("value", cabinClass.toUpperCase());
        moreOptions.put("preferredCabins", preferredCabins);
        moreOptions.put("isCalendarSearch", false);
        requestBody.put("moreOptions", moreOptions);

        // Add empty fields in request body
        requestBody.put("outboundFlightNumber", "");
        requestBody.put("inboundFlightNumber", "");

        // Itinerary details
        JSONObject itinerary = new JSONObject();
        itinerary.put("id", CID);

        JSONObject origin = new JSONObject();
        JSONObject originValue = new JSONObject();
        originValue.put("type", "");
        originValue.put("city", "");
        originValue.put("airport", "");
        originValue.put("iata", originIATACode);
        originValue.put("code", originIATACode);
        originValue.put("country", "");
        originValue.put("countryIata", "");
        originValue.put("locationId", "");
        origin.put("value", originValue);
        origin.put("display", "");

        JSONObject destination = new JSONObject();
        JSONObject destinationValue = new JSONObject();
        destinationValue.put("type", "");
        destinationValue.put("city", "");
        destinationValue.put("airport", "");
        destinationValue.put("iata", destinationIATACode);
        destinationValue.put("code", destinationIATACode);
        destinationValue.put("country", "");
        destinationValue.put("countryIata", "");
        destinationValue.put("locationId", "");
        destination.put("value", destinationValue);
        destination.put("display", "");

        itinerary.put("origin", origin);
        itinerary.put("destination", destination);
        itinerary.put("departDate", departureDate);

        // Set return date based on tripType
        if (tripType.equalsIgnoreCase("oneway")) {
            itinerary.put("returnDate", "");  // Empty return date for oneway trip
        } else {
            itinerary.put("returnDate", returnDate);
        }

        requestBody.put("itineraries", new JSONArray().put(itinerary));

        // Add other empty fields
        requestBody.put("searchIdentifier", "");

        JSONObject locale = new JSONObject();
        locale.put("country", "ZA");
        locale.put("currentLocale", "en");
        locale.put("locales", new JSONArray());
        requestBody.put("locale", locale);

        requestBody.put("userProfileUsername", "");
        requestBody.put("businessLoggedOnToken", "");
        requestBody.put("isDeepLink", false);

        // Send the POST request
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("ts-country", "za")
                .header("ts-language", "en")
                .body(requestBody.toString())
                .post(apiUrl + "?correlation_id=test_correlation&cpysource=tszaweb&language=en&includeDeeplinks=true");

        // Store the response in a JSONObject if the response is successful
        if (response.getStatusCode() == 200) {
            searchResponse = new JSONObject(response.getBody().asString());
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode());
        }

        return searchResponse;
    }


    public JSONArray getItinerariesFromSearchResponse(JSONObject searchResponse){

        // Parse JSON response
        JSONArray itineraries = searchResponse.getJSONObject("response").getJSONArray("itineraries");


        return itineraries;
    }

    public String getItineraryDeeplinkFromSearchResponse(JSONObject itineraryBody) {
        // Retrieve the deepLink value from the JSON object
        String deepLink = itineraryBody.getString("deepLink");

        // Return the deepLink URL directly
        return deepLink;
    }


    public int getItineraryPriceFromSearchResponse(JSONObject itineraryBody){
        int amount = 0;

        amount = itineraryBody.getInt("amount");

        return amount;
    }

    public int getItineraryIDFromSearchResponse(JSONObject itineraryBody){
        int id = 0;

        id = itineraryBody.getInt("id");

        return id;
    }

    public String getFlightNumbersFromItineraryObject(JSONObject itineraryBody) {
        StringBuilder flightNumbers = new StringBuilder();

        JSONArray odoListArray = itineraryBody.getJSONArray("odoList");
        int odoListCount = odoListArray.length();

        for (int i = 0; i < odoListCount; i++) {
            JSONObject segmentBody = odoListArray.getJSONObject(i);
            JSONArray segmentsArray = segmentBody.getJSONArray("segments");

            int segmentCount = segmentsArray.length();
            for (int j = 0; j < segmentCount; j++) {
                JSONObject segmentObject = segmentsArray.getJSONObject(j);
                String flightNumber = segmentObject.getString("flightNumber");

                if (flightNumbers.length() > 0) {
                    flightNumbers.append(",");
                }
                flightNumbers.append(flightNumber);
            }
        }

        return flightNumbers.toString();
    }

    public String getAirlineCodesFromItineraryObject(JSONObject itineraryBody) {
        Set<String> airlineCodes = new LinkedHashSet<>();  // Using LinkedHashSet to maintain insertion order and avoid duplicates

        JSONArray odoListArray = itineraryBody.getJSONArray("odoList");

        for (int i = 0; i < odoListArray.length(); i++) {
            JSONObject odoObject = odoListArray.getJSONObject(i);
            JSONArray segmentsArray = odoObject.getJSONArray("segments");

            for (int j = 0; j < segmentsArray.length(); j++) {
                JSONObject segmentObject = segmentsArray.getJSONObject(j);
                String airlineCode = segmentObject.getString("airlineCode");

                if (airlineCode != null && !airlineCode.isEmpty()) {
                    airlineCodes.add(airlineCode);
                }
            }
        }

        // Join all unique airline codes into a comma-separated string
        return String.join(",", airlineCodes);
    }




}
