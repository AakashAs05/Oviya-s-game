package inkball;

import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONObject;
import processing.core.PApplet;

public class Hole extends Tile {
    public static final float ATTRACTION_RADIUS = 32;  // Change from private to public
    private static final float CAPTURE_RADIUS = 16;
    
    public Hole(float x, float y, int color, App app) {
        super(x, y, color, app.getHoleImage(color));
        this.position = new PVector(x + App.CELLSIZE, y + App.CELLSIZE); // Center of 2x2 tile
    }

    public boolean captures(Ball ball) {
        float distance = PVector.dist(ball.getPosition(), position);
        return distance < CAPTURE_RADIUS;
    }

    public void applyAttraction(Ball ball, App app) {
        PVector attractionVector = PVector.sub(position, ball.getPosition());
        attractionVector.normalize();
        attractionVector.mult(0.5f); // Adjust attraction strength
        ball.applyForce(attractionVector);

        // Shrink the ball as it approaches the hole
        float distance = PVector.dist(ball.getPosition(), position);
        float scaleFactor = app.constrain(1 - (distance / ATTRACTION_RADIUS), 0.1f, 1.0f); 
        ball.setSize(App.CELLSIZE * scaleFactor);  // Shrink the ball
    }

    public boolean attracts(Ball ball) {
        float distance = PVector.dist(ball.getPosition(), position);
        return distance < ATTRACTION_RADIUS;
    }

    @Override
    public void display(App app) {
        PImage holeImage = app.getHoleImage(getColor());
        if (holeImage != null) {
            app.image(holeImage, position.x - App.CELLSIZE, position.y - App.CELLSIZE, App.CELLSIZE * 2, App.CELLSIZE * 2);
        } else {
            // Fallback rendering if image is not available
            app.noStroke();
            app.fill(200); // Light grey color
            app.ellipse(position.x, position.y, App.CELLSIZE * 2, App.CELLSIZE * 2);
        }
    }

    @Override
    public int calculateScoreChange(Ball ball, App app) {
    JSONObject config = app.getConfig();
    boolean success = ball.getColor() == getColor() || ball.getColor() == 0 || getColor() == 0; // Grey is wildcard

    if (success) {
        // Correct capture - increase score
        int scoreIncrease = config.getJSONObject("score_increase_from_hole_capture").getInt(String.valueOf(ball.getColor()));
        float modifier = config.getFloat("score_increase_from_hole_capture_modifier", 1.0f);
        return (int) (scoreIncrease * modifier);
    } else {
        // Wrong hole - decrease score
        int scoreDecrease = config.getJSONObject("score_decrease_from_wrong_hole").getInt(String.valueOf(ball.getColor()));
        float modifier = config.getFloat("score_decrease_from_wrong_hole_modifier", 1.0f);
        return (int) (-scoreDecrease * modifier);  // Negative score change
    }
}

}
