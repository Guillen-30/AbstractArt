package AbstractArt;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class DotFactory implements ShapeStrategy {
    @Override
    public Shape createShape(Random rand, Point lastPosition) {
        int x, y;
        if (lastPosition == null) {
            lastPosition = new Point(rand.nextInt(0, 450), rand.nextInt(0, 450)); // Set a default position
        }
        while (true) {
            x = rand.nextInt(450);
            y = rand.nextInt(450);
            if ((x == lastPosition.getX()) || y == lastPosition.getY()) {
                x = rand.nextInt(450);
                y = rand.nextInt(450);
            } else {
                break;
            }
        }
        int size = rand.nextInt(50) + 50;
        return new Ellipse2D.Double(x, y, size, size);
    }
}