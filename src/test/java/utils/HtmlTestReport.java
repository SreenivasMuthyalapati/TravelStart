package utils;

import configs.dataPaths;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlTestReport {

    private StringBuilder htmlContent;
    private int testCount = 0;

    public HtmlTestReport() {
        createHTMLReport();
    }

    // Method to initialize the HTML report structure
    public void createHTMLReport() {
        htmlContent = new StringBuilder();
        htmlContent.append("<html><head><title>Test Report</title>")
                .append("<style>")
                .append("table {width: 100%; border-collapse: collapse;}")
                .append("table, th, td {border: 1px solid black; padding: 8px; text-align: left;}")
                .append("th {background-color: #f2f2f2;}")
                .append("tr:nth-child(even) {background-color: #f9f9f9;}")
                .append("tr:nth-child(odd) {background-color: #fff;}")
                .append(".pass {color: green;}")
                .append(".fail {color: red;}")
                .append("</style></head><body>")
                .append("<h1>Test Report</h1>")
                .append("<table><tr>")
                .append("<th>Test Scenario ID</th>")
                .append("<th>Test Case ID</th>")
                .append("<th>Test Case Summary</th>")
                .append("<th>Test Status</th>")
                .append("<th>Booking Reference or Correlation ID</th>")
                .append("</tr>");
    }

    // Method to add a test case result to the HTML report
    public synchronized void writeTestReport(String testScenarioID, String testCaseID, String testCaseSummary, String testStatus, String bookingRefOrCID) {
        htmlContent.append("<tr>")
                .append("<td>").append(testScenarioID).append("</td>")
                .append("<td>").append(testCaseID).append("</td>")
                .append("<td>").append(testCaseSummary).append("</td>")
                .append("<td class='").append(testStatus.toLowerCase()).append("'>")
                .append(testStatus).append("</td>")
                .append("<td>").append(bookingRefOrCID).append("</td>")
                .append("</tr>");
        testCount++;
    }

    // Method to save the HTML report with a unique name based on the current timestamp
    public synchronized String saveHTMLReport() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = formatter.format(new Date());
        String fileName = dataPaths.dataBasePath + "\\TestResult\\TestReport_" + timestamp + ".html";

        // Closing the HTML structure
        htmlContent.append("</table><br><br>")
                .append("<p>Total Test Cases: ").append(testCount).append("</p>")
                .append("</body></html>");

        // Writing to the HTML file
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(htmlContent.toString());
        } catch (IOException e) {
            System.err.println("Failed to save HTML report: " + e.getMessage());
            throw e;
        }

        System.out.println("Test report generated: " + fileName);
        return fileName;
    }
}
