import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Transaction {

    String type;
    String category;
    double amount;
    LocalDate date;

    public Transaction(String type,
                       String category,
                       double amount,
                       LocalDate date) {

        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}

 class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Transaction> transactions =
            new ArrayList<>();

    static double balance = 0;

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      SmartBudget Tracker");
        System.out.println("=================================");

        while (true) {

            try {

                System.out.println("\n===== MENU =====");

                System.out.println("1. Add Income");
                System.out.println("2. Add Expense");
                System.out.println("3. View Balance");
                System.out.println("4. View Transactions");
                System.out.println("5. Monthly Summary");
                System.out.println("6. Exit");

                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        addIncome();
                        break;

                    case 2:
                        addExpense();
                        break;

                    case 3:
                        viewBalance();
                        break;

                    case 4:
                        viewTransactions();
                        break;

                    case 5:
                        monthlySummary();
                        break;

                    case 6:
                        System.out.println(
                                "\nThank you for using SmartBudget!");

                        System.exit(0);

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (InputMismatchException e) {

                System.out.println(
                        "Please enter valid input.");

                scanner.nextLine();
            }
        }
    }

    static void addIncome() {

        System.out.println("\n===== ADD INCOME =====");

        System.out.print("Enter Income Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.print("Enter Date (DD-MM-YYYY): ");

        LocalDate date =
                LocalDate.parse(scanner.nextLine(),
                        formatter);

        Transaction income =
                new Transaction(
                        "Income",
                        category,
                        amount,
                        date);

        transactions.add(income);

        balance += amount;

        System.out.println("Income Added Successfully!");
    }

    static void addExpense() {

        System.out.println("\n===== ADD EXPENSE =====");

        System.out.print("Enter Expense Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.print("Enter Date (DD-MM-YYYY): ");

        LocalDate date =
                LocalDate.parse(scanner.nextLine(),
                        formatter);

        Transaction expense =
                new Transaction(
                        "Expense",
                        category,
                        amount,
                        date);

        transactions.add(expense);

        balance -= amount;

        System.out.println("Expense Added Successfully!");
    }

    static void viewBalance() {

        System.out.println("\n===== CURRENT BALANCE =====");

        System.out.println("Balance: Rs." + balance);
    }

    static void viewTransactions() {

        System.out.println("\n===== TRANSACTION HISTORY =====");

        if (transactions.isEmpty()) {

            System.out.println("No transactions found.");
            return;
        }

        for (Transaction t : transactions) {

            System.out.println("\nType     : " + t.type);

            System.out.println("Category : " + t.category);

            System.out.println("Amount   : Rs." + t.amount);

            System.out.println("Date     : " + t.date);

            System.out.println("----------------------------");
        }
    }

    static void monthlySummary() {

        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction t : transactions) {

            if (t.type.equals("Income")) {

                totalIncome += t.amount;

            } else {

                totalExpense += t.amount;
            }
        }

        System.out.println("\n===== MONTHLY SUMMARY =====");

        System.out.println("Total Income  : Rs."
                + totalIncome);

        System.out.println("Total Expense : Rs."
                + totalExpense);

        System.out.println("Savings       : Rs."
                + (totalIncome - totalExpense));
    }
}