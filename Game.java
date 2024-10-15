
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The main game class that handles game initialization, game loop, rendering, and user input.
 */
public class Game extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running = false;
    private boolean leftPressed, rightPressed, paused;
    private int score = 0;
    private int lives = 3;

    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;

    /**
     * Constructor to set up the game panel.
     */
    public Game() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        initializeGame();
    }

    /**
     * Initializes game objects like paddle, ball, and bricks.
     */
    private void initializeGame() {
        paddle = new Paddle(350, 550, 100, 10, 5, Color.WHITE);
        ball = new Ball(390, 540, 20, 3, -3, Color.YELLOW);
        bricks = new ArrayList<>();

        // Create a grid of bricks
        int rows = 5;
        int cols = 10;
        int brickWidth = 60;
        int brickHeight = 20;
        int padding = 10;
        int offsetX = 70;
        int offsetY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                bricks.add(new Brick(offsetX + col * (brickWidth + padding),
                                     offsetY + row * (brickHeight + padding),
                                     brickWidth, brickHeight, Color.RED));
            }
        }

        // Initialize sound managers here if implementing sounds
    }

    /**
     * Starts the game thread.
     */
    public void start() {
        if (gameThread == null || !running) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    /**
     * The main game loop.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            if (!paused) {
                updateGame();
            }
            repaint();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game state: moves objects, checks collisions, updates score and lives.
     */
    private void updateGame() {
        // Update paddle position
        if (leftPressed) {
            paddle.moveLeft();
        }
        if (rightPressed) {
            paddle.moveRight(getWidth());
        }

        // Move the ball
        ball.move();

        // Check wall collisions
        if (ball.getX() <= 0 || ball.getX() + ball.getDiameter() >= getWidth()) {
            ball.bounceX();
            // Play wall hit sound if implemented
        }
        if (ball.getY() <= 0) {
            ball.bounceY();
            // Play wall hit sound if implemented
        }

        // Check paddle collision
        if (ball.checkCollision(paddle)) {
            // Adjust ball direction based on where it hits the paddle
            int paddleCenter = paddle.getX() + paddle.getWidth() / 2;
            int ballCenter = ball.getX() + ball.getDiameter() / 2;
            int relativeIntersect = ballCenter - paddleCenter;
            ball.setXVelocity(relativeIntersect / 10); // Adjust X velocity
            ball.bounceY();
            // Play paddle hit sound if implemented
        }

        // Check brick collisions
        for (Brick brick : bricks) {
            if (!brick.isDestroyed() && ball.checkCollision(brick)) {
                brick.setDestroyed(true);
                ball.bounceY();
                score += 10;
                // Play brick break sound if implemented
                break; // Only one brick can be hit at a time
            }
        }

        // Check for game over
        if (ball.getY() > getHeight()) {
            lives--;
            if (lives > 0) {
                resetBallAndPaddle();
            } else {
                gameOver();
            }
        }

        // Check for victory
        boolean allDestroyed = true;
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                allDestroyed = false;
                break;
            }
        }
        if (allDestroyed) {
            victory();
        }
    }

    /**
     * Resets the ball and paddle positions after losing a life.
     */
    private void resetBallAndPaddle() {
        paddle.setX(350);
        ball.setX(390);
        ball.setY(540);
        ball.setXVelocity(3);
        ball.setYVelocity(-3);
        paused = true;

        // Add a short delay before resuming
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Handles game over state.
     */
    private void gameOver() {
        running = false;
        // Display game over message
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        System.exit(0);
    }

    /**
     * Handles victory state.
     */
    private void victory() {
        running = false;
        // Display victory message
        JOptionPane.showMessageDialog(this, "Congratulations! You won! Your score: " + score);
        System.exit(0);
    }

    /**
     * Renders all game objects and UI elements.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddle
        paddle.draw(g);

        // Draw ball
        ball.draw(g);

        // Draw bricks
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                brick.draw(g);
            }
        }

        // Draw score and lives
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Lives: " + lives, getWidth() - 100, 20);
    }

    /**
     * Handles key press events.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_P:
                paused = !paused;
                break;
            case KeyEvent.VK_R:
                resetGame();
                break;
            // Add more controls as needed
            default:
                break;
        }
    }

    /**
     * Handles key release events.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        // Reset game state
        score = 0;
        lives = 3;
        bricks.clear();
        initializeGame();
        paused = false;
        if (!running) {
            start();
        }
    }

    /**
     * The main method to launch the game.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        Game game = new Game();
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
        game.start();
    }
}
