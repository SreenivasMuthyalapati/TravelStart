package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import javax.activation.DataSource;

public class CustomTestListener implements ITestListener {

    @Override
    public void onFinish(ITestContext context) {
        // Generate report and send email
        generateReport();
        sendEmail();
    }

    // Method to generate report
    private void generateReport() {
        // Add code to generate report here
    }

    // Method to send email without authentication
    // Method to send email
    private void sendEmail() {
        // Sender's email ID needs to be specified
        String from = "sreenivas@yopmail.com"; // Replace with your YOPmail address
        // Recipient's email ID needs to be specified
        String to = "sreenivasulu@travelstart.com"; // Change this to the recipient's email address

        // Setting up mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");

        // Get the Session object
        Session session = Session.getInstance(props);

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Test Report");
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText("Please find the test report attached.");
            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "reports/test-report.html"; // Change this to your actual report file path
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);

            // Connect to SMTP server and send message
            Transport.send(message);

            System.out.println("Email sent successfully...");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}