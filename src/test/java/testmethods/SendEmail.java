package testmethods;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.stream.Stream;

// Other imports remain the same...

public class SendEmail {

    private static final int DAILY_LIMIT = 100;
    private static final int MONTHLY_LIMIT = 3000;

    private int dailyEmailCount = 0;
    private int monthlyEmailCount = 0;

    public void sendEmail(String attachmentLocationPath) {
        // Retrieve the SendGrid API key from environment variable
        String apiKey = "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";

        System.out.println(apiKey);

        String username = System.getProperty("user.name").toUpperCase();

        // Create the SendGrid client
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        // Track email counts to respect SendGrid's limits
        if (dailyEmailCount >= DAILY_LIMIT || monthlyEmailCount >= MONTHLY_LIMIT) {
            System.out.println("Email limit reached. Cannot send more emails today.");
            return;
        }

        try {
            // Set the request method to POST and the endpoint
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            // Create email details
            Email from = new Email("sreenivasulu@travelstart.com");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String subject = String.format("Automation Test Results – Travelstart B2C %s", timestamp);

            String[] recipients = {"rezza@travelstart.com", "sreenivas.tsqa@gmail.com"};
            Content content = new Content("text/plain", String.format("""

Dear Stakeholders,


Please find attached the detailed automation test results, executed on %s. The attached excel report contains a comprehensive breakdown of all tests, including booking scenarios, test cases and test statuses and other key metrics.

For complete details, kindly review the attached excel report.


Best regards,
Sreenivas Muthyalapati
QA Analyst
Travelstart
""", username, timestamp));

            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.addContent(content);

            // Add multiple recipients
            Personalization personalization = new Personalization();
            for (String recipientEmail : recipients) {
                Email to = new Email(recipientEmail);
                personalization.addTo(to);
            }
            mail.addPersonalization(personalization);

            // Attach the latest report
            File latestReport = getLatestReport(attachmentLocationPath);
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(Files.readAllBytes(latestReport.toPath())));
            attachments.setType("text/html");
            attachments.setFilename(latestReport.getName());
            attachments.setDisposition("attachment");
            mail.addAttachments(attachments);

            // Add the mail object to the request body
            request.setBody(mail.build());

            // Send the request
            Response response = sg.api(request);
 //           System.out.println(String.format("Status Code: %d", response.getStatusCode()));
//            System.out.println("Response Body: " + response.getBody());
//            System.out.println("Response Headers: " + response.getHeaders());
            if (response.getStatusCode() == 202) {
                System.out.println("Email report has been sent");
            }
            // Update email counts
            dailyEmailCount++;
            monthlyEmailCount++;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to get the latest report file dynamically
    private File getLatestReport(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .max(Comparator.comparingLong(File::lastModified))
                    .orElseThrow(() -> new IOException("No report found in the specified directory"));
        }
    }
}



// We can make line 54 dynamic to make sure we get the latest report; line 66-69 are not really needed but will help for us to know if the mail has been sent
// Modification
// The mail should be able to be sent to multiple people at one time also note we have a single day limit of 100 and 3000 per month

