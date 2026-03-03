import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfilePanel extends JPanel {
    private JTextField usernameField;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> departmentComboBox;

    private JButton closeButton;
    private JButton updateButton;

    public ProfilePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Set up components
        usernameField = new JTextField(20);
        fullNameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        departmentComboBox = new JComboBox<>(new String[]{"Visaul Arts", "Science", "Home Economics"});

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(new Color(0xDC3545)); // Bootstrap danger red color
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        updateButton = new JButton("Update");
        updateButton.setFont(new Font("Arial", Font.BOLD, 14));
        updateButton.setBackground(new Color(0x007BFF)); // Bootstrap primary blue color
        updateButton.setForeground(Color.WHITE);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add components to panel with GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Full Name:"), gbc);

        gbc.gridx = 1;
        add(fullNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Phone Number:"), gbc);

        gbc.gridx = 1;
        add(phoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Department:"), gbc);

        gbc.gridx = 1;
        add(departmentComboBox, gbc);

        // Add buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);
        buttonPanel.add(closeButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel, gbc);

        // Populate fields with user data
        populateFields();

        // Action listener for close button
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the panel or parent frame
                Window window = SwingUtilities.getWindowAncestor(ProfilePanel.this);
                if (window != null) {
                    window.dispose();
                }
            }
        });

        // Action listener for update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserDetails();
            }
        });
    }

    private void populateFields() {
        int userId = GlobalUserStore.getUserId(); // Retrieve user_id from GlobalUserStore

        String query = "SELECT username, full_name, email, phone_number, gender, department FROM users WHERE user_id = ?";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usernameField.setText(rs.getString("username"));
                    fullNameField.setText(rs.getString("full_name"));
                    emailField.setText(rs.getString("email"));
                    phoneNumberField.setText(rs.getString("phone_number"));
                    genderComboBox.setSelectedItem(rs.getString("gender"));
                    departmentComboBox.setSelectedItem(rs.getString("department"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving user details: " + e.getMessage());
        }
    }

    private void updateUserDetails() {
        int userId = GlobalUserStore.getUserId(); // Retrieve user_id from GlobalUserStore

        String query = "UPDATE users SET full_name = ?, email = ?, phone_number = ?, gender = ?, department = ? WHERE user_id = ?";

        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            // Set the parameters
            pstmt.setString(1, fullNameField.getText().trim());
            pstmt.setString(2, emailField.getText().trim());
            pstmt.setString(3, phoneNumberField.getText().trim());
            pstmt.setString(4, genderComboBox.getSelectedItem().toString());
            pstmt.setString(5, departmentComboBox.getSelectedItem().toString());
            pstmt.setInt(6, userId);

            // Execute update
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Profile updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating user details: " + e.getMessage());
        }
    }
}
