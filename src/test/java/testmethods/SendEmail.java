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

public class SendEmail {

    public void sendEmail() {
        // Retrieve the SendGrid API key from environment variable
        String apiKey = System.getenv("SENDGRID_API_KEY");
        String username = System.getProperty("user.name").toUpperCase();

        // Create the SendGrid client
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            // Set the request method to POST and the endpoint
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            // Create email details
            Email from = new Email("lihle@travelstart.com");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);
            String subject = STR."""
Automation Test Results – Travelstart B2C\s
\{timestamp}""";
            String[] recipients = {"lihlesolomon@outlook.com"};
            // Recipient email
            Content content = new Content("text/plain", STR."""
Dear Stakeholders,

Please find attached the detailed automation test results for \{}, executed on \{timestamp}. The attached HTML report contains a comprehensive breakdown of all tests, including pass/fail statuses and other key metrics.

Summary of Results:
- Total Tests Executed: [Number of Tests]
- Passed Tests: [Number of Passed Tests]
- Failed Tests: [Number of Failed Tests]
- Tests Skipped: [Number of Skipped Tests]

For complete details, kindly review the attached HTML report.

If you have any questions, feel free to reach out.

Best regards,
Lihle Solomon
QA Analyst
Travelstart

Mailing Address: Longkloof Studios, Darters Rd, Gardens, Cape Town, 8001
Unsubscribe: If you no longer wish to receive these updates, please contact us.""");
            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.addContent(content);
            Personalization personalization = new Personalization();
            for (String recipientEmail : recipients) {
                Email to = new Email(recipientEmail);
                personalization.addTo(to); // Add each recipient to the same Personalization object
            }
            mail.addPersonalization(personalization);

            // Attach a file
            File file = new File("C:\\Users\\lihle\\IdeaProjects\\travelStart\\TestResult\\report.html"); // Path to the file you want to attach
            Attachments attachments = new Attachments();
            attachments.setContent(Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath())));
            attachments.setType("text/html"); // Change type as per your file type
            attachments.setFilename(file.getName());
            attachments.setDisposition("attachment");
            mail.addAttachments(attachments);

            // Add the mail object to the request body
            request.setBody(mail.build());

            // Send the request
            Response response = sg.api(request);
            System.out.println(STR."Status Code: \{response.getStatusCode()}");
            System.out.println(STR."Response Body: \{response.getBody()}");
            System.out.println(STR."Response Headers: \{response.getHeaders()}");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


// We can make line 54 dynamic to make sure we get the latest report; line 66-69 are not really needed but will help for us to know if the mail has been sent
// Modification
// The mail should be able to be sent to multiple people at one time also note we have a single day limit of 100 and 3000 per month

