package utils;

import configs.dataPaths;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HtmlTestReport {
    private StringBuilder htmlContent;
    private int totalScenarios = 0;
    private int totalTests = 0;
    private int totalPassed = 0;
    private int totalFailed = 0;
    private Map<String, StringBuilder> scenariosMap = new HashMap<>();
    private Map<String, Boolean> scenarioStatus = new HashMap<>();
    private Map<String, String> scenarioBookingRefs = new HashMap<>();
    private Map<String, ScenarioStats> scenarioStats = new HashMap<>();

    private static class ScenarioStats {
        int total = 0;
        int passed = 0;
        int failed = 0;
        String bookingRef = "";
    }

    private final String CSS = """
            
            * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
                    }
                        
                    :root {
                        --primary-color: #2196F3;
                        --success-color: #4CAF50;
                        --danger-color: #F44336;
                        --neutral-color: #607D8B;
                    }
                        
                    body {
                        background: linear-gradient(135deg, #f6f9fc 0%, #edf2f7 100%);
                        min-height: 100vh;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        padding: 20px;
                    }
                        
                    .report-container {
                        background: white;
                        border-radius: 20px;
                        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                        width: 100%;
                        max-width: 400px;
                        padding: 24px;
                        position: relative;
                        overflow: hidden;
                    }
                        
                    .report-container::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: 0;
                        right: 0;
                        height: 4px;
                        background: linear-gradient(90deg, var(--primary-color), var(--success-color));
                    }
                        
                    .header {
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        margin-bottom: 24px;
                        position: relative;
                    }
                        
                    .logo {
                        margin-bottom: 16px;
                        position: relative;
                        padding: 10px;
                    }
                        
                    .logo::after {
                        content: '';
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -50%);
                        width: 120%;
                        height: 120%;
                        background: linear-gradient(135deg, rgba(33, 150, 243, 0.1), rgba(76, 175, 80, 0.1));
                        border-radius: 50%;
                        z-index: 0;
                    }
                        
                    .logo img {
                        height: 35px;
                        position: relative;
                        z-index: 1;
                        transition: transform 0.3s ease;
                    }
                        
                    .logo:hover img {
                        transform: scale(1.05);
                    }
                        
                    .title {
                        font-size: 26px;
                        font-weight: 700;
                        color: #1a202c;
                        text-align: center;
                        margin-bottom: 8px;
                        background: linear-gradient(90deg, #2196F3, #4CAF50);
                        -webkit-background-clip: text;
                        -webkit-text-fill-color: transparent;
                    }
                        
                    .timestamp {
                        color: #718096;
                        font-size: 14px;
                        text-align: center;
                        padding: 6px 12px;
                        background: #f7fafc;
                        border-radius: 20px;
                        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
                    }
                        
                    .stats-grid {
                        display: grid;
                        grid-template-columns: repeat(2, 1fr);
                        gap: 16px;
                        margin-bottom: 24px;
                    }
                        
                    .stat-card {
                        background: #f8fafc;
                        border-radius: 16px;
                        padding: 20px;
                        text-align: center;
                        transition: all 0.3s ease;
                        border: 1px solid rgba(226, 232, 240, 0.8);
                        position: relative;
                        overflow: hidden;
                    }
                        
                    .stat-card::before {
                        content: '';
                        position: absolute;
                        top: 0;
                        left: 0;
                        width: 100%;
                        height: 100%;
                        background: linear-gradient(45deg, rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0.2));
                        opacity: 0;
                        transition: opacity 0.3s ease;
                    }
                        
                    .stat-card:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
                    }
                        
                    .stat-card:hover::before {
                        opacity: 1;
                    }
                        
                    .stat-value {
                        font-size: 32px;
                        font-weight: 800;
                        margin-bottom: 8px;
                        position: relative;
                        transition: all 0.3s ease;
                    }
                        
                    .scenarios .stat-value { color: var(--primary-color); }
                    .tests .stat-value { color: var(--neutral-color); }
                    .passed .stat-value { color: var(--success-color); }
                    .failed .stat-value { color: var(--danger-color); }
                        
                    .stat-label {
                        font-size: 14px;
                        color: #718096;
                        font-weight: 500;
                        text-transform: uppercase;
                        letter-spacing: 0.5px;
                    }
                        
                    .search-container {
                        margin-bottom: 24px;
                        position: relative;
                    }
                        
                    .search-input {
                        width: 100%;
                        padding: 14px 20px;
                        border: 2px solid #e2e8f0;
                        border-radius: 12px;
                        font-size: 15px;
                        transition: all 0.3s ease;
                        background: #f8fafc;
                    }
                        
                    .search-input:focus {
                        outline: none;
                        border-color: var(--primary-color);
                        box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.1);
                        background: white;
                    }
                        
                    .scenarios-list {
                        display: flex;
                        flex-direction: column;
                        gap: 16px;
                    }
                        
                    .scenario-item {
                        background: #f8fafc;
                        border-radius: 12px;
                        padding: 20px;
                        cursor: pointer;
                        transition: all 0.3s ease;
                        border: 1px solid #e2e8f0;
                    }
                        
                    .scenario-item:hover {
                        background: white;
                        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
                        transform: translateY(-2px);
                    }
                        
                    .scenario-header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 12px;
                    }
                        
                    .scenario-name {
                        font-weight: 600;
                        color: #2d3748;
                        display: flex;
                        align-items: center;
                        gap: 8px;
                    }
                        
                    .scenario-name::before {
                        content: '▸';
                        color: var(--primary-color);
                        transition: transform 0.3s ease;
                    }
                        
                    .scenario-item:hover .scenario-name::before {
                        transform: rotate(90deg);
                    }
                        
                    .scenario-stats {
                        display: flex;
                        gap: 16px;
                        font-size: 14px;
                        color: #718096;
                    }
                        
                    .fail-badge {
                        background: linear-gradient(135deg, #ff6b6b, #ff8787);
                        color: white;
                        padding: 4px 12px;
                        border-radius: 20px;
                        font-size: 12px;
                        font-weight: 600;
                        letter-spacing: 0.5px;
                        box-shadow: 0 2px 4px rgba(255, 107, 107, 0.2);
                    }
                        
                    /* Desktop Styles */
                    @media (min-width: 768px) {
                        .report-container {
                            max-width: 1200px;
                            padding: 32px 40px;
                        }
                        
                        .header {
                            flex-direction: row;
                            justify-content: space-between;
                            margin-bottom: 40px;
                        }
                        
                        .logo {
                            margin-bottom: 0;
                        }
                        
                        .logo img {
                            height: 40px;
                        }
                        
                        .stats-grid {
                            grid-template-columns: repeat(4, 1fr);
                            margin-bottom: 40px;
                            gap: 24px;
                        }
                        
                        .stat-card {
                            padding: 24px;
                        }
                        
                        .stat-value {
                            font-size: 38px;
                        }
                        
                        .search-input {
                            padding: 16px 24px;
                        }
                        
                        .scenarios-list {
                            gap: 20px;
                        }
                        
                        .scenario-item {
                            padding: 24px;
                        }
                        
                        .scenario-stats {
                            font-size: 15px;
                        }
                    }
                        
                    /* Animations */
                    @keyframes slideInUp {
                        from {
                            opacity: 0;
                            transform: translateY(20px);
                        }
                        to {
                            opacity: 1;
                            transform: translateY(0);
                        }
                    }
                        
                    @keyframes pulse {
                        0% { transform: scale(1); }
                        50% { transform: scale(1.05); }
                        100% { transform: scale(1); }
                    }
                        
                    .stat-card {
                        animation: slideInUp 0.5s ease-out forwards;
                    }
                        
                    .scenario-item {
                        animation: slideInUp 0.5s ease-out forwards;
                    }
                        
                    .fail-badge {
                        animation: pulse 2s infinite;
                    }
                        
            """;

    private final String JAVASCRIPT = """
            function toggleTestCases(id) {
                const element = document.getElementById(id);
                element.style.display = element.style.display === 'none' ? 'block' : 'none';
            }
            
            function drawChart() {
                const ctx = document.getElementById('testChart').getContext('2d');
                new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: ['Passed (%d)', 'Failed (%d)'],
                        datasets: [{
                            data: [%d, %d],
                            backgroundColor: ['#4CAF50', '#FF6347'],
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                position: 'bottom'
                            }
                        }
                    }
                });
            }
            
            function searchScenarios() {
                const input = document.getElementById('searchInput').value.toLowerCase();
                const scenarios = document.getElementsByClassName('scenario');
                Array.from(scenarios).forEach(scenario => {
                    scenario.style.display = scenario.textContent.toLowerCase().includes(input) ? 'flex' : 'none';
                });
            }
            
            window.onload = drawChart;
            """;

    public HtmlTestReport() {
        createHTMLReport();
    }

    public void createHTMLReport() {
        htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>")
                .append("<html lang='en'><head>")
                .append("<meta charset='UTF-8'>")
                .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                .append("<title>Test Execution Report</title>")
                .append("<style>").append(CSS).append("</style>")
                .append("<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>")
                .append("</head><body>")

                .append("<div class='report-container'>")
                .append("<div class='header'>")
                .append("<div class='logo'>")
                .append("<img src='https://cdn1.travelstart.com/assets/images/travelstart-new-brand-logo.svg' alt='TravelStart Logo'>")
                .append("</div>")
                .append("<h1 class='title'>Test Execution Report</h1>")
                .append("<div class='timestamp'>")
                .append("<p>Generated on: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("</p>")
                .append("</div>")
                .append("</div>")

                .append("<div class='chart-container'><canvas id='testChart'></canvas></div>")


                .append("<div class='stats-grid'>")
                .append("<div class='stat-card scenarios'><div class='stat-value'>").append(totalScenarios).append("</div><div class='stat-label'>Total Scenarios</div></div>")
                .append("<div class='stat-card tests'><div class='stat-value'>").append(totalTests).append("</div><div class='stat-label'>Total Tests</div></div>")
                .append("<div class='stat-card passed'><div class='stat-value'>").append(totalPassed).append("</div><div class='stat-label'>Passed</div></div>")
                .append("<div class='stat-card failed'><div class='stat-value'>").append(totalFailed).append("</div><div class='stat-label'>Failed</div></div>")
                .append("</div>")

                .append("<div class='search-container'")
                .append("<input type='text' id='searchInput' class='search-input' onkeyup='searchScenarios()' placeholder='Search scenarios...'>")
                .append("</div>");

    }

    public synchronized void writeTestReport(String testScenarioID, String testCaseID,
                                             String testCaseSummary, String testStatus,
                                             String bookingRefOrCID, String testCaseBookingRef,
                                             String failureMessage) {
        // Initialize scenario data if new
        if (!scenariosMap.containsKey(testScenarioID)) {
            scenariosMap.put(testScenarioID, new StringBuilder());
            scenarioStatus.put(testScenarioID, true);
            scenarioBookingRefs.put(testScenarioID, bookingRefOrCID);
            scenarioStats.put(testScenarioID, new ScenarioStats());
            totalScenarios++;
        }

        ScenarioStats stats = scenarioStats.get(testScenarioID);
        stats.total++;
        totalTests++;

        if (testStatus.equalsIgnoreCase("pass")) {
            stats.passed++;
            totalPassed++;
        } else {
            stats.failed++;
            totalFailed++;
            scenarioStatus.put(testScenarioID, false);
        }
        stats.bookingRef = bookingRefOrCID;


//      Review upto 471 line




        StringBuilder scenarioContent = scenariosMap.get(testScenarioID);
        scenarioContent.append("<div class='test-case'>")
                .append("<p><strong>Test Case ID:</strong> ").append(testCaseID)
                .append("<br><strong>Summary:</strong> ").append(testCaseSummary)
                .append("<br><strong>Booking Reference:</strong> ").append(testCaseBookingRef)
                .append("<br><strong>Status:</strong> <span class='").append(testStatus.toLowerCase()).append("'>")
                .append(testStatus).append("</span>");

        if (!testStatus.equalsIgnoreCase("pass") && failureMessage != null && !failureMessage.isEmpty()) {
            scenarioContent.append("<div class='failure-message'>")
                    .append("<strong>Failure Message:</strong><br>")
                    .append(failureMessage.replace("\n", "<br>"))
                    .append("</div>");
        }

        scenarioContent.append("</p></div><hr>");
    }

    public synchronized String saveHTMLReport() throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = formatter.format(new Date());
        String fileName = dataPaths.dataBasePath + "\\TestResult\\TestReport_" + timestamp + ".html";

        // Update summary statistics
        htmlContent.append("<script>")
                .append("document.querySelector('.summary .stat-value:nth-child(1)').textContent = '").append(totalScenarios).append("';")
                .append("document.querySelector('.summary .stat-value:nth-child(2)').textContent = '").append(totalTests).append("';")
                .append("document.querySelector('.summary .stat-value.pass').textContent = '").append(totalPassed).append("';")
                .append("document.querySelector('.summary .stat-value.fail').textContent = '").append(totalFailed).append("';")
                .append("</script>");

        // Add scenarios with stats
        for (Map.Entry<String, StringBuilder> entry : scenariosMap.entrySet()) {
            String scenarioID = entry.getKey();
            StringBuilder scenarioContent = entry.getValue();
            boolean isPassing = scenarioStatus.get(scenarioID);
            ScenarioStats stats = scenarioStats.get(scenarioID);

            htmlContent.append("<div class='scenarios-list'>")
                    .append("<div class='scenario-item'>")
                    .append("<div class='scenario-name'")

                    .append("<span onclick=\"toggleTestCases('").append(scenarioID).append("')\">▶ ").append(scenarioID).append("</span>")
                    .append("<span class='scenario-stats'>")
                    .append("Total: ").append(stats.total)
                    .append(" | Passed: ").append(stats.passed)
                    .append(" | Failed: ").append(stats.failed)
                    .append("</span>")
                    .append("</div>")
                    .append("<span class='scenario-status status-").append(isPassing ? "pass" : "fail").append("'>")
                    .append(isPassing ? "PASS" : "FAIL").append("</span>")
                    .append("</div>")
                    .append("<div id='").append(scenarioID).append("' class='testcases'>")
                    .append("<div class='booking-ref'><strong>Booking Ref/ID:</strong> ").append(stats.bookingRef).append("</div>")
                    .append(scenarioContent.toString())
                    .append("</div>");
        }

        // Add JavaScript and close HTML
        htmlContent.append("<script>")
                .append(String.format(JAVASCRIPT, totalPassed, totalFailed, totalPassed, totalFailed))
                .append("</script>")
                .append("</div></body></html>");

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(htmlContent.toString());
        }

        return fileName;
    }
}