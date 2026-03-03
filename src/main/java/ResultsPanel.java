import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.MessageFormat;

public class ResultsPanel extends JPanel {

    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private int userId; // Store user ID here

    public ResultsPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());

        // Initialize JTable and its model
        tableModel = new DefaultTableModel(new String[]{"Full Name", "Quiz Name", "Score", "Timestamp"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        resultsTable = new JTable(tableModel);

        // Customize the JTable appearance for a modern look
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        resultsTable.setRowHeight(30);
        resultsTable.setGridColor(new Color(224, 224, 224));
        resultsTable.setSelectionBackground(new Color(96, 125, 139));
        resultsTable.setSelectionForeground(Color.WHITE);

        JTableHeader tableHeader = resultsTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
        tableHeader.setBackground(new Color(63, 81, 181));
        tableHeader.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Add the print button
        JButton printButton = new JButton("Print");
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        printButton.setBackground(new Color(63, 81, 181));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);

        // Action listener for the print button
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageFormat header = new MessageFormat("Quiz Results");
                    MessageFormat footer = new MessageFormat("Page {0,number,integer}");
                    resultsTable.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to print table: " + ex.getMessage());
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadResults(); // Load results when panel is created
    }

    private void loadResults() {
        try {
            Connection conn = DatabaseUtil.getConnection(); // Replace with your method to get the database connection

            // Fetch the full name from the users table
            String fullName = "";
            String queryUser = "SELECT full_name FROM users WHERE user_id = ?";
            PreparedStatement stmtUser = conn.prepareStatement(queryUser);
            stmtUser.setInt(1, userId);
            ResultSet rsUser = stmtUser.executeQuery();
            if (rsUser.next()) {
                fullName = rsUser.getString("full_name");
            }

            // Fetch quiz results for the user
            String queryResults = "SELECT quiz_name, score, timestamp FROM quiz_results WHERE user_id = ?";
            PreparedStatement stmtResults = conn.prepareStatement(queryResults);
            stmtResults.setInt(1, userId);
            ResultSet rsResults = stmtResults.executeQuery();

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Add new data to the table
            while (rsResults.next()) {
                String quizName = rsResults.getString("quiz_name");
                int score = rsResults.getInt("score");
                Timestamp timestamp = rsResults.getTimestamp("timestamp");

                // Add data to the table
                tableModel.addRow(new Object[]{fullName, quizName, score, timestamp});
            }

            // Close resources
            rsUser.close();
            stmtUser.close();
            rsResults.close();
            stmtResults.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching quiz results: " + e.getMessage());
        }
    }
}
