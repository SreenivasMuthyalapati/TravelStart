package test.B2CEndToEnd;

public class BookingFlowE2E {


    /*Booking Validations
    :

    1: Web app launch assertion
    2: Search result assertion
    3: Filters assertion
    4: Currency validation
    5: Result match validation
    6: Result rendering time assertion
    7: Modify search
    8: Traveller page assertion
    9: (Login user) Mobile number and mail ID prepopulate
    10: Saved travellers on traveller details page
    11: Price match between SRP and traveller page
    12: Flight numbers match between SRP and traveller page
    13: Departure & return dates match between SRP and traveller page
    14: Flight timings match between SRP and traveller page
    15: Traveller details fields validation
    16: Checked baggage reprice
    17: Checked baggage addition validation
    18: Checked baggage price addition
    19: Checked baggage
    20: Check in baggage addition
    21: Check in baggage price into breakdown
    22: Whatsapp price in breakdown
    23: Meals selection (If offered)
    24: Meals price into breakdown
    25: Frequent Flyer Number
    26: Seat map assertion
    27: Seats selection
    28: Seats price
    29: Seats price in breakdown validation
    30: Addons assertion
    31: Addons selections
    32: Addons price into breakdown validation
    33: Payment page assertion
    34: Payment methods validation
    35: Payment fields validation
    36: Booking reference validation
    38: Voucher application
    39: Voucher amount into breakdown
    40: price calculation validation on payment page
    41: Booking fee
    42: VAT application
    43: Payment split validation
    44: 3ds redirection(cc/dc)
    45: (Login user)Saved cards prepopulate
    46: (Login user)Saved card payment redirection
    47: Paystack / IPAY redirection
    48: Paystack / IPAY failure booking confirmation
    49: EFT booking
    50: Booking confirmation assertion
    51: Price calculation on booking confirmation
    52: Flight numbers validation on booking confirmation
    53: Invoice validation
    54: Passengers details validation on booking confirmation
    55: Selected seats display on booking confirmation
    56: Payment method details on booking confirmation
    57: Booking details in "My Bookings"

    */

    // Class variables and instances


    // Validation and Assertion variables
    // Global variables:

    public static String departureDate;
    public static String returnDate;
    public static String[] departureAndReturnAirport;


    // SRP variables:

    public static String priceInSRP;
    public static String[] flightNumbersInSRP;
    public static int segmentCount;
    public static String[] segmentsInSRP;
    public static String[] segmentDatesInSRP;
    public static String[] flightTimingsInSRP;


    //TravellerPage variables

    public static String baseFareAndTaxes;
    public static String totalAmountInTravellerPage;
    public static String segmentsInTravellerPage;
    public static String[] flightNumbersInTravellerPage;
    public static String[] segmentDatesInTravellerPage;
    public static String[] flightTimingsInTravellerPage;
    

}
