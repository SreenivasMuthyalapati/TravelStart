package listeners;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class CustomTestNGReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // Implement your report generation logic here
        // This could involve creating an HTML report or any other format

        // After generating the report, you can send it via email
        sendEmail(outputDirectory + "C:\\Users\\Dell\\IdeaProjects\\travelStart\\test-output\\emailable-report.html", "sreenivasulu@travelstart.com");
    }

    private void sendEmail(String reportPath, String recipientEmail) {
        // Implement logic to send the email with the report attachment
        // You can use JavaMail API or any other library for sending emails
    }
}
