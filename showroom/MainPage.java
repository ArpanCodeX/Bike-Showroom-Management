package showroom;

import java.awt.*;
import javax.swing.*;
import showroom.brands.Bajaj.view.BajajView;
import showroom.brands.Hero.view.HeroView;
import showroom.brands.Honda.view.HondaView;
import showroom.brands.KTM.view.KTMView;
import showroom.brands.RoyalEnfield.view.RoyalEnfieldView;

public class MainPage extends JFrame {

    public MainPage(String username) {
        setTitle("Bike Showroom Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setLayout(new GridBagLayout());
        add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("Welcome to Bike Showroom Dashboard");
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(label);

        // Show floating message
        showFloatingMessage(username);
    }

    private void showFloatingMessage(String username) {
        JWindow popup = new JWindow();
        popup.setLayout(new FlowLayout());
        JLabel msg = new JLabel("Hello, " + username + "!");
        msg.setFont(new Font("SansSerif", Font.BOLD, 16));
        msg.setForeground(Color.WHITE);

        JPanel msgPanel = new JPanel();
        msgPanel.setBackground(new Color(46, 204, 113));
        msgPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        msgPanel.add(msg);
        popup.add(msgPanel);

        popup.setSize(200, 80);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        popup.setLocation(screenSize.width / 2 - 100, screenSize.height / 2 - 150);
        popup.setVisible(true);

        // Hide after 2 seconds and open company selector
        Timer timer = new Timer(2000, e -> {
            popup.setVisible(false);
            popup.dispose();
            showCompanySelector();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void showCompanySelector() {
        String[] companies = {"Honda", "Hero", "Royal Enfield", "Bajaj", "KTM"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Which company do you prefer?",
                "Select Company",
                JOptionPane.QUESTION_MESSAGE,
                null,
                companies,
                companies[0]
        );

        if (selected != null) {
            openCompanyDashboard(selected);
        }
    }

    private void openCompanyDashboard(String company) {
        switch (company) {
            case "Honda":
                new HondaView().setVisible(true);
                break;
            case "Hero":
                new HeroView().setVisible(true);
                break;
            case "Royal Enfield":
                new RoyalEnfieldView().setVisible(true);
                break;
            case "Bajaj":
                new BajajView().setVisible(true);
                break;
            case "KTM":
                new KTMView().setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid selection");
        }
        dispose(); // close main page
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainPage("User").setVisible(true));
    }
}


