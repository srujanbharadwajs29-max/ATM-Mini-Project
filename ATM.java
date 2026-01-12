import java.sql.*;

public class ATM implements ATMOperations {

    Connection con;
    int currentAcc;

    public ATM() {
        try {
            con = DBConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }

    public boolean login(int acc, int pin) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM accounts WHERE acc_no=? AND pin=?");
            ps.setInt(1, acc);
            ps.setInt(2, pin);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentAcc = acc;
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    public void checkBalance() {
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM accounts WHERE acc_no=?");
            ps.setInt(1, currentAcc);
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println("Current Balance: ₹" + rs.getDouble(1));
        } catch (Exception e) {}
    }

  public void deposit(double amt) {
    try {
        PreparedStatement ps = con.prepareStatement(
            "UPDATE accounts SET balance = balance + ? WHERE acc_no=?");
        ps.setDouble(1, amt);
        ps.setInt(2, currentAcc);
        int rows = ps.executeUpdate();
        System.out.println("Rows updated: " + rows);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public void withdraw(double amt) {
        if (amt % 100 != 0) {
            System.out.println("Only ₹100 multiples allowed");
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT balance FROM accounts WHERE acc_no=?");
            ps.setInt(1, currentAcc);
            ResultSet rs = ps.executeQuery();
            rs.next();
            double bal = rs.getDouble(1);

            if (bal < amt) {
                System.out.println("Insufficient balance");
                return;
            }

            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE acc_no=?");
            ps2.setDouble(1, amt);
            ps2.setInt(2, currentAcc);
            ps2.executeUpdate();
            System.out.println("Withdraw successful");
        } catch (Exception e) {}
    }

    public void transfer(int toAcc, double amt) {
        try {
            withdraw(amt);
            PreparedStatement ps = con.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE acc_no=?");
            ps.setDouble(1, amt);
            ps.setInt(2, toAcc);
            ps.executeUpdate();
            System.out.println("Transfer successful");
        } catch (Exception e) {}
    }
}
