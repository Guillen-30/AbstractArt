package AbstractArt;

import java.awt.*;
import java.util.Random;

public class PolygonFactory implements ShapeStrategy {
    @Override
    public Shape createShape(Random rand, Point lastPosition) {
        int sides = 3 + rand.nextInt(6); // 3 to 8 sides
        int[] xPoints = new int[sides];
        int[] yPoints = new int[sides];
        if (lastPosition == null) {
            lastPosition = new Point(rand.nextInt(0, 450), rand.nextInt(0, 450)); // Set a default position
        }
        for (int i = 0; i < sides; i++) {
            while (true) {
                xPoints[i] = rand.nextInt(450);
                yPoints[i] = rand.nextInt(450);
                if (((xPoints[i] == lastPosition.getX()) || (yPoints[i] == lastPosition.getY()))) {
                    xPoints[i] = rand.nextInt(450);
                    yPoints[i] = rand.nextInt(450);
                } else {
                    break;
                }
            }
        }
        return new Polygon(xPoints, yPoints, sides);
    }
}