package test.B2CEndToEnd;

import pageObjects.PaymentPage;
import testmethods.Method;

import java.io.IOException;

public class CodeTestClass {

    static Method m = new Method();

    public static void main(String[] args) throws IOException {


       System.out.println(m.isBundled("NG", "Return", "JNB", "CPT"));

       System.out.println(m.generatePaymentLink("preprod", "ZA", "ZA00109002"));

    }




}
