package AbstractArt;

public class PolygonPainter extends ShapePainter {

    public PolygonPainter(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    protected ShapeStrategy getShapeStrategy() {
        return new PolygonFactory();
    }
}