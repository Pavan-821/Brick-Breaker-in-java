
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * Represents the ball in the Brick Breaker game.
 */
public class Ball {
    private int x, y, diameter;
    private int xVelocity, yVelocity;
    private Color color;

    /**
     * Constructor to initialize the ball.
     *
     * @param x         Initial x-coordinate
     * @param y         Initial y-coordinate
     * @param diameter  Diameter of the ball
     * @param xVelocity Initial velocity in x-direction
     * @param yVelocity Initial velocity in y-direction
     * @param color     Color of the ball
     */
    public Ball(int x, int y, int diameter, int xVelocity, int yVelocity, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.color = color;
    }

    /**
     * Moves the ball based on its current velocity.
     */
    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    /**
     * Reverses the ball's x-direction velocity.
     */
    public void bounceX() {
        xVelocity = -xVelocity;
    }

    /**
     * Reverses the ball's y-direction velocity.
     */
    public void bounceY() {
        yVelocity = -yVelocity;
    }

    /**
     * Checks collision with the paddle.
     *
     * @param paddle The paddle object to check collision with
     * @return true if collision occurs, false otherwise
     */
    public boolean checkCollision(Paddle paddle) {
        Rectangle ballRect = new Rectangle(x, y, diameter, diameter);
        Rectangle paddleRect = new Rectangle(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
        return ballRect.intersects(paddleRect);
    }

    /**
     * Checks collision with a brick.
     *
     * @param brick The brick object to check collision with
     * @return true if collision occurs, false otherwise
     */
    public boolean checkCollision(Brick brick) {
        Rectangle ballRect = new Rectangle(x, y, diameter, diameter);
        Rectangle brickRect = new Rectangle(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
        return ballRect.intersects(brickRect);
    }

    /**
     * Draws the ball on the screen.
     *
     * @param g The Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }

    // Getters and setters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
