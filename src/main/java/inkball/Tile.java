package inkball;

import processing.core.PVector;
import processing.core.PImage;

public class Tile {
    protected PVector position;
    protected int color;
    protected PImage image;

    // Constructor for Tile
    public Tile(float x, float y, int color, PImage image) {
        this.position = new PVector(x, y);
        this.color = color;
        this.image = image;
    }

    // Display the tile
    public void display(App app) {
        if (image != null) {
            app.image(image, position.x, position.y, App.CELLSIZE, App.CELLSIZE);
        } else {
            System.out.println("Tile image is null, drawing fallback rectangle");
            app.fill(200); // Default light gray
            app.rect(position.x, position.y, App.CELLSIZE, App.CELLSIZE);
        }
    }

    // Get the position of the tile
    public PVector getPosition() {
        return position.copy();
    }

    // Get the color of the tile
    public int getColor() {
        return color;
    }

    // Check if the ball collides with the tile
    public boolean collidesWith(Ball ball) {
        // Find the closest point on the tile to the ball's center
        float closestX = Math.max(position.x, Math.min(ball.getX(), position.x + App.CELLSIZE));
        float closestY = Math.max(position.y, Math.min(ball.getY(), position.y + App.CELLSIZE));
        
        // Calculate the distance between the ball's center and this closest point
        float distanceX = ball.getX() - closestX;
        float distanceY = ball.getY() - closestY;
        
        // Check if the distance is less than the ball's radius (collision check)
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (ball.getRadius() * ball.getRadius());
    }

    // Get the normal vector of the tile based on where the ball hits
    public PVector getNormal(Ball ball) {
        // Find the center of the tile
        PVector center = new PVector(position.x + App.CELLSIZE / 2, position.y + App.CELLSIZE / 2);

        // Subtract the tile's center from the ball's position to get the normal direction
        PVector normal = PVector.sub(ball.getPosition(), center);
        normal.normalize(); // Normalize the vector so it's unit length (just direction, no magnitude)

        return normal;
    }

    // Default method for calculating score change (could be overridden by subclasses like Wall, Hole)
    public int calculateScoreChange(Ball ball, App app) {
        return 0; // No score change for basic tiles
    }
}
