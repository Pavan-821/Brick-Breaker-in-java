
import java.awt.Graphics;
import java.awt.Color;

/**
 * Represents a brick in the Brick Breaker game.
 */
public class Brick {
    private int x, y, width, height;
    private Color color;
    private boolean destroyed;

    /**
     * Constructor to initialize the brick.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param width  Width of the brick
     * @param height Height of the brick
     * @param color  Color of the brick
     */
    public Brick(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.destroyed = false;
    }

    /**
     * Draws the brick on the screen if it's not destroyed.
     *
     * @param g The Graphics object used for drawing
     */
    public void draw(Graphics g) {
        if (!destroyed) {
            g.setColor(color);
            g.fillRect(x, y, width, height); // Draw brick
            g.setColor(Color.BLACK); // Optional: Add brick borders
            g.drawRect(x, y, width, height);
        }
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

    public int getHeight() {
        return height;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
