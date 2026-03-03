import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.net.URI;

public class ChemistryPanel extends JPanel {
    private int currentSection = 0; // Tracks the current section

    public ChemistryPanel() {
        setLayout(new BorderLayout());
        displaySection(currentSection); // Display the first section initially
    }

    private void displaySection(int section) {
        removeAll(); // Clear the panel before adding new content

        // Create a JLabel for the subject information
        JLabel subjectInfo = new JLabel(getSectionContent(section));
        subjectInfo.setHorizontalAlignment(JLabel.CENTER);

        // Add the subject information label to the top of the panel
        add(subjectInfo, BorderLayout.NORTH);

        // Create a JPanel for the buttons and the links
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Adjusted for the Back button

        // Create a smaller button to navigate to the previous page
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(20, 10)); // Set preferred size
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSection--;
                if (currentSection >= 0) { // Check bounds
                    displaySection(currentSection);
                } else {
                    JOptionPane.showMessageDialog(null, "You are already on the first section.");
                    currentSection = 0; // Reset to first section
                }
            }
        });

        // Create a smaller button to navigate to the next page
        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(20, 10)); // Set preferred size
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentSection++;
                if (currentSection < 3) { // Assume there are 3 sections
                    displaySection(currentSection);
                } else {
                    JOptionPane.showMessageDialog(null, "You have reached the end of the material.");
                }
            }
        });

        // Add the back button to the bottom panel
        bottomPanel.add(backButton);
        // Add the next button to the bottom panel
        bottomPanel.add(nextButton);

        // Create links to PDF resources
        JPanel linkPanel = new JPanel(new FlowLayout());
        JLabel pdfLinkLabel = new JLabel("<html><a href='#'>Click to Download PDF</a></html>");
        pdfLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pdfLinkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openPDFLink("https://drive.google.com/drive/folders/1_NSTrBcmjwl9fMwck8oF9vAlMcPXiZub?usp=sharing");
            }
        });

        // Add the link to the link panel
        linkPanel.add(pdfLinkLabel);

        // Add the link panel to the bottom panel
        bottomPanel.add(linkPanel);

        // Add the bottom panel to the center of the main panel
        add(bottomPanel, BorderLayout.CENTER);

        // Refresh the panel
        revalidate();
        repaint();
    }

    private String getSectionContent(int section) {
        switch (section) {
            case 0:
                return "<html><h2>Chemistry</h2><p>Chemistry:\n" +
                    "<html><h3>Chemical Symbols and Compounds</h3>\n" +
                    "• Understanding Chemical Symbols\n" +
                    "Chemical symbols are shorthand representations of elements. For example, the symbol for water is \"H₂O,\" which represents two hydrogen atoms bonded with one oxygen atom.\n" +
                    "<html><h4>Related Question: What is the chemical symbol for water? Answer: H₂O</h4>\n" +
                    "<html><h3>Acids, Bases, and Salts</h3>\n" +
                    "• Introduction to Acids and Bases\n" +
                    "Acids are substances that donate protons (H⁺ ions) in a reaction, while bases accept protons. Common examples include hydrochloric acid (HCl) and sodium hydroxide (NaOH).\n" +
                    "<html><h4>Related Question: What is the formula for common table salt? Answer: NaCl</h4>\n" +
                    "<html><h3> Atomic Structure</h3>\n" +
                    "• The Structure of an Atom\n" +
                    "Atoms consist of a nucleus containing protons and neutrons, surrounded by electrons in orbitals. Understanding atomic structure is key to grasping chemical reactions.\n" +
                    "<html><h4>Related Question: What is the charge of an electron? Answer: Negative</h4>\n" +
                    "<html><h3> Chemical Reactions</h3>\n" +
                    "• Types of Chemical Reactions\n" +
                    "Chemical reactions involve the rearrangement of atoms to form new substances. Types include synthesis, decomposition, single displacement, and double displacement reactions.\n" +
                    "<html><h4>Related Question: What happens when hydrogen reacts with oxygen? Answer: Water (H₂O) is formed.</h4>\n" +
                    "<html><h3>The Periodic Table</h3>\n" +
                    "• Elements and Their Properties\n" +
                    "The periodic table organizes elements based on their properties. Elements in the same group have similar chemical behaviors.\n" +
                    "<html><h4>Related Question: Which element is represented by the symbol 'Au'? Answer: Gold.</p></html>";
            case 1:
                return "<html><h2>PHYSICS</h2>\n" +
                    "<html><h3> Planets and the Solar System</h3>\n" +
                    "• Exploring the Planets\n" +
                    "The solar system comprises eight planets, each with unique characteristics. Mars is known as the Red Planet due to its reddish appearance from iron oxide (rust) on its surface.\n" +
                    "<html><h4>Related Question: What planet is known as the Red Planet? Answer: Mars</h4>\n" +
                    "<html><h3>Forces and Motion</h3>\n" +
                    "• Newton's Laws of Motion\n" +
                    "Newton's three laws of motion describe the relationship between a body and the forces acting upon it. These laws are fundamental in understanding how objects move.\n" +
                    "<html><h4>Related Question: What force keeps the planets in orbit around the Sun? Answer: Gravity</h4>\n" +
                    "<html><h3>Energy Forms</h3>\n" +
                    "• Different Forms of Energy\n" +
                    "Energy can exist in various forms, such as kinetic, potential, thermal, and chemical energy. Understanding these forms is crucial in physics.\n" +
                    "<html><h4>Related Question: What type of energy is stored in a stretched rubber band? Answer: Potential Energy</h4>\n" +
                    "<html><h3> Light and Optics</h3>\n" +
                    "• The Nature of Light\n" +
                    "Light behaves both as a wave and a particle. It travels in straight lines and can be reflected, refracted, or absorbed by different materials.\n" +
                    "<html><h4>Related Question: What phenomenon explains the bending of light as it passes from air into water? Answer: Refraction</h4>\n" +
                    "<html><h3>Electricity and Magnetism</h3>\n" +
                    "• Understanding Electric Circuits\n" +
                    "Electric circuits are pathways through which electric current flows. Key components include resistors, capacitors, and inductors.\n" +
                    "<html><h4>Related Question: What is the unit of electrical resistance? Answer: Ohm (Ω).</p></html>";
            case 2:
                return "<html><h2>Chemical Reactions</h2><p>Biology:\n" +
                    "<html><h3> The Structure of Cells</h3>\n" +
                    "• Understanding Cell Anatomy\n" +
                    "Cells are the basic building blocks of all living organisms. They contain organelles like the nucleus, mitochondria, and ribosomes, each with a specific function.\n" +
                    "<html><h4>Related Question: What is the powerhouse of the cell? Answer: Mitochondria</h4>\n" +
                    "<html><h3>Human Anatomy</h3>\n" +
                    "• The Circulatory System\n" +
                    "The circulatory system consists of the heart, blood, and blood vessels. It is responsible for transporting oxygen and nutrients throughout the body.\n" +
                    "<html><h3>Related Question: Which organ is responsible for pumping blood through the body? Answer: The Heart</h4>\n" +
                    "<html><h3> Genetics</h3>\n" +
                    "• Topic: Introduction to Genetics\n" +
                    "Genetics is the study of heredity and variation in organisms. It involves understanding DNA, genes, and how traits are passed from parents to offspring.\n" +
                    "<html><h4>Related Question: What molecule carries genetic information? Answer: DNA</h4>\n" +
                    "<html><h3> Evolution and Natural Selection</h3>\n" +
                    "• The Theory of Evolution\n" +
                    "Evolution is the process through which populations of organisms change over time. Natural selection is a key mechanism driving this process.\n" +
                    "<html><h4>Related Question: Who proposed the theory of evolution by natural selection? Answer: Charles Darwin</h4>\n" +
                    "<html><h3>Ecology and Ecosystems</h3>\n" +
                    "• Understanding Ecosystems\n" +
                    "Ecosystems consist of living organisms interacting with each other and their environment. Key components include producers, consumers, and decomposers.\n" +
                    "<html><h4>Related Question: What is the role of decomposers in an ecosystem? Answer: They break down dead organic matter and recycle nutrients.</p></html>";
            default:
                return "<html><h2>Welcome to the Chemistry Section</h2></html>";
        }
    }

    private void openPDFLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to open the link.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chemistry Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new ChemistryPanel());
        frame.setVisible(true);
    }
}
