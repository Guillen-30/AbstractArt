package AbstractArt;

import java.awt.*;

interface ShapeSubject {
    void addObserver(ShapeObserver observer);
    void removeObserver(ShapeObserver observer);
    void notifyObservers(Shape shape, Color color);
}
