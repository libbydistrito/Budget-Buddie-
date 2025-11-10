import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

interface BudgetBuddie {
    void trackExpenses();
    void createBudget();
    void addExpense(String category, double amount);
    void removeExpense(int expenseId);
    void totalExpenses();
    void remainingBudget();
    void generateReports();
}

class Expense {
    private final int id;
    private final String category;
    private final double amount;

    public Expense(int id, String category, double amount) {
        this.id = id;
        this.category = category;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class BudgetBuddieImpl implements BudgetBuddie {
    private final ArrayList<Expense> expenses = new ArrayList<>();
    private double budget = 0;
    private final Scanner scanner;
    private int nextExpenseId = 1;

    public BudgetBuddieImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void createBudget() {
        System.out.print("Enter your total budget: $");
        while (true) {
            try {
                budget = scanner.nextDouble();
                scanner.nextLine(); // consume newline
                if (budget < 0) {
                    System.out.print("Budget cannot be negative. Enter again: $");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a number: $");
                scanner.nextLine(); // clear invalid input
            }
        }
        System.out.println("Budget set to $" + budget);
    }

    @Override
    public void addExpense(String category, double amount) {
        if (amount < 0) {
            System.out.println("Amount cannot be negative.");
            return;
        }
        Expense expense = new Expense(nextExpenseId++, category, amount);
        expenses.add(expense);
        System.out.println("Added expense: " + category + " - $" + amount);
    }

    @Override
    public void removeExpense(int expenseId) {
        boolean removed = expenses.removeIf(e -> e.getId() == expenseId);
        if (removed) {
            System.out.println("Removed expense with ID: " + expenseId);
        } else {
            System.out.println("Expense with ID " + expenseId + " not found.");
        }
    }

    @Override
    public void trackExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        System.out.println("Expenses:");
        for (Expense e : expenses) {
            System.out.println(e.getId() + ". " + e.getCategory() + ": $" + e.getAmount());
        }
    }

    private double calculateTotalExpenses() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    @Override
    public void totalExpenses() {
        System.out.printf("Total expenses: $%.2f%n", calculateTotalExpenses());
    }

    @Override
    public void remainingBudget() {
        double remaining = budget - calculateTotalExpenses();
        System.out.printf("Remaining budget: $%.2f%n", remaining);
    }

    @Override
    public void generateReports() {
        System.out.println("\n=== Budget Report ===");
        totalExpenses();
        remainingBudget();
        System.out.println("====================\n");
    }
}

public class BudgetBuddieApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BudgetBuddieImpl app = new BudgetBuddieImpl(scanner);

            System.out.println("Welcome to BudgetBuddie!");
            app.createBudget();

            boolean running = true;
            while (running) {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Add Expense");
                System.out.println("2. Remove Expense");
                System.out.println("3. Track Expenses");
                System.out.println("4. Total Expenses");
                System.out.println("5. Remaining Budget");
                System.out.println("6. Generate Report");
                System.out.println("7. Exit");
                System.out.print("Choose an option: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    continue;
                }

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter expense category: ");
                        String category = scanner.nextLine();
                        double amount;
                        while (true) {
                            System.out.print("Enter amount: $");
                            try {
                                amount = scanner.nextDouble();
                                scanner.nextLine();
                                if (amount < 0) {
                                    System.out.println("Amount cannot be negative.");
                                    continue;
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Enter a number.");
                                scanner.nextLine();
                            }
                        }
                        app.addExpense(category, amount);
                    }
                    case 2 -> {
                        app.trackExpenses();
                        System.out.print("Enter expense ID to remove: ");
                        int id;
                        try {
                            id = scanner.nextInt();
                            scanner.nextLine();
                            app.removeExpense(id);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Enter a number.");
                            scanner.nextLine();
                        }
                    }
                    case 3 -> app.trackExpenses();
                    case 4 -> app.totalExpenses();
                    case 5 -> app.remainingBudget();
                    case 6 -> app.generateReports();
                    case 7 -> {
                        System.out.println("Thank you for using BudgetBuddie! Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            }
        }
    }
}
