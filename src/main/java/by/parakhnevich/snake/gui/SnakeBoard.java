package by.parakhnevich.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class SnakeBoard extends JPanel implements ActionListener {
    private static final String IMAGES_PATH = "src/main/resources/images/";
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private static final int B_WIDTH = 300;
    private static final int B_HEIGHT = 300;
    private static final int FIGURE_SIZE = 10;
    private static final int MAX_SNAKE_SIZE = (int) (B_WIDTH * B_HEIGHT / Math.pow(FIGURE_SIZE, 2));
    private final java.util.List<Integer> x = new ArrayList<>(MAX_SNAKE_SIZE);
    private final java.util.List<Integer> y = new ArrayList<>(MAX_SNAKE_SIZE);
    private int dotsCount = 3;
    private int appleX;
    private int appleY;
    private Image head;
    private Image body;
    private Image apple;
    private final Timer timer = new Timer(70, this);



    public SnakeBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        for (int i = 0; i < B_WIDTH / FIGURE_SIZE; i++) {
            x.add(0);
            y.add(0);
        }
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {
        head = new ImageIcon(IMAGES_PATH + "head.png").getImage();
        body = new ImageIcon(IMAGES_PATH + "dot.png").getImage();
        apple = new ImageIcon(IMAGES_PATH + "apple.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dotsCount; i++) {
                if (i == 0) {
                    g.drawImage(head, x.get(i), y.get(i), this);
                } else {
                    g.drawImage(body, x.get(i), y.get(i), this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else if (x.size() == MAX_SNAKE_SIZE) {
            sendMessage(g, "Victory!");
            timer.stop();
        } else {
            sendMessage(g, "Game over\nPress \'R\' to restart game");
            timer.stop();
        }
    }

    private void sendMessage(Graphics g, String message) {
        Font font = new Font("Helvetica", Font.BOLD, 14);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (B_WIDTH - 9) / 2, B_HEIGHT / 2);
    }


    private void initGame() {
        for (int i = 0; i < dotsCount; i++) {
            x.set(0, 50 - i * FIGURE_SIZE);
            y.set(0, 50);
        }
        locateApple();
        timer.start();
    }

    private void locateApple() {
        do {
            appleX = (FIGURE_SIZE * (int) (Math.random() * 29));
            appleY = (FIGURE_SIZE * (int) (Math.random() * 29));
        } while (x.contains(appleX) && y.contains(appleY));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private void move() {
        for (int i = dotsCount - 1; i > 0; --i) {
            x.set(i, x.get(i - 1));
            y.set(i, y.get(i - 1));
        }
        if (leftDirection) {
            x.set(0, x.get(0) - FIGURE_SIZE);
        } else if (rightDirection) {
            x.set(0, x.get(0) + FIGURE_SIZE);
        } else if (upDirection) {
            y.set(0, y.get(0) + FIGURE_SIZE);
        } else {
            y.set(0, y.get(0) - FIGURE_SIZE);
        }
    }

    private void checkCollision() {
        for (int i = dotsCount; i > 0; --i) {
            if ((i > 4) && (x.get(0).equals(x.get(i))) && (y.get(0).equals(y.get(i)))) {
                inGame = false;
                break;
            }
        }
        if (y.get(0) < 0 || y.get(0) > B_HEIGHT || x.get(0) < 0 || x.get(0) > B_WIDTH) {
            inGame = false;
        } else if (!inGame) {
            timer.stop();
        }
    }

    private void checkApple() {
        if (x.get(0) == appleX && y.get(0) == appleY) {
            dotsCount++;
            locateApple();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case VK_LEFT:
                    if (!rightDirection) {
                        leftDirection = true;
                        upDirection = false;
                        downDirection = false;
                    }
                    break;
                case VK_RIGHT:
                    if (!leftDirection) {
                        rightDirection = true;
                        upDirection = false;
                        downDirection = false;
                    }
                    break;
                case VK_UP:
                    if (!upDirection) {
                        downDirection = true;
                        leftDirection = false;
                        rightDirection = false;
                    }
                    break;
                case VK_DOWN:
                    if (!downDirection) {
                        upDirection = true;
                        leftDirection = false;
                        rightDirection = false;
                    }
                    break;
                case VK_R:
                    if (!inGame) {
                        inGame = true;
                        initBoard();
                        initGame();
                    }
            }
        }
    }
}
