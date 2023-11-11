package AbstractArt;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class AbstractArt extends JFrame {

    private DrawingPanel drawingPanel;
    private ShapeManager shapeManager;

    public AbstractArt() {
        setTitle("Shape Drawer App");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        List<ShapePainter> painters = new ArrayList<>();
        painters.add(new DotPainter(this.drawingPanel));
        painters.add(new LinePainter(this.drawingPanel));
        painters.add(new PolygonPainter(this.drawingPanel));

        shapeManager = new ShapeManager(painters, this.drawingPanel);
        shapeManager.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AbstractArt app = new AbstractArt();
            app.setVisible(true);
        });
    }
}
