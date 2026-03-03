import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EnglishQuizPanel extends JPanel {
    private JTextArea questionArea;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton submitButton, exitButton;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft = 60; // Time in seconds
    private int currentQuestionIndex = 0;
    private int score = 0;

    private List<Question> questions;

    public EnglishQuizPanel() {
        setLayout(new BorderLayout());

        // Create questions
        questions = new ArrayList<>();
        questions.add(new Question("Which of the following is a synonym of 'happy'?", "Sad", "Content", "Angry", "Lonely", "Content"));
        questions.add(new Question("What is the past tense of 'run'?", "Running", "Ran", "Runs", "Run", "Ran"));
        questions.add(new Question("Which sentence is correctly punctuated?", "Can I have some more, please?", "Can I have some more please?", "Can I have some, more please?", "Can I have some more please", "Can I have some more, please?"));

        // Create components
        questionArea = new JTextArea(5, 30);
        questionArea.setEditable(false);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        submitButton = new JButton("Submit");
        exitButton = new JButton("Exit");
        timerLabel = new JLabel("Time Left: 60");

        // Add components to panel
        JPanel questionPanel = new JPanel();
        questionPanel.add(questionArea);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(4, 1));
        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(submitButton);
        bottomPanel.add(exitButton);
        bottomPanel.add(timerLabel);

        add(questionPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set up timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time Left: " + timeLeft);
                } else {
                    ((Timer) e.getSource()).stop();
                    submitButton.setEnabled(false);
                    String message;
                    if (score > 1) {
                        message = "CONGRATULATIONS, YOU PASSED!";
                    } else {
                        message = "DON'T GIVE UP, KEEP TRYING.";
                    }
                    JOptionPane.showMessageDialog(null, "Time is up! Your total score is: " + score + "\n" + message);
                }
            }
        });
        timer.start();

        // Load the first question
        loadQuestion();

        // Add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Evaluate answers here
                Question currentQuestion = questions.get(currentQuestionIndex);
                String selectedAnswer = null;
                if (option1.isSelected()) {
                    selectedAnswer = option1.getText();
                } else if (option2.isSelected()) {
                    selectedAnswer = option2.getText();
                } else if (option3.isSelected()) {
                    selectedAnswer = option3.getText();
                } else if (option4.isSelected()) {
                    selectedAnswer = option4.getText();
                }

                if (selectedAnswer != null) {
                    if (currentQuestion.getCorrectAnswer().equals(selectedAnswer)) {
                        score++;
                    }
                }

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    loadQuestion();
                } else {
                    timer.stop();
                    submitButton.setEnabled(false);

                    // Check the score and display the appropriate message
                    String message;
                    if (score > 1) {
                        message = "CONGRATULATIONS, YOU PASSED!";
                    } else {
                        message = "DON'T GIVE UP, KEEP TRYING.";
                    }

                    JOptionPane.showMessageDialog(null, "Quiz completed! Your total score is: " + score + "\n" + message);
                }
            }
        });

        // Add action listener for exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the quiz?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Window window = SwingUtilities.getWindowAncestor(EnglishQuizPanel.this);
                    window.dispose();
                }
            }
        });
    }

    private void loadQuestion() {
        Question question = questions.get(currentQuestionIndex);
        questionArea.setText(question.getQuestion());
        option1.setText(question.getOption1());
        option2.setText(question.getOption2());
        option3.setText(question.getOption3());
        option4.setText(question.getOption4());
        optionsGroup.clearSelection();
    }

    // Inner class to represent a question
    private class Question {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private String correctAnswer;

        public Question(String question, String option1, String option2, String option3, String option4, String correctAnswer) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() {
            return question;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }
    }
}
