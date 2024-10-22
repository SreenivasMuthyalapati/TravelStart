package testClasses.BookingCancellation;

import org.json.JSONException;
import testmethods.ExcelUtils;
import testmethods.Method;

import java.io.IOException;

public class BookingCancellation {

    static Method m = new Method();
    static ExcelUtils excelUtils = new ExcelUtils();
    public static void main(String[] args) throws IOException {
        
        
        int bookingsCount = excelUtils.getRowCount(configs.dataPaths.bookingReferencesForCancellation,"Bookings References")-1;

        System.out.println("Total bookings count: "+ bookingsCount);
        System.out.println();

        for (int i = 0; i<bookingsCount; i++){

            System.out.println(i+1 + ": ");

            System.out.println();

            String environment = excelUtils.readDataFromExcel(configs.dataPaths.bookingReferencesForCancellation,"Bookings References", i+1, 1);

            String bookingRefNumber = excelUtils.readDataFromExcel(configs.dataPaths.bookingReferencesForCancellation, "Bookings References", i+1, 0);

            String firstName = excelUtils.readDataFromExcel(configs.dataPaths.bookingReferencesForCancellation,"Bookings References", i+1, 2);
            try {
                m.cancelBooking(environment, bookingRefNumber);
            } catch (JSONException e){
                System.out.println("JSON exception occured, booking reference:"+bookingRefNumber+" may not have been cancelled");
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------");

            System.out.println();

        }

    }

}
