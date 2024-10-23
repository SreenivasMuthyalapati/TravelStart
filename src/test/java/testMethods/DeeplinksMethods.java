package testMethods;

import java.io.IOException;

import static testMethods.TSMethods.m;

public class DeeplinksMethods {

    public String generateSRPDeeplink (String environment, String domain, String tripType, String from, String to, String depDay, String depMonth, String depYear, String retDay, String retMonth, String retYear, String adultCount, String teenCount, String childCount, String infantCount){

        String deepLink = "";

        if (environment.equalsIgnoreCase("LIVE")){

            if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://www.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://www.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://www.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://www.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }

        }

        else if (environment.equalsIgnoreCase("BETA")){

            if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://beta.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://beta.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://beta.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://beta.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }

        }

        else if (environment.equalsIgnoreCase("preprod")){

            if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://preprod.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://preprod.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://preprod.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://preprod.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }

        }

        else if (environment.equalsIgnoreCase("alpha")){

            if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://alpha.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Oneway")){

                deepLink = "https://alpha.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("ZA") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://alpha.travelstart.co.za/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }
            else if (domain.equalsIgnoreCase("NG") && tripType.equalsIgnoreCase("Return")){

                deepLink = "https://alpha.travelstart.com.ng/search-on-index?version=3&timestamp=2024-07-15_15-48-06-048&from_0="+from+"&from_type_0=city&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=city&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens="+teenCount+"&children="+childCount+"&infants="+infantCount+"&currency=ZAR&cpy_source=tszaweb&correlation_id="+Method.generateCID()+"&search=true&show_search_options=false&language=en&class=Economy";

            }


        }

        return deepLink;
    }

    public String generateMetaDeeplink (String environment, String domain, String metaName, String tripType, String from, String to, String depDay, String depMonth, String depYear, String retDay, String retMonth, String retYear, String adultCount, String teenCount, String childCount, String infantCount, boolean isBundled, String onwardAirline, String returnAirline){

        String deeplink = "";

        String enviromentURL = "";
        String domainURL = "";
        String pathParameter = "";
        String searchParams = "";
        String currency = "";
        String flightDetailsParams = "";
        String metaTrackingParams = "";
        String CID = Method.generateCID();


        if (domain.equalsIgnoreCase("ZA")){

            currency = "ZAR";

        } else if (domain.equalsIgnoreCase("NG")) {

            currency = "NGN";

        }

        if (metaName.equalsIgnoreCase("CheapFlights")){

            pathParameter = "price-on-index";

        } else {

            pathParameter = "search-on-index";

        }

        if(environment.equalsIgnoreCase("live")){

            enviromentURL = "www";

        } else if (environment.equalsIgnoreCase("preprod")) {

            enviromentURL = "preprod";

        } else if (environment.equalsIgnoreCase("beta")) {

            enviromentURL = "beta";

        }

        if (domain.equalsIgnoreCase("ZA")){

            domainURL = "co.za";

        } else if (domain.equalsIgnoreCase("NG")) {

            domainURL = "com.ng";

        }

        if (tripType.equalsIgnoreCase("Oneway")){

            searchParams = "from_0="+from+"&from_type_0=airport&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&is_flex_dates=false&trip_type=OneWay&adults="+adultCount+"&teens=0&children="+childCount+"&infants="+infantCount+"&class=Economy";

        } else if (tripType.equalsIgnoreCase("Return")) {

            searchParams = "from_0="+from+"&from_type_0=airport&to_0="+to+"&to_type_0=airport&depart_date_0="+depYear+"-"+depMonth+"-"+depDay+"&from_1="+to+"&from_type_1=airport&to_1="+from+"&to_type_1=airport&depart_date_1="+retYear+"-"+retMonth+"-"+retDay+"&is_flex_dates=false&trip_type=Roundtrip&adults="+adultCount+"&teens=0&children="+childCount+"&infants="+infantCount+"&class=Economy";

        }

        if (tripType.equalsIgnoreCase("Oneway")){

            flightDetailsParams = "outbound_flight_number="+onwardAirline+"369";

        }
        else if (tripType.equalsIgnoreCase("return")){

            flightDetailsParams = "outbound_flight_number="+onwardAirline+"369&inbound_flight_number="+returnAirline+"372";

        }

        if (metaName.equalsIgnoreCase("googleFlights")){

            metaTrackingParams = "cpy_source=GoogleFSZA&aff_id=googleflights&correlation_id="+CID+"&search=true&show_search_options=false&language=en&utm_source=metasearch&utm_source=metasearch&utm_medium=googleflights_za&utm_medium=googleflights_za&utm_campaign=flights&utm_campaign=flights&utm_term="+from+"_"+to+"&utm_term="+from+"_"+to+"&gclid=ADowPOJqc_gCBb3tkyD9Y2VFv69yPARBbOSur_UKpf8ArWGKlFntgrHJF2987GBSKpPzN9Nij5eFlzIDDH4jRqbyO-Sm5B6_ZNVbMHs7uZsufN7y5ij5&gclsrc=gf";

        }
        else if (metaName.equalsIgnoreCase("cheapflights")){

            metaTrackingParams = CID+"&search=true&show_search_options=false&language=en&cpy_source=cheapflightsza&affid=cheapflightsza&utm_source=meta&utm_medium=cheapflightsza&utm_term="+from+"_"+to+"&utm_term="+from+"_"+to+"&kayakclickid=TNkc8IP9Bo9cQbIvHRrLrQ";

        }
        else if (metaName.equalsIgnoreCase("skyscanner")){

            metaTrackingParams = CID+"aff_id=skyscanzats&search=true&skyscanner_redirectid=lEFxtGKBRmSpTFRoLtt8BQ&affid=skyscanzats&cpy_source=skyscanzats&utm_source=meta&utm_medium=skyscanzats&utm_term="+from+"_"+to+"&utm_term="+from+"_"+to;

        }

        deeplink = "https://"+enviromentURL+".travelstart."+domainURL+"/"+pathParameter+"?version=3&"+searchParams+"&class=Economy&"+"currency="+currency+"&"+flightDetailsParams+"&"+metaTrackingParams;


        return deeplink;
    }




    public String generatePaymentDeeplink(String environment, String domain, String bookingReference) throws IOException {

        String paymentLink = "";

        String UUID = m.getUUID(environment, bookingReference);
        String invID = m.getInvID(environment, bookingReference);
        String cpySource = m.getCpySource(environment, bookingReference);
        String CID = Method.generateCID();

        String baseURL = "https://www.travelstart.co.za/find-itinerary?";


        if (domain.equalsIgnoreCase("ZA")) {

            if (environment.equalsIgnoreCase("Live")) {
                baseURL = "https://www.travelstart.co.za/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Preprod")) {
                baseURL = "https://preprod.travelstart.co.za/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Beta")) {
                baseURL = "https://beta.travelstart.co.za/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Alpha")) {
                baseURL = "https://alpha.travelstart.co.za/find-itinerary?";
            }

        } else if (domain.equalsIgnoreCase("NG")) {

            if (environment.equalsIgnoreCase("Live")) {
                baseURL = "https://www.travelstart.com.ng/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Preprod")) {
                baseURL = "https://preprod.travelstart.com.ng/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Beta")) {
                baseURL = "https://beta.travelstart.com.ng/find-itinerary?";
            } else if (environment.equalsIgnoreCase("Alpha")) {
                baseURL = "https://alpha.travelstart.com.ng/find-itinerary?";
            }

        }

        paymentLink = baseURL+"uuid="+UUID+"&invid="+invID+"&cpysource="+cpySource+"&utm_source=transactional&utm_medium=email&utm_campaign=booking-confirmation&utm_content=payment-link"+"&correlation_id="+CID;


        return paymentLink;
    }

}
