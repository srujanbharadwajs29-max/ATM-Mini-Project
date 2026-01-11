import java.util.ArrayList;

public class ATM implements ATMOperations {

    ArrayList<Account> accounts = new ArrayList<>();
    Account currentUser;

    public void addAccount(Account acc) {
        accounts.add(acc);
    }

    public boolean login(int accNo, int pin) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNo && acc.getPin() == pin) {
                currentUser = acc;
                return true;
            }
        }
        return false;
    }

    public Account findAccount(int accNo) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accNo)
                return acc;
        }
        return null;
    }

    public void checkBalance() {
        System.out.println("Current Balance: ₹" + currentUser.getBalance());
    }
public void deposit(double amount) {
    if (amount % 100 != 0) {
        System.out.println("Invalid amount! Please deposit in multiples of ₹100 only.");
        return;
    }
    currentUser.deposit(amount);
    System.out.println("Deposit successful!");
}

public void withdraw(double amount) {
    if (amount % 100 != 0) {
        System.out.println("Invalid amount! Please withdraw in multiples of ₹100 only.");
        return;
    }

    if (currentUser.withdraw(amount))
        System.out.println("Withdraw successful!");
    else
        System.out.println("Insufficient balance!");
}


    public void transfer(int toAcc, double amount) {
        Account receiver = findAccount(toAcc);
        if (receiver == null) {
            System.out.println("Receiver not found!");
            return;
        }
        if (currentUser.withdraw(amount)) {
            receiver.deposit(amount);
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Not enough balance!");
        }
    }
}
