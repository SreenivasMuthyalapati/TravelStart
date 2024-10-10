package test.Booking;

import testmethods.Method;

import java.util.HashMap;
import java.util.Map;

public class TestStatusTracker {

    static Method method = new Method();

    public static void main(String[] args) {
        TestStatusTracker tracker = new TestStatusTracker();

        // Example of calling the method 10 times with different statuses
        method.updateTestStatus("PASSED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("SKIPPED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");
        method.updateTestStatus("FAILED");

        // Print the final status map
        System.out.println(method.statusMap);
    }
}

