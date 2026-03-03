import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordChangePanel extends JPanel {
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton changePasswordButton;
    private JLabel statusLabel;

    public PasswordChangePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(emailField, gbc);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(20);
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(newPasswordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        formPanel.add(confirmPasswordField, gbc);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(changePasswordButton, gbc);

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 5;
        formPanel.add(statusLabel, gbc);

        // Action listener for the change password button
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (email.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    statusLabel.setText("Please fill in all fields");
                    statusLabel.setForeground(Color.RED);
                } else {
                    if (verifyTeacherEmail(email)) {
                        if (newPassword.equals(confirmPassword)) {
                            String hashedNewPassword = PasswordUtils.hashPassword(newPassword);
                            if (changeTeacherPassword(email, hashedNewPassword)) {
                                if (saveTeacherDetails(email)) {
                                    statusLabel.setText("Password changed successfully. Teacher details saved.");
                                    statusLabel.setForeground(Color.GREEN);
                                } else {
                                    statusLabel.setText("Failed to save teacher details.");
                                    statusLabel.setForeground(Color.RED);
                                }
                            } else {
                                statusLabel.setText("Failed to change password");
                                statusLabel.setForeground(Color.RED);
                            }
                        } else {
                            statusLabel.setText("Passwords do not match");
                            statusLabel.setForeground(Color.RED);
                        }
                    } else {
                        statusLabel.setText("Email verification failed");
                        statusLabel.setForeground(Color.RED);
                    }
                }
            }
        });

        add(formPanel, BorderLayout.CENTER);
    }

    // Method to verify email in the database
    private boolean verifyTeacherEmail(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseUtil.getConnection();
            String query = "SELECT * FROM users WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            return stmt.executeQuery().next(); // returns true if email exists in database
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to change password in the database
    private boolean changeTeacherPassword(String email, String newPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseUtil.getConnection();
            String query = "UPDATE users SET password = ? WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to save teacher details to teachers table
    private boolean saveTeacherDetails(String email) {
        Connection conn = null;
        PreparedStatement stmtSelect = null;
        PreparedStatement stmtInsert = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // Retrieve teacher details from users table
            String selectQuery = "SELECT user_id, full_name, email, gender, phone_number, department FROM users WHERE email = ?";
            stmtSelect = conn.prepareStatement(selectQuery);
            stmtSelect.setString(1, email);
            var resultSet = stmtSelect.executeQuery();
            
            if (resultSet.next()) {
                // Check if teacher already exists in teachers table
                if (!teacherExists(email, conn)) {
                    // Insert into teachers table
                    String insertQuery = "INSERT INTO teachers (user_id, full_name, email, gender, phone_number, department) VALUES (?, ?, ?, ?, ?, ?)";
                    stmtInsert = conn.prepareStatement(insertQuery);
                    stmtInsert.setInt(1, resultSet.getInt("user_id"));
                    stmtInsert.setString(2, resultSet.getString("full_name"));
                    stmtInsert.setString(3, resultSet.getString("email"));
                    stmtInsert.setString(4, resultSet.getString("gender"));
                    stmtInsert.setString(5, resultSet.getString("phone_number"));
                    stmtInsert.setString(6, resultSet.getString("department"));
                    
                    int rowsInserted = stmtInsert.executeUpdate();
                    return rowsInserted > 0;
                } else {
                    // Teacher already exists in teachers table, no need to insert
                    return true;
                }
            } else {
                return false; // Email not found in users table
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (stmtSelect != null) stmtSelect.close();
                if (stmtInsert != null) stmtInsert.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Method to check if teacher exists in teachers table
    private boolean teacherExists(String email, Connection conn) throws SQLException {
        PreparedStatement stmt = null;
        try {
            String query = "SELECT * FROM teachers WHERE email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            var resultSet = stmt.executeQuery();
            return resultSet.next(); // returns true if teacher exists in teachers table
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Password Change");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new PasswordChangePanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
