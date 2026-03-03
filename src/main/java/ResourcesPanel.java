import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.*;
import java.text.MessageFormat;

public class ResourcesPanel extends JPanel {

    private JTable resourcesTable;
    private JButton closeButton;
    private JButton printButton;
    private String subjectFilter;

    public ResourcesPanel(String subjectFilter) {
        this.subjectFilter = subjectFilter;
        setLayout(new BorderLayout());

        // Create the JTable with a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Resource Name");
        model.addColumn("Content");
        model.addColumn("Related Exam");

        resourcesTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(resourcesTable);
        add(scrollPane, BorderLayout.CENTER);

        // Set column widths
        TableColumn contentColumn = resourcesTable.getColumnModel().getColumn(1);
        contentColumn.setPreferredWidth(500); // Adjust this value as needed
//          adjustRowHeight();
        // Set cell renderer for content column to wrap text
        contentColumn.setCellRenderer(new WrapCellRenderer());
     
        // Create buttons
        JPanel buttonPanel = new JPanel();
        closeButton = new JButton("Close");
        printButton = new JButton("Print");

        buttonPanel.add(closeButton);
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(ResourcesPanel.this).dispose();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resourcesTable.print(JTable.PrintMode.FIT_WIDTH, new MessageFormat("Resources"), new MessageFormat("Page {0}"));
                } catch (PrinterException pe) {
                    pe.printStackTrace();
                }
            }
        });

        // Populate the table with data from the database
        populateTable();
    }

    private void populateTable() {
        // SQL query to fetch data from the resources table filtered by subject
        String query = "SELECT * FROM resources WHERE related_exam = ?";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/elearn", "root", "Hellohi.rappers@1992");
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, subjectFilter);
            ResultSet rs = pstmt.executeQuery();
            DefaultTableModel model = (DefaultTableModel) resourcesTable.getModel();

            // Add rows to the table
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getString("resource_name");
                row[1] = rs.getString("content");
                row[2] = rs.getString("related_exam");
                model.addRow(row);
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
