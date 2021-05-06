package engine.geometry;

import java.awt.*;

public class Line {

    private double a;
    private double b;

    private Point p1;
    private Point p2;

    public Line(int x1, int y1, int x2, int y2) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
        a = (y2 - y1) / (x2 - x1);
        b = y1 - x1 * a;
    }

    public boolean intersect(Line l) {
        try {
            double x = (l.b - b) / (a - l.a);
            if(l.b == b && a == l.a) {
                return (p2.x >= l.p1.x && p1.x < l.p2.x) ||
                        (l.p2.x >= p1.x && l.p1.x < p2.x);
            }
            return (x <= p2.x && x <= l.p2.x) && (x >= p1.x && x >= l.p1.x);
        } catch(ArithmeticException e) {
            return b == l.b;
        }
    }

    public void draw(Graphics2D g) {
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
//        g.drawLine();
    }

}
