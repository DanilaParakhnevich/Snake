package by.parakhnevich.snake.gui;


import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public GUI() {
        initUI();
    }

    private void initUI() {
        add(new SnakeBoard());
        setResizable(false);
        pack();

        setTitle("Snake");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
