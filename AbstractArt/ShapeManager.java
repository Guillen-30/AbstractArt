package AbstractArt;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ShapeManager extends Thread {

    private List<ShapePainter> painters;
    private Iterator<ShapePainter> iterator;
    private DrawingPanel drawingPanel;

    public ShapeManager(List<ShapePainter> painters, DrawingPanel drawingPanel) {
        this.painters = painters;
        this.iterator = painters.iterator();
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void run() {
        while (drawingPanel.getShapes().size() < 30) {
            if (!iterator.hasNext()) {
                iterator = painters.iterator();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ShapePainter painter = iterator.next();
            painter.addRandomShape(new Random());
        }
    }
}