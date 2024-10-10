package test.B2CEndToEnd;

import pageObjects.PaymentPage;
import testmethods.Method;
import testmethods.SendEmail;

import java.io.IOException;

public class CodeTestClass {

    static Method m = new Method();
    static SendEmail sendEmail = new SendEmail();

    public static void main(String[] args) throws IOException {
        
        //sendEmail.sendEmail();
        System.out.println(m.generatePaymentLink("beta", "ZA", "ZA00182892"));


    }




}
