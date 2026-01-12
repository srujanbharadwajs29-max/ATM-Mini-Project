import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ATMUI {

    ATM atm = new ATM();

    public ATMUI() {
        showLogin();
    }

    void showLogin() {
        JFrame f = new JFrame("ATM Login");
        f.setSize(350, 250);
        f.setLayout(new GridLayout(4, 2));

        JLabel l1 = new JLabel("Account No:");
        JTextField acc = new JTextField();

        JLabel l2 = new JLabel("PIN:");
        JPasswordField pin = new JPasswordField();

        JButton login = new JButton("Login");

        f.add(l1); f.add(acc);
        f.add(l2); f.add(pin);
        f.add(new JLabel()); f.add(login);

        login.addActionListener(e -> {
            try {
                int a = Integer.parseInt(acc.getText());
                int p = Integer.parseInt(new String(pin.getPassword()));

                if (atm.login(a, p)) {
                    f.dispose();
                    showMenu();
                } else {
                    JOptionPane.showMessageDialog(f, "Invalid Login");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Enter valid numbers");
            }
        });

        f.setVisible(true);
    }

    void showMenu() {
        JFrame f = new JFrame("ATM Menu");
        f.setSize(400, 300);
        f.setLayout(new GridLayout(5, 1));

        JButton bal = new JButton("Check Balance");
        JButton dep = new JButton("Deposit");
        JButton wit = new JButton("Withdraw");
        JButton tran = new JButton("Transfer");
        JButton exit = new JButton("Exit");

        f.add(bal);
        f.add(dep);
        f.add(wit);
        f.add(tran);
        f.add(exit);

        bal.addActionListener(e -> atm.checkBalance());

        dep.addActionListener(e -> {
            try {
                String amt = JOptionPane.showInputDialog("Enter Amount:");
                atm.deposit(Double.parseDouble(amt));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Invalid amount");
            }
        });

        wit.addActionListener(e -> {
            try {
                String amt = JOptionPane.showInputDialog("Enter Amount:");
                atm.withdraw(Double.parseDouble(amt));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Invalid amount");
            }
        });

        tran.addActionListener(e -> {
            try {
                String acc = JOptionPane.showInputDialog("Receiver Account:");
                String amt = JOptionPane.showInputDialog("Amount:");
                atm.transfer(Integer.parseInt(acc), Double.parseDouble(amt));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Invalid input");
            }
        });

        exit.addActionListener(e -> System.exit(0));

        f.setVisible(true);
    }

    public static void main(String[] args) {
        new ATMUI();
    }
}
