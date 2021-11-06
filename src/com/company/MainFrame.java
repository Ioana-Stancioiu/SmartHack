package com.company;

import javax.swing.*;

public class MainFrame extends JFrame {
    private JButton Login;
    private JTextField tf;
    private JTextField tf2;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Manager de parole");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
    }
}
