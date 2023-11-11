package AbstractArt;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class LineFactory implements ShapeStrategy {
    @Override
    public Shape createShape(Random rand, Point lastPosition) {
        int x, y, x2, y2, size;
        if (lastPosition == null) {
            lastPosition = new Point(rand.nextInt(0, 450), rand.nextInt(0, 450)); // Set a default position
        }
        size = rand.nextInt(1, 50);
        while (true) {
            x = rand.nextInt(450);
            y = rand.nextInt(450);
            x2 = (rand.nextInt(x) + size);
            y2 = (rand.nextInt(y) + size);

            if (((x == lastPosition.getX()) || (y == lastPosition.getY()) || (x2 == lastPosition.getX()) || (y2 == lastPosition.getY()))) {
                x = rand.nextInt(450);
                y = rand.nextInt(450);
                x2 = (rand.nextInt(x) + size);
                y2 = (rand.nextInt(y) + size);
            } else {
                break;
            }
        }
        return new Rectangle2D.Double(x, y, x2, y2);
    }
}