
import javax.swing.*;
import java.awt.*;

public class ATMUI {

    ATM atm = new ATM();

    public ATMUI() {
        showLogin();
    }

    // ================= COMMON BUTTON STYLE =================
    JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(40, 40, 40));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.RED));
        return btn;
    }

    // ================= SMS SIMULATION =================
    void sendSMS(String message) {
        JOptionPane.showMessageDialog(
                null,
                "📱 SMS ALERT\n\n" + message,
                "SMS Sent",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= LOGIN SCREEN =================
    void showLogin() {
        JFrame frame = new JFrame("ATM Login");
        frame.setSize(400, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 12, 12));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("ATM LOGIN", JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel accLbl = new JLabel("Account No:");
        JLabel pinLbl = new JLabel("PIN:");

        accLbl.setForeground(Color.WHITE);
        pinLbl.setForeground(Color.WHITE);

        JTextField accField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        JButton loginBtn = createButton("Login");

        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.NORTH);

        panel.add(accLbl);
        panel.add(accField);
        panel.add(pinLbl);
        panel.add(pinField);
        panel.add(new JLabel());
        panel.add(loginBtn);

        frame.add(panel, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            try {
                int acc = Integer.parseInt(accField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                if (atm.login(acc, pin)) {
                    frame.dispose();
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Login");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid numbers");
            }
        });

        frame.setVisible(true);
    }

    // ================= ATM MENU =================
    void showMenu() {
        JFrame frame = new JFrame("ATM Services");
        frame.setSize(450, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel title = new JLabel("ATM SERVICES", JLabel.CENTER);
        title.setForeground(Color.RED);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JPanel panel = new JPanel(new GridLayout(5, 1, 15, 15));
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 30, 60));

        JButton balBtn = createButton("Check Balance");
        JButton depBtn = createButton("Deposit");
        JButton witBtn = createButton("Withdraw");
        JButton tranBtn = createButton("Transfer");
        JButton exitBtn = createButton("Exit");

        panel.add(balBtn);
        panel.add(depBtn);
        panel.add(witBtn);
        panel.add(tranBtn);
        panel.add(exitBtn);

        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);

        // -------- CHECK BALANCE --------
        balBtn.addActionListener(e -> {
            double bal = atm.checkBalance();
            JOptionPane.showMessageDialog(frame, "Current Balance: ₹" + bal);
            sendSMS("Balance Enquiry Successful.\nAvailable Balance: ₹" + bal);
        });

        // -------- DEPOSIT --------
        depBtn.addActionListener(e -> {
            try {
                String amt = JOptionPane.showInputDialog(frame, "Enter Deposit Amount:");
                if (amt != null) {
                    double amount = Double.parseDouble(amt);
                    String msg = atm.deposit(amount);
                    JOptionPane.showMessageDialog(frame, msg);

                    double bal = atm.checkBalance();
                    sendSMS("₹" + amount + " deposited successfully.\nBalance: ₹" + bal);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount");
            }
        });

        // -------- WITHDRAW --------
        witBtn.addActionListener(e -> {
            try {
                String amt = JOptionPane.showInputDialog(frame, "Enter Withdraw Amount:");
                if (amt != null) {
                    double amount = Double.parseDouble(amt);
                    String msg = atm.withdraw(amount);
                    JOptionPane.showMessageDialog(frame, msg);

                    double bal = atm.checkBalance();
                    sendSMS("₹" + amount + " withdrawn.\nAvailable Balance: ₹" + bal);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid amount");
            }
        });

        // -------- TRANSFER --------
        tranBtn.addActionListener(e -> {
            try {
                String toAcc = JOptionPane.showInputDialog(frame, "Receiver Account:");
                String amt = JOptionPane.showInputDialog(frame, "Amount:");
                if (toAcc != null && amt != null) {
                    double amount = Double.parseDouble(amt);
                    String msg = atm.transfer(Integer.parseInt(toAcc), amount);
                    JOptionPane.showMessageDialog(frame, msg);

                    double bal = atm.checkBalance();
                    sendSMS("₹" + amount + " transferred to A/c " + toAcc +
                            ".\nBalance: ₹" + bal);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        exitBtn.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ATMUI();
    }
}
