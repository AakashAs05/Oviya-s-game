package inkball;

import processing.core.PVector;
import processing.core.PImage;

public class Wall extends Tile {

    public Wall(float x, float y, int color, PImage image) {
        super(x, y, color, image);
    }

    @Override
    public boolean collidesWith(Ball ball) {
        float closestX = Math.max(position.x, Math.min(ball.getX(), position.x + App.CELLSIZE));
        float closestY = Math.max(position.y, Math.min(ball.getY(), position.y + App.CELLSIZE));
        
        float distanceX = ball.getX() - closestX;
        float distanceY = ball.getY() - closestY;
        
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (ball.getRadius() * ball.getRadius());
    }

    @Override
    public PVector getNormal(Ball ball) {
        PVector normal;
        float deltaX = ball.getX() - (position.x + App.CELLSIZE / 2);
        float deltaY = ball.getY() - (position.y + App.CELLSIZE / 2);

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            normal = new PVector(1, 0); // Horizontal surface
        } else {
            normal = new PVector(0, 1); // Vertical surface
        }
        return normal;
    }

    @Override
    public void display(App app) {
        app.image(image, position.x, position.y, App.CELLSIZE, App.CELLSIZE);
    }
}
