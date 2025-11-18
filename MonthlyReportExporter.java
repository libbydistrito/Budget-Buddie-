import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Map;
import java.util.Locale;

/**
 * Generates monthly budget reports in text format.
 * Exports monthly expense summaries, category breakdowns, and spending analysis to a text file.
 */
public class MonthlyReportExporter {

    private MainApp mainApp;

    /**
     * Constructs a MonthlyReportExporter with reference to the main application.
     * @param mainApp the MainApp instance to retrieve expense data from
     */
    public MonthlyReportExporter(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Exports the current month's budget report, opening a file chooser for user selection.
     * @param ownerStage the parent stage for the file chooser dialog
     */
    public void exportCurrentMonthReport(Stage ownerStage) {
        exportReportForMonth(ownerStage, YearMonth.now());
    }

    /**
     * Exports a specific month's budget report.
     * @param ownerStage the parent stage for the file chooser dialog
     * @param month the year-month to generate the report for
     */
    public void exportReportForMonth(Stage ownerStage, YearMonth month) {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save Monthly Report");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            String defaultName = String.format("BudgetBuddy_Report_%s_%d.txt",
                    month.getMonth().getDisplayName(TextStyle.SHORT, Locale.getDefault()), month.getYear());
            chooser.setInitialFileName(defaultName);
            File file = chooser.showSaveDialog(ownerStage);
            if (file == null) return;

            // Create report
            createTextReport(file, month);
            
            System.out.println("Monthly report exported to: " + file.getAbsolutePath());

        } catch (Exception ex) {
            // Failed to export report
        }
    }

    private void createTextReport(File outFile, YearMonth month) throws Exception {
        try (FileWriter writer = new FileWriter(outFile)) {
            writer.write("========================================\n");
            writer.write("          BUDGET BUDDY MONTHLY REPORT\n");
            writer.write("========================================\n\n");
            
            writer.write("Month: " + month.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + month.getYear() + "\n");
            writer.write("Generated: " + java.time.LocalDate.now() + "\n\n");
            
            // Summary section
            writer.write("---- SUMMARY ----\n");
            double totalSpending = mainApp.totalExpenses;
            double budget = mainApp.monthlyBudget;
            double remaining = budget - totalSpending;
            
            writer.write("Monthly Budget:      " + formatCurrency(budget) + "\n");
            writer.write("Total Spending:      " + formatCurrency(totalSpending) + "\n");
            writer.write("Remaining:           " + formatCurrency(Math.max(0, remaining)) + "\n");
            
            if (budget > 0) {
                double percentSpent = (totalSpending / budget) * 100;
                writer.write("Percentage Spent:    " + String.format("%.1f%%", percentSpent) + "\n");
            }
            
            writer.write("\n---- CATEGORY BREAKDOWN ----\n");
            if (mainApp.categoryExpenses != null && !mainApp.categoryExpenses.isEmpty()) {
                for (Map.Entry<String, Double> entry : mainApp.categoryExpenses.entrySet()) {
                    if (entry.getValue() > 0) {
                        writer.write(entry.getKey() + ":  " + formatCurrency(entry.getValue()) + "\n");
                    }
                }
            } else {
                writer.write("No category data available.\n");
            }
            
            writer.write("\n---- EXPENSES ----\n");
            if (mainApp.expenseListView != null && !mainApp.expenseListView.getItems().isEmpty()) {
                for (String item : mainApp.expenseListView.getItems()) {
                    writer.write(item + "\n");
                }
            } else {
                writer.write("No expenses recorded.\n");
            }
            
            writer.write("\n========================================\n");
            writer.write("End of Report\n");
            writer.write("========================================\n");
        }
    }

    private String formatCurrency(double amount) {
        return NumberFormat.getCurrencyInstance().format(amount);
    }
}
