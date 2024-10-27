package configs;

public interface dataPaths {


        public static String dataBasePath = System.getProperty("user.dir");


        //Data Paths
        public static String URLs = dataBasePath+"\\TestData\\URLs.xls";
        public static String dataPath = dataBasePath+"\\TestData\\DataBook.xls";
        public static String B2CBookingE2ETestData = dataBasePath+"\\TestData\\B2CBookingFlowEndToEnd.xls";
        public static String contactUsDataPath = dataBasePath+"\\TestData\\ContactUsData.xls";
        public static String screenshotFolder = dataBasePath+"\\TestScreenShots";
        public static String excelOutputPath = dataBasePath+"\\TestResult\\BookingOutput.xlsx";
        public static String bookingReferencesForCancellation = dataBasePath+"\\TestData\\BookingReferences.xls";
        public static String TSPlusDataPath = dataBasePath+"\\TestData\\TS+ Subscription Data.xls";
        public static String deepLinks = dataBasePath+"\\TestData\\Deeplinks.xls";
         public static String seatsTest = dataBasePath+"\\TestData\\SeatsTest.xls";
        public static String b2bTestData = dataBasePath+"\\TestData\\B2BBookingData.xls";





}
