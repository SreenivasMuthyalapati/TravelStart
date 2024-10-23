package testClasses.B2CEndToEnd;

import testMethods.BookingConfirmationPageMethods;
import testMethods.DeeplinksMethods;
import testMethods.Method;
import testMethods.SendEmail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeTestClass {

    static Method m = new Method();
    static SendEmail sendEmail = new SendEmail();
    static DeeplinksMethods deeplinksMethods = new DeeplinksMethods();

    public static void main(String[] args) throws IOException {
        
        //sendEmail.sendEmail(dataPaths.URLs);
        //System.out.println(m.generatePaymentLink("preprod", "ZA", "ZA00109497"));


       }

}
