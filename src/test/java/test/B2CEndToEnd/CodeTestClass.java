package test.B2CEndToEnd;

import configs.dataPaths;
import org.json.JSONObject;
import pageObjects.PaymentPage;
import testmethods.DeeplinksMethods;
import testmethods.Method;
import testmethods.SendEmail;

import java.io.IOException;

public class CodeTestClass {

    static Method m = new Method();
    static SendEmail sendEmail = new SendEmail();
    static DeeplinksMethods deeplinksMethods = new DeeplinksMethods();

    public static void main(String[] args) throws IOException {
        
        //sendEmail.sendEmail(dataPaths.URLs);
        //System.out.println(m.generatePaymentLink("preprod", "ZA", "ZA00109497"));

      sendEmail.sendEmailWithTemplateAndJson("C:\\Users\\Sreen\\IdeaProjects\\travelStart\\TestData\\B2CBookingFlowEndToEnd.xls", "B2C", 2, 6, 4,1,1);

        System.out.println(m.getBearerToken("Preprod"));


       }

}
