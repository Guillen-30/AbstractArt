package AbstractArt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel implements ShapeSubject {

    private List<ColoredShape> shapes;
    private List<ShapeObserver> observers;

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

    public List<ColoredShape> getShapes(){
        return this.shapes;
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