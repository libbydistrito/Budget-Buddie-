import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.security.MessageDigest;

public class MainApp extends Application {

    private class SavingsGoal {
        String name;
        double targetAmount;
        double savedAmount;
        LocalDate targetDate; 

        SavingsGoal(String name, double targetAmount, LocalDate targetDate) {
            this.name = name;
            this.targetAmount = targetAmount;
            this.savedAmount = 0;
            this.targetDate = targetDate;
        }
    }
    private class Theme {
        String name, primary, secondary, accent, background;

        Theme(String name, String primary, String secondary, String accent, String background) {
            this.name = name;
            this.primary = primary;
            this.secondary = secondary;
            this.accent = accent;
            this.background = background;
        }
    }

    private List<Theme> availableThemes = new ArrayList<>();

    double totalExpenses = 0;
    double monthlyBudget = 0;
    double monthlyExpenses = 0;
    ListView<String> expenseListView;
    private Stage primaryStage;
    private Label budgetLabel;
    private Label monthlyBudgetLabel;
    private Label spentLabel;
    private Label remainingLabel;
    private Label percentLabel;
    private Label forecastLabel;
    private ProgressBar budgetProgressBar;
    private ProgressBar goalProgress;
    private double currentBudget = 0;
    private PieChart pieChart;
    Map<String, Double> categoryExpenses = new HashMap<>();
    private int currentThemeIndex = 0;
    private final String[][] themes = {
        {"#FFFFFF", "#F0F0F0", "#404040", "#222222", "#000000"},
        {"#F5F5F5", "#EEEEEE", "#505050", "#333333", "#111111"},
        {"#F0F4F8", "#E1EBF5", "#1E3A5F", "#0F2D5C", "#081D3F"},
        {"#FFF8F3", "#F5E6D3", "#8B6F47", "#5C4033", "#3E2723"},
        {"#F0FFFA", "#D4EDDA", "#2D5F4F", "#1D4038", "#0F2B21"},
        {"#F8F4FF", "#E8DDF5", "#4A3B5C", "#2F2140", "#1A0D2E"},
        {"#FFF5F2", "#FFE4DB", "#8B4A4A", "#5D2E2E", "#3D1515"},
        {"#E3F2FD", "#BBDEFB", "#1A4D7A", "#0D2847", "#051020"},
        {"#E8F5E9", "#C8E6C9", "#1B5E20", "#0D3317", "#050F0A"},
        {"#2B2B2B", "#1F1F1F", "#E0E0E0", "#F5F5F5", "#FFFFFF"},
    };
    
    private final String[] themeNames = {
        "Clean White", "Light Grey", "Soft Blue", "Warm Beige", "Mint Green",
        "Soft Purple", "Coral Pink", "Ocean Blue", "Forest Green", "Deep Charcoal"
    };
    
    private VBox root;
    private MenuBar menuBar;
    private Text titleLabel;  
    private Theme currentAppliedTheme = null;  
    private boolean isDarkMode = true;  
    
    private double currentGoalAmount = 0;
    private int currentGoalDays = 0;
    private double currentSavedAmount = 0;
    
    private List<SavingsGoal> savingsGoals = new ArrayList<>();
    
    private String pinCode = "1234";
    private final String PIN_FILE = "pin.txt";
    
    private String[] tips = {
        "Bring your own lunch to save money!",
        "Use public transport whenever possible.",
        "Track your expenses daily to avoid surprises.",
        "Cut unnecessary subscriptions.",
        "Set a small weekly saving goal.",
        "Cook meals at home instead of eating out.",
        "Use a shopping list and stick to it.",
        "Compare prices before making purchases.",
        "Unsubscribe from marketing emails.",
        "Cancel unused gym memberships.",
        "Buy generic brands instead of name brands.",
        "Use cashback apps and credit card rewards.",
        "Turn off lights when leaving a room.",
        "Carpool with friends or coworkers.",
        "Meal prep on weekends to save time and money.",
        "Negotiate your phone or internet bill.",
        "Use the library for free books and movies.",
        "Shop during sales and use coupons.",
        "Buy used items when possible.",
        "Set up automatic savings transfers.",
        "Avoid impulse purchases by waiting 24 hours.",
        "Use a budget app to track spending.",
        "Pack snacks instead of buying them out.",
        "Walk or bike instead of driving short distances.",
        "Use reusable water bottles and bags.",
        "Switch to LED light bulbs.",
        "Take shorter showers to save water.",
        "Refinance your loans for better rates.",
        "Ask for discounts or price matches.",
        "Sell items you no longer need.",
        "Start a garden to grow your own food.",
        "Use public WiFi instead of using data.",
        "Plan vacations during off-season.",
        "Make coffee at home instead of buying it.",
        "Batch errands to save gas money.",
        "Use free entertainment options.",
        "Join loyalty programs at your favorite stores.",
        "Invest in quality items that last longer.",
        "Reduce energy usage by unplugging devices.",
        "Buy seasonal produce for lower prices.",
        "Learn to repair items instead of replacing them.",
        "Use streaming services with family to split costs.",
        "Negotiate medical bills.",
        "Use student discounts if applicable.",
        "Buy in bulk for items you use frequently.",
        "Track your daily spending on paper or app.",
        "Create a sinking fund for large purchases.",
        "Use free tax preparation services.",
        "Cancel or pause subscriptions monthly.",
        "Spend cash instead of credit to avoid overspending."
    };
    
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new File("budget_data.txt"))) {
            writer.println(monthlyBudget); 
            writer.println(totalExpenses); 
            for (String expense : expenseListView.getItems()) {
                writer.println(expense); 
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to save budget data: " + e.getMessage());
        }
    }
    
    private void loadData() {
        File file = new File("budget_data.txt");
        if (!file.exists()) return; 
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            monthlyBudget = Double.parseDouble(reader.readLine());
            totalExpenses = Double.parseDouble(reader.readLine());
            budgetLabel.setText("Current Budget: $" + monthlyBudget);

            String line;
            while ((line = reader.readLine()) != null) {
                expenseListView.getItems().add(line);
            }
        } catch (IOException | NumberFormatException e) {
            showAlert("Error", "Failed to load budget data: " + e.getMessage());
        }
    }
    
    private void initializeThemes() {
        availableThemes.add(new Theme("Sunset", "#FF6B6B", "#FFD93D", "#6BCB77", "#FFE5D9"));
        availableThemes.add(new Theme("Ocean", "#0D3B66", "#FAF0CA", "#F4D35E", "#EE6C4D"));
        availableThemes.add(new Theme("Forest", "#2A9D8F", "#E9C46A", "#F4A261", "#264653"));
        availableThemes.add(new Theme("Candy", "#FFB5E8", "#B28DFF", "#85E3FF", "#FFF5BA"));
        availableThemes.add(new Theme("Retro", "#FF6F61", "#6B5B95", "#88B04B", "#F7CAC9"));
        availableThemes.add(new Theme("Matcha Green", "#8FBC8F", "#6B8E23", "#A8D08D", "#0F0F0F"));
        availableThemes.add(new Theme("Lavender", "#B19CD9", "#DDA0DD", "#E6B3E6", "#F0E6FF"));
        availableThemes.add(new Theme("Coral Reef", "#FF6B9D", "#FFA502", "#C44569", "#FFE5E5"));
        availableThemes.add(new Theme("Midnight", "#0A0E27", "#16213E", "#0F3460", "#E94560"));
        availableThemes.add(new Theme("Peach", "#FFB3A7", "#FF8A80", "#FF6E40", "#FFF8F0"));
        availableThemes.add(new Theme("Emerald", "#00C853", "#1DE9B6", "#00BFA5", "#E8F5E9"));
        availableThemes.add(new Theme("Navy Blue", "#001A4D", "#0033CC", "#0052CC", "#E3F2FD"));
        availableThemes.add(new Theme("Rose Gold", "#B76E79", "#D4A574", "#F0E6D2", "#FFF5F0"));
        availableThemes.add(new Theme("Teal Dream", "#008080", "#20B2AA", "#AFEEEE", "#F0FFFF"));
        availableThemes.add(new Theme("Warm Sand", "#D2B48C", "#A0826D", "#8B7355", "#FFF8DC"));
        availableThemes.add(new Theme("Electric", "#00D9FF", "#00FF00", "#FFFF00", "#1A1A2E"));
        availableThemes.add(new Theme("Sunset Purple", "#6A0572", "#AB47BC", "#CE93D8", "#F3E5F5"));
        availableThemes.add(new Theme("Fresh Mint", "#00D4AA", "#00B894", "#00A86B", "#E0F7F4"));
        availableThemes.add(new Theme("Deep Ocean", "#0C1823", "#12263A", "#1A3A52", "#A0C4FF"));
        availableThemes.add(new Theme("Berry Burst", "#9C27B0", "#E1BEE7", "#F3E5F5", "#FFEBEE"));
        availableThemes.add(new Theme("Sunflower", "#FFD700", "#FFA500", "#FF8C00", "#FFFACD"));
        availableThemes.add(new Theme("Slate Grey", "#708090", "#2F4F4F", "#36454F", "#D3D3D3"));
        availableThemes.add(new Theme("Flamingo", "#FF1493", "#FF69B4", "#FFB6C1", "#FFF0F5"));
        availableThemes.add(new Theme("Jungle", "#228B22", "#32CD32", "#00FA9A", "#F0FFF0"));
        availableThemes.add(new Theme("Cosmic Purple", "#2D1B69", "#5B21B6", "#8B5CF6", "#EDE9FE"));
        availableThemes.add(new Theme("Honey Amber", "#FFA500", "#FFB30D", "#FFD700", "#FFFEF0"));
        availableThemes.add(new Theme("Cool Breeze", "#87CEEB", "#00BFFF", "#1E90FF", "#F0F8FF"));
        availableThemes.add(new Theme("Wine Red", "#722F37", "#A23B72", "#C41E3A", "#FFE6E6"));
        availableThemes.add(new Theme("Pistachio", "#93C572", "#A8D5BA", "#D4F1D4", "#F0FFF0"));
        availableThemes.add(new Theme("Charcoal", "#36454F", "#2F4F4F", "#4A4E69", "#E8E8E8"));
        availableThemes.add(new Theme("Bubblegum", "#FF6EC7", "#FF1493", "#FF69B4", "#FFE4E1"));
        availableThemes.add(new Theme("Bronze", "#CD7F32", "#B87333", "#A0522D", "#FFE4B5"));
        availableThemes.add(new Theme("Lime Green", "#32CD32", "#7FFF00", "#ADFF2F", "#F0FFF0"));
        availableThemes.add(new Theme("Royal Purple", "#7851A9", "#9932CC", "#BA55D3", "#F8F6FF"));
        availableThemes.add(new Theme("Copper", "#B87333", "#D4A574", "#CD7F32", "#FFF8DC"));
        availableThemes.add(new Theme("Sky Blue", "#87CEEB", "#4B9FD8", "#0087BE", "#E0F6FF"));
        availableThemes.add(new Theme("Fuchsia", "#FF00FF", "#DA70D6", "#EE82EE", "#F8F0FF"));
        availableThemes.add(new Theme("Khaki", "#F0E68C", "#DAA520", "#B8860B", "#FFFFF0"));
        availableThemes.add(new Theme("Indigo", "#4B0082", "#6A0DAD", "#9370DB", "#F0E6FF"));
        availableThemes.add(new Theme("Salmon", "#FA8072", "#FF7F50", "#FFB7A2", "#FFF0F5"));
        availableThemes.add(new Theme("Sage", "#87AE73", "#9CAF88", "#B2AC88", "#F5F5F0"));
        availableThemes.add(new Theme("Turquoise", "#40E0D0", "#30D5D5", "#00CED1", "#F0FFFF"));
        availableThemes.add(new Theme("Maroon", "#800000", "#A52A2A", "#C21E56", "#FFF0F5"));
        availableThemes.add(new Theme("Moss Green", "#556B2F", "#6B8E23", "#708238", "#F0FFF0"));
        availableThemes.add(new Theme("Periwinkle", "#CCCCFF", "#B0C4DE", "#9999FF", "#F5F5FF"));
        availableThemes.add(new Theme("Rust Orange", "#B7410E", "#FF8C42", "#FF6B35", "#FFE4C4"));
        availableThemes.add(new Theme("Seafoam", "#93E9BE", "#A8E6CF", "#C1F7DC", "#E0FFF7"));
        availableThemes.add(new Theme("Taupe", "#B38B6D", "#A39E93", "#8B7355", "#F5E6D3"));
    }

    private void applyDynamicTheme(Theme theme, VBox rootNode, Stage stage) {
        currentAppliedTheme = theme;  
        isDarkMode = true;  
        
        rootNode.setStyle("-fx-background-color: " + theme.background + ";");
        menuBar.setStyle(
            "-fx-background-color: " + theme.accent + ";" +
            "-fx-text-fill: " + theme.background + ";"
        );
        
        if (titleLabel != null) {
            titleLabel.setFill(new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web(theme.primary)),
                new Stop(1, Color.web(theme.secondary))
            ));
        }
        
        // Update expenseListView to match theme background
        if (expenseListView != null) {
            expenseListView.setStyle(
                "-fx-control-inner-background: " + theme.background + "; " +
                "-fx-text-fill: " + theme.primary + ";"
            );
        }
        
        updateNodeThemeRecursive(rootNode, theme);
    }

    private void updateNodeThemeRecursive(javafx.scene.layout.Pane pane, Theme theme) {
        if (pane == null || pane.getChildren() == null) return;
        
        for (Node child : pane.getChildren()) {
            if (child instanceof javafx.scene.layout.Pane) {
                javafx.scene.layout.Pane childPane = (javafx.scene.layout.Pane) child;
                String borderStyle = childPane.getStyle();
                
                if (borderStyle != null && borderStyle.contains("-fx-border-color")) {
                    borderStyle = borderStyle.replaceAll(
                        "-fx-border-color:\\s*[^;]*;?", 
                        "-fx-border-color: " + theme.primary + ";"
                    );
                    childPane.setStyle(borderStyle);
                }
                
                updateNodeThemeRecursive(childPane, theme);
            }
            
            if (child instanceof Label) {
                Label label = (Label) child;
                String style = label.getStyle();
                if (style != null && !style.isEmpty()) {
                    style = style.replaceAll(
                        "-fx-text-fill:\\s*#[0-9A-Fa-f]{6};?", 
                        "-fx-text-fill: " + theme.primary + ";"
                    );
                    label.setStyle(style);
                }
            }
            
            if (child instanceof Button) {
                Button button = (Button) child;
                String style = button.getStyle();
                if (style != null && !style.isEmpty()) {
                    style = style.replaceAll(
                        "linear-gradient\\([^)]*\\)", 
                        "linear-gradient(to bottom, " + theme.accent + ", " + theme.secondary + ")"
                    );
                    button.setStyle(style);
                }
            }
        }
    }

    private String invertColor(String hexColor) {
        // Convert hex color to RGB, invert, and convert back
        if (hexColor == null || !hexColor.startsWith("#")) return "#FFFFFF";
        
        try {
            String hex = hexColor.substring(1);
            int rgb = Integer.parseInt(hex, 16);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;
            
            // Invert RGB values
            r = 255 - r;
            g = 255 - g;
            b = 255 - b;
            
            return String.format("#%02X%02X%02X", r, g, b);
        } catch (Exception e) {
            return "#FFFFFF";
        }
    }

    private void updateNodeLightMode(javafx.scene.layout.Pane pane, String textColor) {
        if (pane == null || pane.getChildren() == null) return;
        
        for (Node child : pane.getChildren()) {
            if (child instanceof javafx.scene.layout.Pane) {
                updateNodeLightMode((javafx.scene.layout.Pane) child, textColor);
            }
            
            if (child instanceof Label) {
                Label label = (Label) child;
                String style = label.getStyle();
                if (style != null) {
                    style = style.replaceAll(
                        "-fx-text-fill:\\s*#[0-9A-Fa-f]{6};?",
                        "-fx-text-fill: " + textColor + ";"
                    );
                    label.setStyle(style);
                }
            }
        }
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeThemes();
        loadPIN();
        showPINScreen(primaryStage);
    }
    
    private void showPINScreen(Stage primaryStage) {
        primaryStage.setTitle("BudgetBuddie");
        primaryStage.setWidth(450);
        primaryStage.setHeight(650);
        
        VBox pinRoot = new VBox(30);
        pinRoot.setAlignment(Pos.TOP_CENTER);
        pinRoot.setStyle("-fx-background: #000000; -fx-padding: 50 40 40 40;");
        
        Text titleText = new Text("BudgetBuddie");
        titleText.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 48));
        titleText.setFill(Color.web("#FFFFFF"));
        titleText.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        
        Text subtitleText = new Text("Enter Your PIN");
        subtitleText.setFont(Font.font("Helvetica Neue", 16));
        subtitleText.setFill(Color.web("#C0E8D4"));
        
        VBox titleBox = new VBox(8, titleText, subtitleText);
        titleBox.setAlignment(Pos.CENTER);
        
        HBox pinDisplayBox = new HBox(12);
        pinDisplayBox.setAlignment(Pos.CENTER);
        pinDisplayBox.setStyle("-fx-padding: 20; -fx-background-color: rgba(255,255,255,0.08); -fx-border-color: #FF0000; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12;");
        
        Label dot1 = new Label("●");
        Label dot2 = new Label("●");
        Label dot3 = new Label("●");
        Label dot4 = new Label("●");
        Label[] dots = {dot1, dot2, dot3, dot4};
        
        for (Label dot : dots) {
            dot.setFont(Font.font("Helvetica Neue", 32));
            dot.setTextFill(Color.web("#FF0000"));
            pinDisplayBox.getChildren().add(dot);
        }
        
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.web("#FFB84D"));
        errorLabel.setFont(Font.font("Helvetica Neue", 12));
        errorLabel.setStyle("-fx-text-alignment: center;");
        
        VBox keypadBox = new VBox(12);
        keypadBox.setStyle("-fx-padding: 10;");
        
        HBox row1 = createNumpadRow(new int[]{1, 2, 3});
        HBox row2 = createNumpadRow(new int[]{4, 5, 6});
        HBox row3 = createNumpadRow(new int[]{7, 8, 9});
        
        HBox row4 = new HBox(12);
        row4.setAlignment(Pos.CENTER);
        Button zeroButton = createNumpadButton("0");
        Button clearButton = createClearButton();
        row4.getChildren().addAll(new Label(" "), zeroButton, clearButton);
        
        keypadBox.getChildren().addAll(row1, row2, row3, row4);
        
        StringBuilder pinEntry = new StringBuilder();
        
        for (Node node : row1.getChildren()) {
            if (node instanceof Button) setupNumpadButton((Button) node, pinEntry, dots, errorLabel);
        }
        for (Node node : row2.getChildren()) {
            if (node instanceof Button) setupNumpadButton((Button) node, pinEntry, dots, errorLabel);
        }
        for (Node node : row3.getChildren()) {
            if (node instanceof Button) setupNumpadButton((Button) node, pinEntry, dots, errorLabel);
        }
        setupNumpadButton(zeroButton, pinEntry, dots, errorLabel);
        
        clearButton.setOnAction(e -> {
            pinEntry.setLength(0);
            for (Label dot : dots) dot.setTextFill(Color.web("#4CAA82"));
            errorLabel.setText("");
        });
        
        Button enterButton = new Button("ENTER");
        enterButton.setStyle("-fx-padding: 14 50; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #CC0000, #990000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.4), 8, 0, 0, 2);");
        enterButton.setPrefWidth(180);
        
        enterButton.setOnMouseEntered(e -> {
            enterButton.setStyle("-fx-padding: 14 50; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #FF3333, #CC0000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.6), 12, 0, 0, 3);");
        });
        
        enterButton.setOnMouseExited(e -> {
            enterButton.setStyle("-fx-padding: 14 50; -fx-font-size: 14; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #CC0000, #990000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.4), 8, 0, 0, 2);");
        });
        
        enterButton.setOnAction(e -> {
            if (pinEntry.toString().equals(pinCode)) {
                openMainApp(primaryStage);
            } else if (pinEntry.length() == 4) {
                errorLabel.setText("❌ Incorrect PIN");
                pinEntry.setLength(0);
                for (Label dot : dots) dot.setTextFill(Color.web("#4CAA82"));
            }
        });
        
        HBox enterButtonBox = new HBox();
        enterButtonBox.setAlignment(Pos.CENTER);
        enterButtonBox.getChildren().add(enterButton);
        
        Button settingsButton = new Button("⚙");
        settingsButton.setStyle("-fx-padding: 8 12; -fx-font-size: 16; -fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        
        settingsButton.setOnMouseEntered(e -> {
            settingsButton.setStyle("-fx-padding: 8 12; -fx-font-size: 16; -fx-background-color: rgba(204,0,0,0.2); -fx-text-fill: #000000; -fx-border-color: #FF3333; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        });
        
        settingsButton.setOnMouseExited(e -> {
            settingsButton.setStyle("-fx-padding: 8 12; -fx-font-size: 16; -fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");
        });
        
        settingsButton.setOnAction(e -> showSetNewPINDialog(primaryStage, errorLabel));
        
        HBox settingsBox = new HBox();
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.getChildren().add(settingsButton);
        
        pinRoot.getChildren().addAll(titleBox, pinDisplayBox, errorLabel, keypadBox, enterButtonBox, settingsBox);
        
        Scene scene = new Scene(pinRoot);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox createNumpadRow(int[] numbers) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER);
        for (int num : numbers) {
            row.getChildren().add(createNumpadButton(String.valueOf(num)));
        }
        return row;
    }
    
    private Button createNumpadButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.15); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand;");
        
        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.4); -fx-text-fill: #000000; -fx-border-color: #FF3333; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.3), 6, 0, 0, 1);");
        });
        
        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.15); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand;");
        });
        
        return btn;
    }
    
    private Button createClearButton() {
        Button btn = new Button("⌫");
        btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.15); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand;");
        
        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.4); -fx-text-fill: #000000; -fx-border-color: #FF3333; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.3), 6, 0, 0, 1);");
        });
        
        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-padding: 16; -fx-font-size: 18; -fx-font-weight: bold; -fx-background-color: rgba(204,0,0,0.15); -fx-text-fill: #000000; -fx-border-color: #CC0000; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12; -fx-min-width: 70; -fx-min-height: 70; -fx-cursor: hand;");
        });
        
        return btn;
    }
    
    private void setupNumpadButton(Button btn, StringBuilder pinEntry, Label[] dots, Label errorLabel) {
        btn.setOnAction(e -> {
            if (pinEntry.length() < 4) {
                pinEntry.append(btn.getText());
                dots[pinEntry.length() - 1].setTextFill(Color.web("#FF6B6B"));
                errorLabel.setText("");
                
                if (pinEntry.length() == 4 && pinEntry.toString().equals(pinCode)) {
                    openMainApp((Stage) btn.getScene().getWindow());
                }
            }
        });
    }
    
    private void showSetNewPINDialog(Stage owner, Label errorLabel) {
        Stage dialog = new Stage();
        dialog.setTitle("Set New PIN");
        dialog.setWidth(400);
        dialog.setHeight(350);
        
        VBox dialogRoot = new VBox(20);
        dialogRoot.setAlignment(Pos.CENTER);
        dialogRoot.setStyle("-fx-background: #000000; -fx-padding: 30;");
        
        Label titleLabel = new Label("Set New 4-Digit PIN");
        titleLabel.setFont(Font.font("Helvetica Neue", FontWeight.BOLD, 18));
        titleLabel.setTextFill(Color.web("#FFFFFF"));
        
        TextField newPINField = new TextField();
        newPINField.setPromptText("Enter new PIN");
        newPINField.setStyle("-fx-padding: 12; -fx-font-size: 14; -fx-control-inner-background: rgba(200,200,200,0.15); -fx-text-fill: #000000; -fx-prompt-text-fill: #666666; -fx-border-color: #999999; -fx-border-radius: 8; -fx-background-radius: 8;");
        newPINField.setPrefHeight(45);
        
        Label dialogErrorLabel = new Label("");
        dialogErrorLabel.setTextFill(Color.web("#FFB84D"));
        dialogErrorLabel.setFont(Font.font("Helvetica Neue", 12));
        
        Button confirmButton = new Button("UPDATE PIN");
        confirmButton.setStyle("-fx-padding: 12 40; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #CC0000, #990000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.4), 8, 0, 0, 2);");
        confirmButton.setPrefWidth(150);
        
        confirmButton.setOnMouseEntered(e -> {
            confirmButton.setStyle("-fx-padding: 12 40; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #FF3333, #CC0000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.6), 12, 0, 0, 3);");
        });
        
        confirmButton.setOnMouseExited(e -> {
            confirmButton.setStyle("-fx-padding: 12 40; -fx-font-size: 13; -fx-font-weight: bold; -fx-background-color: linear-gradient(to bottom, #CC0000, #990000); -fx-text-fill: #000000; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(gaussian, rgba(204,0,0,0.4), 8, 0, 0, 2);");
        });
        
        confirmButton.setOnAction(e -> {
            String newPIN = newPINField.getText().trim();
            
            if (newPIN.isEmpty()) {
                dialogErrorLabel.setText("Please enter a PIN");
                return;
            }
            
            if (newPIN.length() != 4) {
                dialogErrorLabel.setText("PIN must be exactly 4 digits");
                return;
            }
            
            if (!newPIN.matches("\\d{4}")) {
                dialogErrorLabel.setText("PIN must contain only numbers");
                return;
            }
            
            pinCode = newPIN;
            savePIN();
            errorLabel.setText("✓ PIN updated successfully");
            errorLabel.setTextFill(Color.web("#40C98B"));
            dialog.close();
        });
        
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(confirmButton);
        
        dialogRoot.getChildren().addAll(titleLabel, newPINField, dialogErrorLabel, buttonBox);
        Scene scene = new Scene(dialogRoot);
        dialog.setScene(scene);
        dialog.show();
    }
    
    private void loadPIN() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PIN_FILE))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                pinCode = line.trim();
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
    
    private void savePIN() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PIN_FILE))) {
            writer.println(pinCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainApp(Stage stage) {
        expenseListView = new ListView<>();
        
        expenseListView.setCellFactory(lv -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 8; " +
                        "-fx-background-color: #505050; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-font-weight: 500; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1);"
                    );
                }
            }

            private String getExpenseColor(String item) {
                if (item.contains("[Income]")) {
                    return "#BBBBBB"; 
                } else if (item.contains("Food")) {
                    return "#AAAAAA";
                } else if (item.contains("Transport")) {
                    return "#999999"; 
                } else if (item.contains("Entertainment")) {
                    return "#888888"; 
                }
                return "#777777"; 
            }
        });
        
        expenseListView.setStyle(
            "-fx-background-color: #404040;" +
            "-fx-text-fill: #000000;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 8;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );
        
        expenseListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
        
        Text titleLabel = new Text("Budget Buddie");
        titleLabel.setFont(Font.font("Helvetica Neue", javafx.scene.text.FontWeight.BOLD, 52));

        titleLabel.setFill(Color.web("#FFFFFF"));

        DropShadow shadow = new DropShadow();
        shadow.setRadius(8);
        shadow.setOffsetY(3);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        titleLabel.setEffect(shadow);
        shadow.setOffsetY(4);
        shadow.setRadius(8);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        titleLabel.setEffect(shadow);
        
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        
        budgetLabel = new Label("Current Budget: $0.00");
        budgetLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #000000; -fx-font-weight: 500;");
        
        forecastLabel = new Label("Forecast: N/A");
        forecastLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #000000; -fx-font-weight: 500;");
        
        budgetProgressBar = new ProgressBar(0);
        budgetProgressBar.setPrefHeight(8);
        budgetProgressBar.setStyle("-fx-accent: #4CAF50;");
        
        Label goalLabel = new Label("Money Saved: $0.00");
        goalLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500; -fx-text-fill: #000000;");

        goalProgress = new javafx.scene.control.ProgressBar(0);
        goalProgress.setPrefWidth(250);

        Label savedLabel = new Label("Saved: $0.00");
        savedLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: 500; -fx-text-fill: #000000;");

        TextField addMoneyInput = new TextField();
        addMoneyInput.setPromptText("Enter amount");
        addMoneyInput.setPrefWidth(150);

        Button addMoneyButton = new Button("Add Money");
        addMoneyButton.setStyle(
            "-fx-font-size: 11; " +
            "-fx-padding: 6 12; " +
            "-fx-background-color: #FFFFFF; " +
            "-fx-text-fill: #000000; " +
            "-fx-border-color: #000000; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 5;"
        );

        Button removeMoneyButton = new Button("Remove Money");
        removeMoneyButton.setStyle(
            "-fx-font-size: 11; " +
            "-fx-padding: 6 12; " +
            "-fx-background-color: #000000; " +
            "-fx-text-fill: white; " +
            "-fx-border-radius: 5;"
        );

        addMoneyButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(addMoneyInput.getText());
                if (amount <= 0) {
                    addMoneyInput.setStyle("-fx-border-color: red;");
                    return;
                }

                currentSavedAmount += amount;
                savedLabel.setText(String.format("Saved: $%.2f", currentSavedAmount));

                double progress = Math.min(currentSavedAmount / currentGoalAmount, 1.0);
                goalProgress.setProgress(progress);

                addMoneyInput.clear();
            } catch (NumberFormatException ex) {
                addMoneyInput.setStyle("-fx-border-color: red;");
            }
        });

        removeMoneyButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(addMoneyInput.getText());
                if (amount <= 0) {
                    addMoneyInput.setStyle("-fx-border-color: red;");
                    return;
                }

                if (amount > currentSavedAmount) {
                    addMoneyInput.setStyle("-fx-border-color: red;");
                    return;
                }

                currentSavedAmount -= amount;
                savedLabel.setText(String.format("Saved: $%.2f", currentSavedAmount));

                double progress = Math.min(currentSavedAmount / currentGoalAmount, 1.0);
                goalProgress.setProgress(progress);

                addMoneyInput.clear();
                addMoneyInput.setStyle("");
            } catch (NumberFormatException ex) {
                addMoneyInput.setStyle("-fx-border-color: red;");
            }
        });

        javafx.scene.layout.HBox addMoneyBox = new javafx.scene.layout.HBox(10, addMoneyInput, addMoneyButton, removeMoneyButton);
        addMoneyBox.setAlignment(Pos.CENTER);

        ListView<String> goalsListView = new ListView<>();
        goalsListView.setPrefHeight(150);
        goalsListView.setPrefWidth(350);
        goalsListView.setStyle(
            "-fx-background-color: #404040;" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;" +
            "-fx-padding: 8;" +
            "-fx-text-fill: #000000;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );
        goalsListView.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

        Button addGoalButton = new Button("Add New Goal");
        addGoalButton.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-text-fill: #000000;" +
            "-fx-border-color: #000000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 8 16;" +
            "-fx-font-weight: 600;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;" +
            "-fx-focus-traversable: false;"
        );
        addGoalButton.setFocusTraversable(false);
        
        addGoalButton.setOnAction(e -> {
            Stage goalPopup = new Stage();

            Label nameLabel = new Label("Goal Name:");
            TextField nameInput = new TextField();

            Label amountLabel = new Label("Target Amount:");
            TextField amountInput = new TextField();

            Label dateLabel = new Label("Target Date:");
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(LocalDate.now()); 

            Button submitGoal = new Button("Add Goal");
            submitGoal.setOnAction(ev -> {
                try {
                    String name = nameInput.getText().trim();
                    String amountText = amountInput.getText().trim();
                    
                    if (name.isEmpty()) {
                        nameInput.setStyle("-fx-border-color: red;");
                        return;
                    }
                    if (amountText.isEmpty()) {
                        amountInput.setStyle("-fx-border-color: red;");
                        return;
                    }
                    
                    double target = Double.parseDouble(amountText);
                    LocalDate date = datePicker.getValue();
                    
                    if (target <= 0) {
                        amountInput.setStyle("-fx-border-color: red;");
                        return;
                    }
                    
                    SavingsGoal newGoal = new SavingsGoal(name, target, date);
                    savingsGoals.add(newGoal);
                    
                    goalsListView.getItems().add(String.format("%s: $%.2f / $%.2f by %s", 
                        name, 0.0, target, date));
                    goalPopup.close();
                } catch (NumberFormatException ex) {
                    amountInput.setStyle("-fx-border-color: red;");
                }
            });

            VBox popupLayout = new VBox(10, 
                nameLabel, nameInput, 
                amountLabel, amountInput, 
                dateLabel, datePicker,
                submitGoal);
            popupLayout.setPadding(new Insets(15));
            popupLayout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(popupLayout, 300, 280);
            goalPopup.setScene(scene);
            goalPopup.setTitle("Add Savings Goal");
            goalPopup.show();
        });
        
        Button addToGoalButton = new Button("Add to Goal");
        addToGoalButton.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-text-fill: #000000;" +
            "-fx-border-color: #000000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 8 16;" +
            "-fx-font-weight: 600;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;" +
            "-fx-focus-traversable: false;"
        );
        addToGoalButton.setFocusTraversable(false);
        
        Button removeGoalButton = new Button("Remove Goal");
        removeGoalButton.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 8 16;" +
            "-fx-font-weight: 600;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;" +
            "-fx-focus-traversable: false;"
        );
        removeGoalButton.setFocusTraversable(false);
        
        removeGoalButton.setOnAction(e -> {
            int selectedIndex = goalsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                savingsGoals.remove(selectedIndex);
                goalsListView.getItems().remove(selectedIndex);
            } else {
                showAlert("Warning", "Please select a goal to remove");
            }
        });

        addToGoalButton.setOnAction(e -> {
            int selectedIndex = goalsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                SavingsGoal selectedGoal = savingsGoals.get(selectedIndex);
                
                Stage addMoneyPopup = new Stage();
                Label amountLabel = new Label("Enter amount to add to " + selectedGoal.name + ":");
                TextField amountInput = new TextField();
                amountInput.setPromptText("Amount");
                
                Button submitButton = new Button("Add");
                submitButton.setStyle(
                    "-fx-font-size: 11; " +
                    "-fx-padding: 6 12; " +
                    "-fx-background-color: linear-gradient(to bottom, #4CAF50, #45a049); " +
                    "-fx-text-fill: white; " +
                    "-fx-border-radius: 5;"
                );
                
                submitButton.setOnAction(ev -> {
                    try {
                        double addedAmount = Double.parseDouble(amountInput.getText());
                        if (addedAmount <= 0) {
                            amountInput.setStyle("-fx-border-color: red;");
                            return;
                        }
                        
                        selectedGoal.savedAmount += addedAmount;
                        
                        goalsListView.getItems().set(selectedIndex,
                            String.format("%s: $%.2f / $%.2f by %s",
                                selectedGoal.name,
                                selectedGoal.savedAmount,
                                selectedGoal.targetAmount,
                                selectedGoal.targetDate
                            )
                        );
                        addMoneyPopup.close();
                    } catch (NumberFormatException ex) {
                        amountInput.setStyle("-fx-border-color: red;");
                    }
                });
                
                VBox layout = new VBox(10, amountLabel, amountInput, submitButton);
                layout.setPadding(new Insets(15));
                layout.setAlignment(Pos.CENTER);
                
                Scene scene = new Scene(layout, 300, 150);
                addMoneyPopup.setScene(scene);
                addMoneyPopup.setTitle("Add Money to Goal");
                addMoneyPopup.show();
            } else {
                showAlert("Warning", "Please select a goal first");
            }
        });
        
        loadData();
        
        Button resetButton = new Button("Reset Budget");
        String buttonStyle = 
            "-fx-background-color: linear-gradient(to bottom, #333333, #1A1A1A);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;" +
            "-fx-padding: 8 16;" +
            "-fx-font-weight: 600;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2);" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;" +
            "-fx-focus-traversable: false;";
        
        resetButton.setStyle(buttonStyle);
        resetButton.setFocusTraversable(false);
        
        resetButton.setOnAction(e -> {
            monthlyBudget = 0;
            totalExpenses = 0;
            budgetLabel.setText("Current Budget: $0.00");
            forecastLabel.setText("Forecast: N/A");
            budgetProgressBar.setProgress(0);
            expenseListView.getItems().clear();
            categoryExpenses.clear();
            updatePieChart();
        });
        
        expenseListView.setStyle(
            "-fx-control-inner-background: #2B2B2B; " +
            "-fx-text-fill: #000000;"
        );
        
        budgetProgressBar = new ProgressBar(0);
        budgetProgressBar.setPrefHeight(15);
        budgetProgressBar.setStyle("-fx-accent: #00DD00;");
        
        pieChart = new PieChart();
        pieChart.setTitle("Spending by Category");
        pieChart.setStyle("-fx-text-fill: #ffffff;");
        pieChart.setPrefHeight(250);
        
        menuBar = new MenuBar();
        menuBar.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #F5F5F5, #E0E0E0);" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #2F3E34;"
        );
        
        Menu budgetMenu = new Menu("Budget");
        
        MenuItem monthlyBudgetItem = new MenuItem("Monthly Budget");
        budgetMenu.getItems().add(monthlyBudgetItem);
        
        monthlyBudgetItem.setOnAction(e -> {
            Stage popup = new Stage();
            Label popupLabel = new Label("Enter monthly budget:");
            popupLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 13;");
            
            TextField budgetInput = new TextField();
            Button setButton = new Button("Set Budget");
            setButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #555555, #404040); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 8 18; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0.3, 1, 1);"
            );
            
            setButton.setOnAction(ev -> {
                try {
                    monthlyBudget = Double.parseDouble(budgetInput.getText());
                    totalExpenses = 0;
                    double remainingBudget = monthlyBudget - totalExpenses;
                    budgetLabel.setText(String.format("Current Budget: $%.2f (Spent: $%.2f)", remainingBudget, totalExpenses));
                    budgetProgressBar.setProgress(0);
                    forecastLabel.setText("Forecast: N/A");
                    updateBudgetDisplay();
                    popup.close();
                } catch (NumberFormatException ex) {
                    budgetInput.setStyle("-fx-border-color: red;");
                }
            });

            HBox inputRow = new HBox(10, budgetInput, setButton);
            inputRow.setPadding(new Insets(10));
            VBox popupLayout = new VBox(10, popupLabel, inputRow);
            popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15; -fx-text-fill: #000000;");
            popupLayout.setPadding(new Insets(10));
            
            Scene popupScene = new Scene(popupLayout, 350, 120);
            popup.setTitle("Monthly Budget");
            popup.setScene(popupScene);
            popup.show();
        });
        
        MenuItem addExpenseItem = new MenuItem("Add Expense");
        budgetMenu.getItems().add(addExpenseItem);
        
        addExpenseItem.setOnAction(e -> {
            Stage expensePopup = new Stage();

            Label expenseLabel = new Label("Enter expense amount:");
            expenseLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 13;");

            TextField expenseInput = new TextField();

            Label typeLabel = new Label("Expense type:");
            typeLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 13;");
            
            ComboBox<String> categoryComboBox = new ComboBox<>();
            categoryComboBox.getItems().addAll(
                "Auto Insurance",
                "Car Maintenance & Repairs",
                "Childcare & Education",
                "Clothing & Accessories",
                "Credit Card Payments",
                "Debt Repayment (Loans)",
                "Dining Out",
                "Entertainment & Recreation",
                "Fitness & Gym",
                "Gifts & Celebrations",
                "Groceries & Food",
                "Healthcare & Medical",
                "Home Maintenance & Repairs",
                "Household Supplies",
                "Internet & Cable",
                "Investments",
                "Laundry & Dry Cleaning",
                "Mortgage or Rent",
                "Other",
                "Parking & Tolls",
                "Pet Care",
                "Phone Bill",
                "Public Transportation",
                "Savings Contributions",
                "Streaming Services & Subscriptions",
                "Taxes",
                "Transportation (Fuel/Bus/Train)",
                "Travel & Vacations",
                "Utilities (Electricity, Water, Gas)",
                "Work or School Supplies"
            );
            categoryComboBox.setEditable(true);
            categoryComboBox.setValue("Groceries & Food");
            categoryComboBox.setStyle("-fx-text-fill: #000000; -fx-control-inner-background: #404040; -fx-padding: 8;");

            Label dateLabel = new Label("Select Date:");
            dateLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 13;");
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(LocalDate.now()); 

            Button addButton = new Button("Add");
            addButton.setStyle("-fx-background-color: linear-gradient(to bottom, #555555, #404040); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 18;");

            addButton.setOnAction(ev -> {
                try {
                    double expense = Double.parseDouble(expenseInput.getText());
                    totalExpenses += expense;
                    
                    LocalDate expenseDate = datePicker.getValue();
                    
                    String customType = categoryComboBox.getValue();
                    if (customType == null || customType.isEmpty()) {
                        customType = "Other";
                    }
                    
                    double roundedExpense = Math.round(expense * 100.0) / 100.0;
                    categoryExpenses.put(customType, categoryExpenses.getOrDefault(customType, 0.0) + roundedExpense);
                    
                    updateBudgetDisplay();
                    expenseListView.getItems().add(customType + " Expense: $" + String.format("%.2f", roundedExpense) + " on " + expenseDate);
                    
                    double remainingBudget = monthlyBudget - totalExpenses;
                    if (remainingBudget < 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Over Budget!");
                        alert.setHeaderText("You have exceeded your monthly budget!");
                        alert.setContentText("Try to reduce expenses!");
                        alert.showAndWait();
                    }
                    
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), expenseListView);
                    fadeIn.setFromValue(0.3);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                    
            
                    if (monthlyBudget > 0) {
                        double progress = Math.min(totalExpenses / monthlyBudget, 1.0);
                        budgetProgressBar.setProgress(progress);
                    }
                    
                    if (currentGoalAmount > 0) {
                        double progress = Math.min(totalExpenses / currentGoalAmount, 1.0);
                        goalProgress.setProgress(progress);
                    }
                    
                    updatePieChart();
                    
                    updateForecast(forecastLabel, remainingBudget);
                    
                    expensePopup.close();
                } catch (NumberFormatException ex) {
                    expenseInput.setStyle("-fx-border-color: red;");
                }
            });

            HBox inputRow = new HBox(10, expenseInput);
            inputRow.setPadding(new Insets(10));
            
            HBox typeRow = new HBox(10, typeLabel, categoryComboBox);
            typeRow.setPadding(new Insets(10));

            HBox dateRow = new HBox(10, dateLabel, datePicker);
            dateRow.setPadding(new Insets(10));
            
            HBox buttonRow = new HBox(10, addButton);
            buttonRow.setAlignment(Pos.CENTER);
            buttonRow.setPadding(new Insets(10));

            VBox popupLayout = new VBox(10, expenseLabel, inputRow, typeRow, dateRow, buttonRow);
            popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15; -fx-text-fill: #000000;");
            popupLayout.setPadding(new Insets(10));

            Scene popupScene = new Scene(popupLayout, 450, 250);

            expensePopup.setTitle("Add Expense");
            expensePopup.setScene(popupScene);
            expensePopup.show();
        });
        
        MenuItem addIncomeItem = new MenuItem("Add Income");
        budgetMenu.getItems().add(addIncomeItem);

        addIncomeItem.setOnAction(e -> {
            Stage incomePopup = new Stage();

            Label incomeLabel = new Label("Enter income amount:");

            TextField incomeInput = new TextField();

            ComboBox<String> incomeCategory = new ComboBox<>();
            incomeCategory.getItems().addAll("Paycheck", "Freelance", "Gift", "Other");
            incomeCategory.setValue("Paycheck");

            Button addIncomeButton = new Button("Add Income");

            addIncomeButton.setOnAction(ev -> {
                try {
                    double income = Double.parseDouble(incomeInput.getText());
                    String category = incomeCategory.getValue();

                    monthlyBudget += income;
                    double remainingBudget = monthlyBudget - totalExpenses;
                    budgetLabel.setText(String.format("Current Budget: $%.2f (Spent: $%.2f)", remainingBudget, totalExpenses));

                    expenseListView.getItems().add("[Income] " + category + ": +$" + income);

                    if (monthlyBudget > 0) {
                        double progress = Math.min(totalExpenses / monthlyBudget, 1.0);
                        budgetProgressBar.setProgress(progress);
                    }

                    updateForecast(forecastLabel, remainingBudget);

                    incomePopup.close();
                } catch (NumberFormatException ex) {
                    incomeInput.setStyle("-fx-border-color: red;");
                }
            });

            HBox inputRow = new HBox(10, incomeInput, incomeCategory, addIncomeButton);
            inputRow.setPadding(new Insets(10));
            VBox popupLayout = new VBox(10, incomeLabel, inputRow);
            popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15; -fx-text-fill: #000000;");
            popupLayout.setPadding(new Insets(10));

            Scene popupScene = new Scene(popupLayout, 380, 120);
            incomePopup.setScene(popupScene);
            incomePopup.setTitle("Add Income");
            incomePopup.show();
        });
        
        MenuItem weeklyBudgetItem = new MenuItem("Weekly Budget");
        budgetMenu.getItems().add(weeklyBudgetItem);
        
        weeklyBudgetItem.setOnAction(e -> {
            Stage popup = new Stage();
            
            Label popupLabel = new Label("Enter weekly budget:");
            
            TextField budgetInput = new TextField();
            
            Button setButton = new Button("Set Budget");
            setButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #A6C48A, #6B8E23); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 8 18; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0.3, 1, 1);"
            );
            
            setButton.setOnAction(ev -> {
                try {
                    monthlyBudget = Double.parseDouble(budgetInput.getText());
                    totalExpenses = 0;
                    double remainingBudget = monthlyBudget - totalExpenses;
                    budgetLabel.setText(String.format("Current Budget: $%.2f (Spent: $%.2f)", remainingBudget, totalExpenses));
                    budgetProgressBar.setProgress(0);
                    forecastLabel.setText("Forecast: N/A");
                    popup.close(); 
                } catch (NumberFormatException ex) {
                    budgetInput.setStyle("-fx-border-color: red;");
                }
            });
            
            HBox inputRow = new HBox(10, budgetInput, setButton);
            inputRow.setPadding(new Insets(10));
            
            VBox popupLayout = new VBox(10, popupLabel, inputRow);
            popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15;");
            popupLayout.setPadding(new Insets(10));
            
            Scene popupScene = new Scene(popupLayout, 350, 120);
            
            popup.setTitle("Weekly Budget");
            popup.setScene(popupScene);
            popup.show();
        });
        
        menuBar.getMenus().add(budgetMenu);
        
        Menu reportsMenu = new Menu("Reports");
        
        MenuItem weeklyReport = new MenuItem("Weekly Summary");
        MenuItem monthlyReport = new MenuItem("Monthly Summary");
        MenuItem categoryReport = new MenuItem("Category Breakdown");
        MenuItem chartsReport = new MenuItem("📊 Analytics Dashboard");
        MenuItem exportPdfReport = new MenuItem("Export Monthly Report as PDF");
        
        reportsMenu.getItems().addAll(weeklyReport, monthlyReport, categoryReport, chartsReport, new SeparatorMenuItem(), exportPdfReport);
        
        weeklyReport.setOnAction(e -> {
            Stage weeklyStage = new Stage();
            weeklyStage.setTitle("Weekly Summary");
            weeklyStage.setWidth(350);
            weeklyStage.setHeight(200);
            
            double weeklyTotal = totalExpenses;
            
            Label summaryLabel = new Label("Total spent this week: $" + String.format("%.2f", weeklyTotal));
            summaryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #1a1a1a;");
            
            VBox layout = new VBox(15, summaryLabel);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.CENTER);
            layout.setStyle("-fx-background-color: #FAFAF8;");
            
            Scene scene = new Scene(layout);
            scene.getRoot().setStyle(
                "-fx-font-family: 'Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', sans-serif;" +
                "-fx-font-size: 13px;"
            );
            weeklyStage.setScene(scene);
            weeklyStage.show();
        });
        
        monthlyReport.setOnAction(e -> {
            Stage monthlyStage = new Stage();
            monthlyStage.setTitle("Monthly Summary");
            monthlyStage.setWidth(350);
            monthlyStage.setHeight(200);
            
            double monthlyTotal = totalExpenses;
            
            Label summaryLabel = new Label("Total spent this month: $" + String.format("%.2f", monthlyTotal));
            summaryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: 600; -fx-text-fill: #1a1a1a;");
            
            VBox layout = new VBox(15, summaryLabel);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.CENTER);
            layout.setStyle("-fx-background-color: #FAFAF8;");
            
            Scene scene = new Scene(layout);
            scene.getRoot().setStyle(
                "-fx-font-family: 'Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', sans-serif;" +
                "-fx-font-size: 13px;"
            );
            monthlyStage.setScene(scene);
            monthlyStage.show();
        });
        
        categoryReport.setOnAction(e -> {
            Stage categoryStage = new Stage();
            categoryStage.setTitle("Category Breakdown");
            categoryStage.setWidth(500);
            categoryStage.setHeight(400);
            
            PieChart chart = new PieChart();
            chart.setStyle("-fx-font-size: 13px;");
            
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                if (entry.getValue() > 0) {
                    chart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
            }
            
            VBox layout = new VBox(chart);
            layout.setPadding(new Insets(20));
            layout.setStyle("-fx-background-color: #FAFAF8;");
            
            Scene scene = new Scene(layout);
            scene.getRoot().setStyle(
                "-fx-font-family: 'Inter', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica Neue', sans-serif;" +
                "-fx-font-size: 13px;"
            );
            categoryStage.setScene(scene);
            categoryStage.show();
        });
        
        chartsReport.setOnAction(e -> {
            Stage chartsStage = new Stage();
            chartsStage.setTitle("Spending Analytics Dashboard");
            chartsStage.setWidth(1200);
            chartsStage.setHeight(1200);
            
            ScrollPane scrollPane = new ScrollPane(createChartsPanel());
            scrollPane.setFitToWidth(true);
            scrollPane.setStyle("-fx-background-color: #000000;");
            
            Scene scene = new Scene(scrollPane);
            scene.getRoot().setStyle("-fx-background-color: #000000;");
            chartsStage.setScene(scene);
            chartsStage.show();
        });
        
        exportPdfReport.setOnAction(e -> {
            new MonthlyReportExporter(this).exportCurrentMonthReport(primaryStage);
        });
        
        menuBar.getMenus().add(reportsMenu);
        
        Menu tipsMenu = new Menu("Tips");
        MenuItem tipsItem = new MenuItem("Money-Saving Tips");
        tipsMenu.getItems().add(tipsItem);
        menuBar.getMenus().add(tipsMenu);
        
        Menu settingsMenu = new Menu("Settings");
        
        MenuItem themeToggleItem = new MenuItem("Select Theme");
        settingsMenu.getItems().add(themeToggleItem);
        
        themeToggleItem.setOnAction(e -> {
            Stage themeStage = new Stage();
            themeStage.setTitle("Theme Selector");
            
            Label themeLabel = new Label("Choose a Theme:");
            themeLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #000000;");
            
            ComboBox<String> themeCombo = new ComboBox<>();
            for (Theme theme : availableThemes) {
                themeCombo.getItems().add(theme.name);
            }
            themeCombo.setPrefWidth(200);
            themeCombo.setStyle("-fx-font-size: 12; -fx-control-inner-background: #FFFFFF; -fx-text-fill: #000000; -fx-padding: 5;");
            
            Label previewLabel = new Label("Preview:");
            previewLabel.setStyle("-fx-font-size: 12; -fx-text-fill: #000000;");
            
            Rectangle previewBox = new Rectangle(200, 100);
            previewBox.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 2;");
            
            Button applyButton = new Button("Apply Theme");
            applyButton.setStyle("-fx-padding: 10; -fx-font-size: 12; -fx-background-color: #CC0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-background-radius: 8;");
            
            themeCombo.setOnAction(ev -> {
                int index = themeCombo.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Theme selected = availableThemes.get(index);
                    previewBox.setFill(Color.web(selected.background));
                }
            });
            
            applyButton.setOnAction(ev -> {
                int index = themeCombo.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    Theme selected = availableThemes.get(index);
                    applyDynamicTheme(selected, root, stage);
                    themeStage.close();
                }
            });
            
            VBox themeLayout = new VBox(15);
            themeLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 20;");
            themeLayout.getChildren().addAll(themeLabel, themeCombo, previewLabel, previewBox, applyButton);
            themeLayout.setAlignment(Pos.TOP_CENTER);
            
            Scene themeScene = new Scene(themeLayout, 300, 280);
            themeStage.setScene(themeScene);
            themeStage.show();
        });
        
        MenuItem toggleDark = new MenuItem("Toggle Dark/Light Mode");
        settingsMenu.getItems().add(toggleDark);
        
        toggleDark.setOnAction(e -> {
            if (currentAppliedTheme == null) {
                currentAppliedTheme = availableThemes.get(5);
            }
            
            isDarkMode = !isDarkMode;  
            
            if (isDarkMode) {
                root.setStyle("-fx-background-color: " + currentAppliedTheme.background + ";");
                menuBar.setStyle(
                    "-fx-background-color: " + currentAppliedTheme.accent + ";" +
                    "-fx-text-fill: " + currentAppliedTheme.background + ";"
                );
                
                if (titleLabel != null) {
                    titleLabel.setFill(new LinearGradient(
                        0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web(currentAppliedTheme.primary)),
                        new Stop(1, Color.web(currentAppliedTheme.secondary))
                    ));
                }
                
                updateNodeThemeRecursive(root, currentAppliedTheme);
            } else {
                String lightBackground = invertColor(currentAppliedTheme.background);
                String lightTextColor = invertColor(currentAppliedTheme.primary);
                
                root.setStyle("-fx-background-color: " + lightBackground + ";");
                menuBar.setStyle(
                    "-fx-background-color: " + currentAppliedTheme.primary + ";" +
                    "-fx-text-fill: " + lightBackground + ";"
                );
                
                if (titleLabel != null) {
                    titleLabel.setFill(new LinearGradient(
                        0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web(currentAppliedTheme.accent)),
                        new Stop(1, Color.web(currentAppliedTheme.secondary))
                    ));
                }
                
                // Update all nodes for light mode
                for (Node node : root.getChildren()) {
                    if (node instanceof javafx.scene.layout.Pane) {
                        updateNodeLightMode((javafx.scene.layout.Pane) node, lightTextColor);
                    }
                }
            }
        });
        
        MenuItem resetAll = new MenuItem("Reset All Data");
        settingsMenu.getItems().add(resetAll);
        
        resetAll.setOnAction(e -> {
            totalExpenses = 0;
            monthlyBudget = 0;
            budgetLabel.setText("Current Budget: $0.00");
            expenseListView.getItems().clear();
            categoryExpenses.clear();
            updatePieChart();
            budgetProgressBar.setProgress(0);
        });
        
        MenuItem setCurrency = new MenuItem("Set Currency");
        settingsMenu.getItems().add(setCurrency);
        
        setCurrency.setOnAction(e -> {
            Stage popup = new Stage();
            Label label = new Label("Choose your currency:");
            
            ComboBox<String> currencyBox = new ComboBox<>();
            currencyBox.getItems().addAll("$", "€", "£");
            currencyBox.setValue("$");

            Button setButton = new Button("Set");
            setButton.setOnAction(ev -> {
                String currency = currencyBox.getValue();
                String currentBudgetValue = budgetLabel.getText().replaceAll("[^0-9.]", "");
                budgetLabel.setText("Current Budget: " + currency + currentBudgetValue);
                popup.close();
            });

            VBox layout = new VBox(10, label, currencyBox, setButton);
            layout.setPadding(new Insets(10));
            Scene scene = new Scene(layout, 250, 150);
            popup.setScene(scene);
            popup.setTitle("Set Currency");
            popup.show();
        });
        
        MenuItem setDefaultMonthly = new MenuItem("Set Monthly Budget");
        settingsMenu.getItems().add(setDefaultMonthly);
        
        setDefaultMonthly.setOnAction(e -> {
            Stage popup = new Stage();
            Label popupLabel = new Label("Enter monthly budget:");
            popupLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 13;");
            
            TextField budgetInput = new TextField();
            Button setButton = new Button("Set Budget");
            setButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #555555, #404040); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 8 18; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 3, 0.3, 1, 1);"
            );
            
            setButton.setOnAction(ev -> {
                try {
                    monthlyBudget = Double.parseDouble(budgetInput.getText());
                    totalExpenses = 0;
                    updateBudgetDisplay();
                    popup.close();
                } catch (NumberFormatException ex) {
                    budgetInput.setStyle("-fx-border-color: red;");
                }
            });

            HBox inputRow = new HBox(10, budgetInput, setButton);
            inputRow.setPadding(new Insets(10));
            VBox popupLayout = new VBox(10, popupLabel, inputRow);
            popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15; -fx-text-fill: #000000;");
            popupLayout.setPadding(new Insets(10));
            
            Scene popupScene = new Scene(popupLayout, 350, 120);
            popup.setTitle("Monthly Budget");
            popup.setScene(popupScene);
            popup.show();
        });
        
        menuBar.getMenus().add(settingsMenu);
        
    
        Label budgetCardLabel = new Label("Budget Overview");
        budgetCardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: #000000; -fx-letter-spacing: 0.1;");
        
        monthlyBudgetLabel = new Label("$0.00");
        monthlyBudgetLabel.setStyle("-fx-font-size: 26px; -fx-font-weight: 500; -fx-text-fill: #000000;");
        Label budgetDescLabel = new Label("Monthly Budget");
        budgetDescLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #888888; -fx-letter-spacing: 0.1;");
        
        VBox budgetAmountBox = new VBox(2, monthlyBudgetLabel, budgetDescLabel);
        budgetAmountBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        spentLabel = new Label("$0.00");
        spentLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 500; -fx-text-fill: #000000;");
        Label spentDescLabel = new Label("Total Spent");
        spentDescLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888; -fx-letter-spacing: 0.1;");
        VBox spentBox = new VBox(2, spentLabel, spentDescLabel);
        spentBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        remainingLabel = new Label("$0.00");
        remainingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 500; -fx-text-fill: #A8D08D;");
        Label remainingDescLabel = new Label("Remaining");
        remainingDescLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888; -fx-letter-spacing: 0.1;");
        VBox remainingBox = new VBox(2, remainingLabel, remainingDescLabel);
        remainingBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        percentLabel = new Label("0% Used");
        percentLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: 500; -fx-text-fill: #8FBC8F;");
        Label percentDescLabel = new Label("Progress");
        percentDescLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888; -fx-letter-spacing: 0.1;");
        VBox percentBox = new VBox(2, percentLabel, percentDescLabel);
        percentBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        budgetLabel = monthlyBudgetLabel;
        
        HBox statsRow1 = new HBox(20, budgetAmountBox, spentBox);
        statsRow1.setPadding(new Insets(0));
        statsRow1.setStyle("-fx-padding: 0;");
        
        HBox statsRow2 = new HBox(20, remainingBox, percentBox);
        statsRow2.setPadding(new Insets(0));
        
        VBox budgetStatsBox = new VBox(16, statsRow1, statsRow2);
        budgetStatsBox.setStyle("-fx-padding: 20; -fx-background-color: rgba(143,188,143,0.08); -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-color: rgba(143,188,143,0.25); -fx-border-width: 1;");
        
        VBox progressBox = new VBox(8);
        progressBox.getChildren().add(budgetProgressBar);
        progressBox.setStyle("-fx-padding: 0;");
        
        VBox budgetCard = new VBox(16, budgetCardLabel, budgetStatsBox, progressBox, forecastLabel, resetButton);
        budgetCard.setPadding(new Insets(24));
        budgetCard.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #000000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 16, 0.7, 0, 6);"
        );
        
        Label expensesCardLabel = new Label("Expenses");
        expensesCardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: #000000; -fx-letter-spacing: 0.1;");
        
        Button removeExpenseButton = new Button("Remove Expense");
        removeExpenseButton.setStyle(
            "-fx-background-color: #000000;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 14;" +
            "-fx-padding: 10 18;" +
            "-fx-font-weight: 600;" +
            "-fx-font-size: 13;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);" +
            "-fx-cursor: hand;" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;" +
            "-fx-focus-traversable: false;"
        );
        removeExpenseButton.setFocusTraversable(false);
        
        removeExpenseButton.setOnAction(e -> {
            int selectedIndex = expenseListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedExpense = expenseListView.getItems().get(selectedIndex);
                
                Stage removePopup = new Stage();
                Label confirmLabel = new Label("Remove this expense?");
                confirmLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 14; -fx-font-weight: bold;");
                
                Label expenseDisplayLabel = new Label(selectedExpense);
                expenseDisplayLabel.setStyle("-fx-text-fill: #333333; -fx-font-size: 12;");
                
                Button confirmButton = new Button("Remove");
                confirmButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #CC5555, #AA4040);" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 12;" +
                    "-fx-padding: 8 16;" +
                    "-fx-font-weight: 600;"
                );
                
                Button cancelButton = new Button("Cancel");
                cancelButton.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #666666, #505050);" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 12;" +
                    "-fx-padding: 8 16;" +
                    "-fx-font-weight: 600;"
                );
                
                confirmButton.setOnAction(ev -> {
                    try {
                        String expenseText = expenseListView.getItems().get(selectedIndex);
                        
                        double expenseAmount = Double.parseDouble(expenseText.replaceAll("[^0-9.]", ""));
                        expenseAmount = Math.round(expenseAmount * 100.0) / 100.0;
                        
                        if (expenseText.contains("[Income]")) {
                            monthlyBudget -= expenseAmount;
                        } else {
                            totalExpenses -= expenseAmount;
                            
                            String[] parts = expenseText.split(" Expense:");
                            if (parts.length > 0) {
                                String category = parts[0].trim();
                                double currentAmount = categoryExpenses.getOrDefault(category, 0.0);
                                if (currentAmount > 0) {
                                    categoryExpenses.put(category, currentAmount - expenseAmount);
                                }
                            }
                        }
                        
                        double remainingBudget = monthlyBudget - totalExpenses;
                        budgetLabel.setText(String.format("Current Budget: $%.2f (Spent: $%.2f)", remainingBudget, totalExpenses));
                        expenseListView.getItems().remove(selectedIndex);
                        
                        if (monthlyBudget > 0) {
                            double progress = Math.min(totalExpenses / monthlyBudget, 1.0);
                            budgetProgressBar.setProgress(progress);
                        } else {
                            budgetProgressBar.setProgress(0);
                        }
                        
                        updateBudgetDisplay();
                        updatePieChart();
                        updateForecast(forecastLabel, remainingBudget);
                        removePopup.close();
                    } catch (Exception ex) {
                    }
                });
                
                cancelButton.setOnAction(ev -> removePopup.close());
                
                HBox buttonBox = new HBox(10, confirmButton, cancelButton);
                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.setPadding(new Insets(10));
                
                VBox popupLayout = new VBox(10, confirmLabel, expenseDisplayLabel, buttonBox);
                popupLayout.setStyle("-fx-background-color: #F5F0E6; -fx-padding: 15; -fx-text-fill: #000000;");
                popupLayout.setPadding(new Insets(15));
                
                Scene popupScene = new Scene(popupLayout, 350, 180);
                removePopup.setScene(popupScene);
                removePopup.setTitle("Remove Expense");
                removePopup.show();
            } else {
                showAlert("Warning", "Please select an expense to remove");
            }
        });
        
        VBox expensesCard = new VBox(10, expensesCardLabel, expenseListView, removeExpenseButton);
        expensesCard.setPadding(new Insets(24));
        expensesCard.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #000000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 16, 0.7, 0, 6);"
        );
        
        Label goalsCardLabel = new Label("Savings Goals");
        goalsCardLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: #000000; -fx-letter-spacing: 0.1;");
        VBox goalsCard = new VBox(10, goalsCardLabel, goalProgress, savedLabel, addMoneyBox,
                                   goalsListView, addGoalButton, addToGoalButton, removeGoalButton);
        goalsCard.setPadding(new Insets(24));
        goalsCard.setStyle(
            "-fx-background-color: #FFFFFF;" +
            "-fx-border-color: #000000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 20;" +
            "-fx-border-radius: 20;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 16, 0.7, 0, 6);"
        );
        
        VBox mainContent = new VBox(20, budgetCard, expensesCard, goalsCard);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #F5F0E6;");
        
        root = new VBox();
        root.getChildren().addAll(menuBar, titleLabel, mainContent);
        
        javafx.scene.layout.VBox.setMargin(titleLabel, new Insets(20, 0, 20, 0));
        

        tipsItem.setOnAction(ev -> {
            Random rand = new Random();
            int index = rand.nextInt(tips.length);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Budget Buddy Tip");
            alert.setHeaderText("Money-Saving Tip");
            alert.setContentText(tips[index]);
            alert.showAndWait();
        });
    
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        
        Scene scene = new Scene(scrollPane, 600, 900);
        scene.getStylesheets().add(getClass().getResource("button-styles.css").toExternalForm());
        
        scene.getRoot().setStyle(
            "-fx-font-family: 'Helvetica Neue', '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Helvetica', sans-serif;" +
            "-fx-font-size: 13px;" +
            "-fx-background-color: #000000;"
        );
        
        root.setStyle("-fx-background-color: #000000;");
        
        try {
            String resource = getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(resource);
        } catch (NullPointerException e) {
        }
        
        stage.setTitle("Budget Buddie");
        stage.setScene(scene);
        
        stage.setOnCloseRequest(e -> saveData());
        
        stage.show();
    }
    
    private void updatePieChart() {
        pieChart.getData().clear();
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            if (entry.getValue() > 0) {
                pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
        }
    }
    
    private void checkBudget(double remainingBudget, double totalBudget) {
        if (remainingBudget < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Budget Alert!");
            alert.setHeaderText("You are over your budget!");
            alert.setContentText("Spend carefully! Your remaining budget is $" + String.format("%.2f", remainingBudget));
            alert.showAndWait();
            
            budgetProgressBar.setStyle("-fx-accent: #FF5252;");
        } else if (remainingBudget <= 0.2 * totalBudget) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Budget Alert!");
            alert.setHeaderText("Budget Running Low");
            alert.setContentText("You have $" + String.format("%.2f", remainingBudget) + " remaining.");
            alert.showAndWait();
            
            budgetProgressBar.setStyle("-fx-accent: #FFA726;"); 
        } else {
            budgetProgressBar.setStyle("-fx-accent: #4CAF50;"); 
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateForecast(Label forecastLabel, double currentBudget) {
        if (totalExpenses <= 0) {
            forecastLabel.setText("Forecast: No expenses yet");
            return;
        }

        double dailyAverage = totalExpenses / 7.0; 
        int daysLeft = (int) Math.floor(currentBudget / dailyAverage);

        forecastLabel.setText(String.format(
            "Forecast: Budget lasts ~%d days (%.2f/day)",
            daysLeft, dailyAverage
        ));
    }

    private void updateBudgetDisplay() {
        double remainingBudget = monthlyBudget - totalExpenses;
        double percentUsed = (monthlyBudget > 0) ? (totalExpenses / monthlyBudget) * 100 : 0;
        
        monthlyBudgetLabel.setText(String.format("$%.2f", monthlyBudget));
        spentLabel.setText(String.format("$%.2f", totalExpenses));
        remainingLabel.setText(String.format("$%.2f", Math.max(0, remainingBudget)));
        percentLabel.setText(String.format("%.1f%%", percentUsed));
        
        if (monthlyBudget > 0) {
            budgetProgressBar.setProgress(Math.min(percentUsed / 100.0, 1.0));
        }
        
        if (remainingBudget < 0) {
            remainingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 500; -fx-text-fill: #000000;");
        } else if (remainingBudget < (monthlyBudget * 0.2)) {
            remainingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 500; -fx-text-fill: #000000;");
        } else {
            remainingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: 500; -fx-text-fill: #A8D08D;");
        }
    }

    private VBox createChartsPanel() {
        VBox chartsPanel = new VBox(20);
        chartsPanel.setPadding(new Insets(25));
        chartsPanel.setStyle("-fx-background-color: #000000;");

        Text chartsTitle = new Text("Spending Analytics Dashboard");
        chartsTitle.setFont(Font.font("Helvetica Neue", FontWeight.NORMAL, 28));
        chartsTitle.setFill(Color.web("#8FBC8F"));
        
        Text subtitle = new Text("Track your expenses across categories and dates");
        subtitle.setFont(Font.font("Helvetica Neue", 14));
        subtitle.setFill(Color.web("#CCCCCC"));

        CategoryAxis xAxisLine = new CategoryAxis();
        xAxisLine.setLabel("📅 Date");
        xAxisLine.setStyle("-fx-text-fill: #000000; -fx-font-size: 11px;");

        NumberAxis yAxisLine = new NumberAxis();
        yAxisLine.setLabel("💵 Amount ($)");
        yAxisLine.setStyle("-fx-text-fill: #000000; -fx-font-size: 11px;");

        LineChart<String, Number> lineChart = new LineChart<>(xAxisLine, yAxisLine);
        lineChart.setTitle("📈 Daily Expenses Over Time");
        lineChart.setTitleSide(javafx.geometry.Side.TOP);
        lineChart.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: #000000; -fx-font-size: 13px;");
        lineChart.setCreateSymbols(true);
        lineChart.setPrefHeight(350);

        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Daily Spending");
        
        Map<String, Double> dailyExpenses = new HashMap<>();
        for (String expense : expenseListView.getItems()) {
            try {
                String[] parts = expense.split(" on ");
                if (parts.length == 2) {
                    String date = parts[1].trim();
                    String amountPart = expense.substring(expense.indexOf("$") + 1, expense.lastIndexOf(" on"));
                    double amount = Double.parseDouble(amountPart);
                    dailyExpenses.put(date, dailyExpenses.getOrDefault(date, 0.0) + amount);
                }
            } catch (Exception e) {
            }
        }
        
        dailyExpenses.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> lineSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue())));
        
        if (dailyExpenses.isEmpty()) {
            lineSeries.getData().add(new XYChart.Data<>("No data", 0));
        }
        
        lineChart.getData().add(lineSeries);

        CategoryAxis xAxisBar = new CategoryAxis();
        xAxisBar.setLabel("🏷️ Category");
        xAxisBar.setStyle("-fx-text-fill: #000000; -fx-font-size: 11px;");

        NumberAxis yAxisBar = new NumberAxis();
        yAxisBar.setLabel("💰 Amount ($)");
        yAxisBar.setStyle("-fx-text-fill: #000000; -fx-font-size: 11px;");

        BarChart<String, Number> barChart = new BarChart<>(xAxisBar, yAxisBar);
        barChart.setTitle("📊 Spending by Category");
        barChart.setStyle("-fx-background-color: #1a1a1a; -fx-text-fill: #000000; -fx-font-size: 13px;");
        barChart.setPrefHeight(350);

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Total Spent");
        
        for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
            barSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        
        if (categoryExpenses.isEmpty()) {
            barSeries.getData().add(new XYChart.Data<>("No data yet", 0));
        }
        
        barChart.getData().add(barSeries);

        chartsPanel.getChildren().addAll(chartsTitle, subtitle, lineChart, barChart);
        return chartsPanel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

