public class Account {
    private int accountNumber;
    private String name;
    private int pin;
    private double balance;

    public Account(int accNo, String name, int pin, double balance) {
        this.accountNumber = accNo;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance)
            return false;
        balance -= amount;
        return true;
    }
}
