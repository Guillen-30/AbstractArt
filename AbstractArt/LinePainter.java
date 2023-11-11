package AbstractArt;

public class LinePainter extends ShapePainter {

    public LinePainter(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected ShapeStrategy getShapeStrategy() {
        return new LineFactory();
    }
}