package org.mccode.graphic;

import org.mccode.data.Expression;
import org.mccode.data.Memo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Display extends JFrame {
    private JTextField nameField;
    private JPanel mainPanel;
    private JTextField expField;
    private JButton saveExpression;
    private JTextField executeField;
    private JTextArea executionHistory;
    private JTextField searchExpression;
    private JList expressionsList;
    private JTextField searchVariable;
    private JList variableList;
    private JButton CALCULATEButton;
    private JScrollPane historyScrollPane;

    public Display() {
        setTitle("Memo-Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Memo.saveHistory();
                super.windowClosing(e);
            }
        });
        configure();
        this.pack();
    }

    private void configure() {

        var popup = new JPopupMenu();
        popup.add("Insert a valid expression and a valid name for it.");
        popup.setPreferredSize(new Dimension(300, 50));
        popup.setToolTipText("");
        saveExpression.setComponentPopupMenu(popup);
        saveExpression.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var name = nameField.getText();
                var exp = expField.getText();
                if (!name.isBlank() && !exp.isBlank() && !name.equals("Name for the expression") && !exp.equals("Expression logic")) {
                    nameField.setText("");
                    expField.setText("");
                    Memo.save(new Expression(name, exp), null, null);
                    reloadSearchExpression();
                }
                else {
                    System.out.println("I'm here");
                    saveExpression.getComponentPopupMenu().show(saveExpression, saveExpression.getX() - 600,saveExpression.getY() + 100);
                }
            }
        });

        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Name for the expression")) {
                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isBlank()) {
                    nameField.setText("Name for the expression");
                }
            }
        });

        expField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (expField.getText().equals("Expression logic")) {
                    expField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (expField.getText().isBlank()) {
                    expField.setText("Expression logic");
                }
            }
        });
    }

    private void reloadSearchExpression() {
    }
}
