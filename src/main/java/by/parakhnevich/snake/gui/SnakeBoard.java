package by.parakhnevich.snake.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private int[] x = new int[900];
    private int[] y = new int[900];
    private int dotsCount = 3;
    private int appleX;
    private int appleY;
    private Image head;
    private Image body;
    private Image apple;
    private Timer timer;



    public SnakeBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

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
                    g.drawImage(head, x[i], y[i], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(font);
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("Game over", (B_WIDTH - 9) / 2, B_HEIGHT / 2);
    }


    private void initGame() {
        for (int i = 0; i < dotsCount; i++) {
            x[i] = 50 - i * 10;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(140, this);
        timer.start();
    }

    private void locateApple() {
        appleX =  (10 * (int) (Math.random() * 29));
        appleY =  (10 * (int) (Math.random() * 29));
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
        for (int i = dotsCount; i > 0; --i) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) {
            x[0] -= 10;
        } else if (rightDirection) {
            x[0] += 10;
        } else if (upDirection) {
            y[0] += 10;
        } else {
            y[0] -= 10;
        }
    }

    private void checkCollision() {
        for (int i = dotsCount; i > 0; --i) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        if (y[0] <= 0 || y[0] >= B_HEIGHT || x[0] <= 0 || x[0] >= B_WIDTH) {
            inGame = false;
        } else if (!inGame) {
            timer.stop();
        }
    }

    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dotsCount++;
            locateApple();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            setFalseToAllDirections();
            switch (key) {
                case VK_LEFT :
                    leftDirection = true;
                    break;
                case VK_RIGHT:
                    rightDirection = true;
                    break;
                case VK_UP:
                    downDirection = true;
                    break;
                case VK_DOWN:
                    upDirection = true;
                    break;
            }
        }

        private void setFalseToAllDirections() {
            leftDirection = false;
            rightDirection = false;
            upDirection = false;
            downDirection = false;
        }
    }
}
