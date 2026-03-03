import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;

public class ExaminationScoresPanel extends JPanel {

    private int userId;

    public ExaminationScoresPanel(int userId) {
        this.userId = userId;
        setLayout(new BorderLayout());
        initUI();
        loadData();
    }

    private void initUI() {
  // Create the table model with overridden isCellEditable method
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Allow editing only in columns for "Username", "Password", and "Phone Number"
            // Modify the condition based on the exact columns you want to be editable
            String columnName = getColumnName(column);
            return columnName.equals("Username") || columnName.equals("Password") || columnName.equals("Phone Number");
        }
    };

    // Add columns to the model
    model.addColumn("Exam Name");
    model.addColumn("Score");
    model.addColumn("Exam Date");

    // Create the JTable with the model
    JTable table = new JTable(model);
    table.setFillsViewportHeight(true);

    // Customize the JTable appearance
    table.setRowHeight(30);
    table.setFont(new Font("Arial", Font.PLAIN, 14));
    table.setSelectionBackground(Color.BLUE);
    table.setSelectionForeground(Color.WHITE);

    // Add table header customization
    JTableHeader header = table.getTableHeader();
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setBackground(Color.GRAY);
    header.setForeground(Color.WHITE);

    // Add the JTable to a JScrollPane
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    // Create and add the print button
    JButton printButton = new JButton("Print");
    add(printButton, BorderLayout.SOUTH);

    // Action listener for the print button
    printButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Print the table
                MessageFormat headerFormat = new MessageFormat("Examination Scores Report");
                MessageFormat footerFormat = new MessageFormat("Page {0,number,integer}");
                table.print(JTable.PrintMode.FIT_WIDTH, headerFormat, footerFormat);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
}

    private void loadData() {
        String query = "SELECT exam_name, score, exam_date FROM examination_scores WHERE user_id = ?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearn", "root", "Hellohi.rappers@1992");
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, userId);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Get the table model
            DefaultTableModel model = (DefaultTableModel) ((JTable) ((JScrollPane) getComponent(0)).getViewport().getView()).getModel();

            // Clear existing rows
            model.setRowCount(0);

            // Add rows to the model
            while (rs.next()) {
                String examName = rs.getString("exam_name");
                float score = rs.getFloat("score");
                Date examDate = rs.getDate("exam_date");
                model.addRow(new Object[]{examName, score, examDate});
            }

            // Close the ResultSet
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
}
 public static void main(String[] args) {
        // Set up the frame
        JFrame frame = new JFrame("Examination Scores Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        // Create an instance of ExaminationScoresPanel with a sample userId
        int sampleUserId = 1; // Replace with an actual userId if available
        ExaminationScoresPanel panel = new ExaminationScoresPanel(sampleUserId);
        
        // Add the panel to the frame
        frame.setContentPane(panel);
        
        // Display the frame
        frame.setVisible(true);
    }
}