# Budget Buddie

A budget tracking app built with JavaFX. Helps you keep track of spending, set savings goals, and see where your money goes.

## What It Does

Budget Buddie lets you:
- Track monthly expenses by category
- Set and monitor budgets in real-time
- Add income sources
- Create savings goals and watch them grow
- See visual breakdowns of spending (pie charts, line graphs)
- Export monthly reports
- Pick from 50+ themes or switch between dark/light mode
- Lock it with a PIN for privacy

## Getting Started

You need:
- Java 21 or newer (I used Temurin-25)
- JavaFX SDK 25
- A terminal

### How to Run It

1. Compile:
```bash
javac --module-path /path/to/javafx-sdk/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.graphics \
      MainApp.java MonthlyReportExporter.java
```

2. Run:
```bash
java --module-path /path/to/javafx-sdk/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     MainApp
```

3. Enter PIN (default is `1234`)

## What I Built

- **MainApp.java**: The main application with UI, expense tracking, goals, themes, and all the features
- **MonthlyReportExporter.java**: Generates text reports of your monthly spending
- **budget_data.txt**: Where your data gets saved automatically
- **pin.txt**: Your PIN (kept simple for this project)

## How to Use It

**First Time:**
1. Set your monthly budget (Budget > Monthly Budget)
2. Start adding expenses (Budget > Add Expense)
3. Watch the progress bar fill up

**Adding Expenses:**
- Enter amount
- Pick a category (food, transport, entertainment, etc.)
- Pick the date
- Click Add

**Savings Goals:**
- Click "Add New Goal"
- Set a name, target amount, and date
- Add money to it as you save
- Watch the progress bar

**Seeing Your Data:**
- **Weekly/Monthly Summary**: Quick overview in Reports menu
- **Category Breakdown**: Pie chart of where money goes
- **Analytics Dashboard**: Line and bar charts
- **Export Report**: Save a text file of your spending

**Customization:**
- Pick a theme (Settings > Select Theme)
- Toggle dark/light mode (Settings > Toggle Dark/Light Mode)

## Data Storage

Your data saves automatically to `budget_data.txt` when you close the app. It's kept in the project folder so it persists between sessions.

## Code Quality

I cleaned up the code to make it professional:
- Removed all debug print statements (System.out.println)
- Proper error handling - errors show as dialog boxes instead of console spam
- Input validation - invalid inputs get a red border instead of crashing
- No wildcard imports - all imports are explicit
- Javadoc comments on key classes and methods
- Clean architecture with MonthlyReportExporter handling report generation

## Monthly Reports

You can export a text report of your spending that includes:
- Your budget, total spending, and what's left
- Breakdown of spending by category
- A list of all your expenses
- Nice formatting so it's easy to read

## Themes

There are 50+ built-in themes to choose from. You can also toggle between dark and light mode. The themes are applied in real-time so you can see changes immediately.

## Things That Could Be Better

- Single user only (PIN is basic, not encryption)
- Data is in a text file (not a database)
- Themes are preset (can't create custom ones)
- Can't view spending history from past months

## What I Learned

Building this taught me a lot about:
- JavaFX UI design and event handling
- File I/O for data persistence
- Managing complex state in a GUI application
- Good software practices (clean code, error handling, validation)
- How to structure larger applications

## Having Issues?

- **App won't start**: Check your JavaFX path and Java version (need 21+)
- **Data not saving**: Make sure the app closed normally and you have write permissions
- **Forgot PIN**: Delete `pin.txt` and it resets to `1234`
- **Everything frozen**: Try closing and reopening

## Summary

Budget Buddie is a full-featured budget tracker with a clean UI, good data visualization, and all the essentials you'd want for managing money. It's written with clean code practices in mind and should be easy to understand and modify if needed.

**Tech**: Java + JavaFX  
**Status**: Complete and working  
**Created**: November 2025
