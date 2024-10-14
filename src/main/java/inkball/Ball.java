package inkball;

import processing.core.PVector;
import processing.core.PImage;

public class Ball {
    private PVector position;
    private PVector velocity;
    private float size;
    private int color;
    private boolean removed;
    private float radius;
    private static final float MAX_SPEED = 5.0f;

    public Ball(float x, float y, int color) {
        this.position = new PVector(x, y);
        this.velocity = new PVector(App.random.nextBoolean() ? 2 : -2, App.random.nextBoolean() ? 2 : -2);
        this.size = App.CELLSIZE;
        this.radius = size / 2; // Initial radius is half of the ball's size
        this.color = color;
        this.removed = false;
    }

    // Ball movement
    public void move() {
        if (!removed) {
            position.add(velocity);
        }
    }

    // Apply a force to the ball (used for attraction)
    public void applyForce(PVector force) {
        velocity.add(force);
        // Limit the maximum speed
        if (velocity.mag() > MAX_SPEED) {
            velocity.normalize().mult(MAX_SPEED);
        }
    }

    // Reflect the ball when it hits an object
    public void reflect(PVector normal) {
        PVector n = normal.copy().normalize();
        velocity.sub(PVector.mult(n, 2 * PVector.dot(velocity, n)));
    }

    public void setVelocity(PVector newVelocity) {
        this.velocity = newVelocity.copy();
    }
    
    // Reverse the X direction
    public void reverseX() {
        velocity.x *= -1;
    }

    // Reverse the Y direction
    public void reverseY() {
        velocity.y *= -1;
    }

    // Get the ball's position
    public PVector getPosition() {
        return position.copy();
    }

    // Get the ball's velocity
    public PVector getVelocity() {
        return velocity.copy();
    }

    // Set the color of the ball (used when hitting a wall)
    public void setColor(int color) {
        this.color = color;
    }

    // Get the color of the ball
    public int getColor() {
        return color;
    }

    // Set the ball's size (used for shrinking when it enters the hole)
    public void setSize(float newSize) {
        this.size = newSize;
        this.radius = size / 2; // Adjust the radius according to the new size
    }

    // Get the ball's radius
    public float getRadius() {
        return radius;
    }

    // Display the ball on the screen
    public void display(App app) {
        if (!removed) {
            app.image(app.getBallImage(color), position.x - radius, position.y - radius, size, size);
        }
    }

    // Mark the ball as removed (when it enters a hole)
    public void remove() {
        this.removed = true;
    }

    // Check if the ball has been removed
    public boolean isRemoved() {
        return removed;
    }

    // Update the ball's attraction factor when it's near a hole
    public void updateAttractionFactor(Hole hole) {
        float distance = PVector.dist(position, hole.getPosition());
        float attractionFactor = Math.max(0, 1 - (distance / Hole.ATTRACTION_RADIUS));
        applyForce(PVector.mult(hole.getPosition().copy().sub(position), attractionFactor));
    }

    // Get the X position of the ball
    public float getX() {
        return position.x;
    }

    // Get the Y position of the ball
    public float getY() {
        return position.y;
    }
}
