

import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents the player's paddle in the Brick Breaker game.
 */
public class Paddle {
    private int x, y, width, height, speed;
    private Color color;

    /**
     * Constructor to initialize the paddle.
     *
     * @param x      Initial x-coordinate
     * @param y      Initial y-coordinate
     * @param width  Width of the paddle
     * @param height Height of the paddle
     * @param speed  Movement speed
     * @param color  Color of the paddle
     */
    public Paddle(int x, int y, int width, int height, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    /**
     * Moves the paddle to the left, ensuring it doesn't go out of bounds.
     */
    public void moveLeft() {
        x -= speed;
        if (x < 0) {
            x = 0;
        }
    }

    /**
     * Moves the paddle to the right, ensuring it doesn't go out of bounds.
     *
     * @param panelWidth The width of the game panel to prevent overflow
     */
    public void moveRight(int panelWidth) {
        x += speed;
        if (x + width > panelWidth) {
            x = panelWidth - width;
        }
    }

    /**
     * Draws the paddle on the screen.
     *
     * @param g The Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    // Getters and setters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getHeight() {
        // TODO Auto-generated method stub
       return height;
    }
}
