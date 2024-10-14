package inkball;

import processing.core.PImage;

public class Spawner {
    private float x, y;  // Position of the spawner
    private PImage image;  // Image for the spawner

    // Constructor that accepts x, y coordinates and the image
    public Spawner(float x, float y, PImage image) {
        this.x = x;
        this.y = y;
        this.image = image;  // Set the spawner image
    }

    // Getter for the x coordinate
    public float getX() {
        return x;
    }

    // Getter for the y coordinate
    public float getY() {
        return y;
    }

    // Method to display the spawner image
    public void display(App app) {
        if (image != null) {
            app.image(image, x, y, App.CELLSIZE, App.CELLSIZE);  // Display the image at the specified position
        } else {
            // If image is null, display a default rectangle or visual representation
            app.fill(150);  // Set a color
            app.rect(x, y, App.CELLSIZE, App.CELLSIZE);  // Draw a rectangle if no image is available
        }
    }
}
