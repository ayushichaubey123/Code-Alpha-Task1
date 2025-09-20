
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTracker extends JFrame {
    private final JTextField nameField, gradeField;
    private final JTextArea displayArea;
    private final ArrayList<String> studentNames = new ArrayList<>();
    private final ArrayList<Integer> studentGrades = new ArrayList<>();

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        final JButton addButton = new JButton("Add Student");
        final JButton summaryButton = new JButton("Show Summary");
        final JButton clearButton = new JButton("Clear All");

        inputPanel.add(addButton);
        inputPanel.add(summaryButton);

        add(inputPanel, BorderLayout.NORTH);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Bottom Panel with Clear Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(clearButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions (using lambdas)
        addButton.addActionListener(e -> addStudent());
        summaryButton.addActionListener(e -> showSummary());
        clearButton.addActionListener(e -> clearAll());
    }

    private void addStudent() {
        final String name = nameField.getText().trim();
        final String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade!");
            return;
        }

        try {
            final int grade = Integer.parseInt(gradeText);
            studentNames.add(name);
            studentGrades.add(grade);
            displayArea.append(name + " - " + grade + "\n");

            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for grade!");
        }
    }

    private void showSummary() {
        if (studentGrades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students added yet!");
            return;
        }

        int total = 0;
        int highest = studentGrades.get(0);
        int lowest = studentGrades.get(0);
        String topStudent = studentNames.get(0);
        String lowStudent = studentNames.get(0);

        for (int i = 0; i < studentGrades.size(); i++) {
            int grade = studentGrades.get(i);
            String name = studentNames.get(i);

            total += grade;
            if (grade > highest) {
                highest = grade;
                topStudent = name;
            }
            if (grade < lowest) {
                lowest = grade;
                lowStudent = name;
            }
        }

        double average = (double) total / studentGrades.size();

        displayArea.append("\n--- Summary ---\n");
        displayArea.append("Total Students: " + studentGrades.size() + "\n");
        displayArea.append("Average Score: " + String.format("%.2f", average) + "\n");
        displayArea.append("Highest Score: " + highest + " (" + topStudent + ")\n");
        displayArea.append("Lowest Score: " + lowest + " (" + lowStudent + ")\n\n");
    }

    private void clearAll() {
        studentNames.clear();
        studentGrades.clear();
        displayArea.setText("");
        JOptionPane.showMessageDialog(this, "All data cleared!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTracker().setVisible(true));
    }
}
