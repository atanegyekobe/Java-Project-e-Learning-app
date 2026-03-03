import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.net.URI;

public class EnglishPanel extends JPanel {
    private int currentSection = 0; // Tracks the current section

    public EnglishPanel() {
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
                openPDFLink("https://drive.google.com/drive/folders/1d49uxTGbQdHgnAKLS5IydTRoKQtnztnA?usp=sharing"); // Update this link with the actual URL
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
                return "<html><h2>Understanding Synonyms</h2>\n" +
"<p>Synonyms are words that have the same or similar meaning as another word. For example, the word \"happy\" has several synonyms, including \"content,\" \"joyful,\" and \"cheerful.\"</p>\n" +
"<html><h3>Synonyms for 'Happy'</h3>\n" +
"<ul>\n" +
"    <li><b>Content:</b> Feeling satisfied or pleased with what you have.</li>\n" +
"    <li><b>Joyful:</b> Experiencing great pleasure or happiness.</li>\n" +
"    <li><b>Cheerful:</b> Displaying a positive and optimistic attitude.</li>\n" +
"</ul>\n" +
"<html><h4>Example Sentence:</h4>\n" +
"<p>\"She felt content after spending the day with her family.\"</p>\n" +
"<html><h3>Why Synonyms Matter</h3>\n" +
"<p>Using synonyms can enhance your writing by avoiding repetition and providing more precise expressions. For instance, if you keep using the word \"happy,\" varying your language with synonyms like \"content\" or \"joyful\" can make your writing more engaging.</p></html>";
            case 1:
                return "<html><h2>Understanding Verb Tenses</h2>\n" +
"<p>Verbs change form to indicate different tenses. The past tense of a verb tells us that an action has already occurred.</p>\n" +
"<html><h3>Past Tense of 'Run'</h3>\n" +
"<ul>\n" +
"    <li><b>Ran:</b> The past tense of the verb \"run.\"</li>\n" +
"</ul>\n" +
"<html><h4>Examples:</h4>\n" +
"<ul>\n" +
"    <li>Present Tense: \"I run every morning.\"</li>\n" +
"    <li>Past Tense: \"Yesterday, I ran in the park.\"</li>\n" +
"</ul>\n" +
"<html><h3>Why Tenses Matter</h3>\n" +
"<p>Using the correct verb tense is crucial for clear communication. It helps the reader understand when an action took place. For instance, \"ran\" indicates that the running happened in the past.</p></html>";
            case 2:
                return "<html><h2>Proper Punctuation Usage</h2>\n" +
"<p>Punctuation helps clarify meaning and makes written communication more effective. Proper punctuation ensures that sentences are understood as intended.</p>\n" +
"<html><h3>Correct Punctuation</h3>\n" +
"<p>\"Can I have some more, please?\"</p>\n" +
"<html><h4>Explanation:</h4>\n" +
"<ul>\n" +
"    <li><b>Comma Usage:</b> A comma is used before \"please\" to separate the request from the polite closing.</li>\n" +
"</ul>\n" +
"<html><h4>Incorrect Options:</h4>\n" +
"<ul>\n" +
"    <li>\"Can I have some more please?\" (Missing comma)</li>\n" +
"    <li>\"Can I have some, more please?\" (Incorrect comma placement)</li>\n" +
"    <li>\"Can I have some more please\" (Missing comma at the end)</li>\n" +
"</ul>\n" +
"<html><h3>Why Punctuation Matters</h3>\n" +
"<p>Proper punctuation can change the meaning of a sentence and make your writing clearer. For example, \"Let's eat, grandma!\" versus \"Let's eat grandma!\" illustrates how a comma can completely alter the meaning.</p></html>";
            default:
                return "<html><h2>Summary</h2>\n" +
"<p>Synonyms: Understanding synonyms helps in enhancing vocabulary and improving writing style.</p>\n" +
"<p>Verb Tenses: Knowing verb tenses, like the past tense of \"run\" (ran), is essential for accurate communication.</p>\n" +
"<p>Punctuation: Correct punctuation, such as placing a comma before \"please,\" ensures clarity and correct meaning in sentences.</p>\n" +
"</html>";
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
        JFrame frame = new JFrame("English Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new EnglishPanel());
        frame.setVisible(true);
    }
}