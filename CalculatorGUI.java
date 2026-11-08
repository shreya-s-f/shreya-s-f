import javax.swing.*;
import java.awt.*;

public class CalculatorGUI extends JFrame {

    private JTextField display;
    private double firstNumber;
    private String operator;

    public CalculatorGUI() {

        setTitle("Smart Calculator");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);

        add(display, BorderLayout.NORTH);

        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "%", "⌫", "+/-"
        };

        for (String text : buttons) {

            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));

            button.addActionListener(e -> handleButton(text));

            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void handleButton(String text) {

        // Numbers and decimal point
        if (text.matches("[0-9]") || text.equals(".")) {

            display.setText(display.getText() + text);
        }

        // Clear
        else if (text.equals("C")) {

            display.setText("");
            firstNumber = 0;
            operator = null;
        }

        // Backspace
        else if (text.equals("⌫")) {

            String current = display.getText();

            if (!current.isEmpty()) {
                display.setText(
                        current.substring(0, current.length() - 1)
                );
            }
        }

        // Positive / Negative
        else if (text.equals("+/-")) {

            if (!display.getText().isEmpty()) {

                double value =
                        Double.parseDouble(display.getText());

                display.setText(String.valueOf(-value));
            }
        }

        // Operators
        else if (
                text.equals("+") ||
                text.equals("-") ||
                text.equals("*") ||
                text.equals("/") ||
                text.equals("%")
        ) {

            if (!display.getText().isEmpty()) {

                firstNumber =
                        Double.parseDouble(display.getText());

                operator = text;

                display.setText("");
            }
        }

        // Equal
        else if (text.equals("=")) {

            if (!display.getText().isEmpty()
                    && operator != null) {

                double secondNumber =
                        Double.parseDouble(display.getText());

                Calculator calculator =
                        new Calculator();

                try {

                    double result =
                            calculator.calculate(
                                    firstNumber,
                                    secondNumber,
                                    operator
                            );

                    display.setText(
                            String.valueOf(result)
                    );

                } catch (ArithmeticException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    display.setText("");
                }

                operator = null;
            }
        }
    }

    public static void main(String[] args) {

        new CalculatorGUI();
    }
}