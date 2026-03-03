import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProfilePanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField fullNameField;
    private JTextField genderField;
    private JTextField phoneNumberField;
    private JTextField levelField;
    private JTextField departmentField;
    private JButton updateButton;
    private JButton closeButton;
    private int currentUserId;

    public UserProfilePanel() throws SQLException {
        // Retrieve user_id from GlobalUserStore
        currentUserId = GlobalUserStore.getUserId();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Set up fields and labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        emailField.setEditable(false); // Email should not be editable
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameField = new JTextField(20);
        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(20);
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(20);
        JLabel levelLabel = new JLabel("Level:");
        levelField = new JTextField(20);
        JLabel departmentLabel = new JLabel("Department:");
        departmentField = new JTextField(20);

        // Update button
        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(0x007BFF)); // Bootstrap primary blue color
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Close button
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(0xDC3545)); // Bootstrap danger red color
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);

        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(fullNameLabel, gbc);

        gbc.gridx = 1;
        add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(genderLabel, gbc);

        gbc.gridx = 1;
        add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(phoneNumberLabel, gbc);

        gbc.gridx = 1;
        add(phoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(levelLabel, gbc);

        gbc.gridx = 1;
        add(levelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(departmentLabel, gbc);

        gbc.gridx = 1;
        add(departmentField, gbc);

        // Add buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);
        buttonPanel.add(closeButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateUserProfile();
                } catch (SQLException ex) {
                    Logger.getLogger(UserProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closePanel();
            }
        });

        fetchUserProfile();
    }

    private void fetchUserProfile() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        String query = "SELECT username, password, email, full_name, gender, phone_number, level, department FROM users WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usernameField.setText(rs.getString("username"));
                passwordField.setText(rs.getString("password"));
                emailField.setText(rs.getString("email"));
                fullNameField.setText(rs.getString("full_name"));
                genderField.setText(rs.getString("gender"));
                phoneNumberField.setText(rs.getString("phone_number"));
                levelField.setText(rs.getString("level"));
                departmentField.setText(rs.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserProfile() throws SQLException {
        Connection connection = DatabaseUtil.getConnection();
        String query = "UPDATE users SET username = ?, password = ?, full_name = ?, gender = ?, phone_number = ?, level = ?, department = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, new String(passwordField.getPassword())); // Consider hashing the password before saving
            stmt.setString(3, fullNameField.getText());
            stmt.setString(4, genderField.getText());
            stmt.setString(5, phoneNumberField.getText());
            stmt.setString(6, levelField.getText());
            stmt.setString(7, departmentField.getText());
            stmt.setInt(8, currentUserId); // Use class-level variable
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Profile update failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closePanel() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }

    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new UserProfilePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
