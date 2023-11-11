package AbstractArt;

public class DotPainter extends ShapePainter {

    public DotPainter(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected ShapeStrategy getShapeStrategy() {
        return new DotFactory();
    }
}