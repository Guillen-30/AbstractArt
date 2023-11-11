package AbstractArt;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;



interface ShapeSubject {
    void addObserver(ShapeObserver observer);
    void removeObserver(ShapeObserver observer);
    void notifyObservers(Shape shape, Color color);
}

public class main extends JFrame {

    private DrawingPanel drawingPanel;
    private ShapeManager shapeManager;

    public main() {
        setTitle("Shape Drawer App");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        List<ShapePainter> painters = new ArrayList<>();
        painters.add(new DotPainter(drawingPanel));
        painters.add(new LinePainter(drawingPanel));
        painters.add(new PolygonPainter(drawingPanel));

        shapeManager = new ShapeManager(painters);
        shapeManager.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            main app = new main();
            app.setVisible(true);
        });
    }

    private class DrawingPanel extends JPanel implements ShapeSubject {

        private final List<ColoredShape> shapes;
        private final List<ShapeObserver> observers;

        public DrawingPanel() {
            shapes = new ArrayList<>();
            observers = new ArrayList<>();
        }

        @Override
        public void addObserver(ShapeObserver observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(ShapeObserver observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers(Shape shape, Color color) {
            for (ShapeObserver observer : observers) {
                observer.update(shape, color);
            }
        }

        public void addShape(Shape shape, Color color) {
            shapes.add(new ColoredShape(shape, color));
            notifyObservers(shape, color); // Notify observers when a new shape is added
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            for (ColoredShape coloredShape : shapes) {
                g2d.setColor(coloredShape.getColor());
                g2d.fill(coloredShape.getShape());
            }
        }
    }

    private static class ColoredShape {
        private final Shape shape;
        private final Color color;

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

    private interface ShapeStrategy {
        Shape createShape(Random rand, Point lastPosition);
    }

    private abstract class ShapePainter extends Thread {

        protected final DrawingPanel drawingPanel;
        protected Color lastColor;
        protected Point lastPosition;

        public ShapePainter(DrawingPanel drawingPanel) {
            this.drawingPanel = drawingPanel;
        }

        protected abstract ShapeStrategy getShapeStrategy();

        @Override
        public void run() {
            Random rand = new Random();

            while (drawingPanel.shapes.size() < 30) {
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

    private class DotPainter extends ShapePainter {

        public DotPainter(DrawingPanel drawingPanel) {
            super(drawingPanel);
        }

        @Override
        protected ShapeStrategy getShapeStrategy() {
            return new DotFactory();
        }
    }

    private class LinePainter extends ShapePainter {

        public LinePainter(DrawingPanel drawingPanel) {
            super(drawingPanel);
        }

        @Override
        protected ShapeStrategy getShapeStrategy() {
            return new LineFactory();
        }
    }

    private class PolygonPainter extends ShapePainter {

        public PolygonPainter(DrawingPanel drawingPanel) {
            super(drawingPanel);
        }

        @Override
        protected ShapeStrategy getShapeStrategy() {
            return new PolygonFactory();
        }
    }

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

    public class PolygonFactory implements ShapeStrategy {
        @Override
        public Shape createShape(Random rand, Point lastPosition) {
            int size = rand.nextInt(50) + 50;
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

    private class ShapeManager extends Thread {

        private final List<ShapePainter> painters;
        private Iterator<ShapePainter> iterator;

        public ShapeManager(List<ShapePainter> painters) {
            this.painters = painters;
            this.iterator = painters.iterator();
        }

        @Override
        public void run() {
            while (drawingPanel.shapes.size() < 30) {
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
}
