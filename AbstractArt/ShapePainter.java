package AbstractArt;

import java.awt.*;
import java.util.Random;

abstract class ShapePainter extends Thread {

    protected DrawingPanel drawingPanel;
    protected Color lastColor;
    protected Point lastPosition;

    public ShapePainter(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    protected abstract ShapeStrategy getShapeStrategy();

    @Override
    public void run() {
        Random rand = new Random();

        while (drawingPanel.getShapes().size() < 30) {
            addRandomShape(rand);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void addRandomShape(Random rand) {
        ShapeStrategy strategy = getShapeStrategy();
        Shape shape = strategy.createShape(rand, lastPosition);

        lastColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 125);
        lastPosition = new Point((int) shape.getBounds2D().getCenterX(), (int) shape.getBounds2D().getCenterY());

        drawingPanel.addShape(shape, lastColor);
    }
}