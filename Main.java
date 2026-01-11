import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATM atm = new ATM();

        // Sample accounts
        atm.addAccount(new Account(101, "Srujan", 1234, 5000));
        atm.addAccount(new Account(102, "Ravi", 4321, 3000));

        System.out.println("------ Welcome to ATM ------");

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (!atm.login(accNo, pin)) {
            System.out.println("Invalid login!");
            return;
        }

        System.out.println("Login successful!");

        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        atm.checkBalance();
                        break;
                    case 2:
                        System.out.print("Enter amount: ");
                        atm.deposit(sc.nextDouble());
                        break;
                    case 3:
                        System.out.print("Enter amount: ");
                        atm.withdraw(sc.nextDouble());
                        break;
                    case 4:
                        System.out.print("Enter receiver account: ");
                        int to = sc.nextInt();
                        System.out.print("Enter amount: ");
                        double amt = sc.nextDouble();
                        atm.transfer(to, amt);
                        break;
                    case 5:
                        System.out.println("Thank you for using ATM!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: Invalid input!");
            }
        }
    }
}
