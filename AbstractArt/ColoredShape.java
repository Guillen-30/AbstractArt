package AbstractArt;

import java.awt.*;

public class ColoredShape {
    private Shape shape;
    private Color color;

    public ColoredShape(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}