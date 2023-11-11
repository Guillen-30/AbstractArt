package AbstractArt;

import java.awt.*;
import java.util.Random;

public interface ShapeStrategy {
    Shape createShape(Random rand, Point lastPosition);
}