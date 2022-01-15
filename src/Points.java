
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class point {

    static int SIZE = 20; // SIZE of the red dot
    int x, y;

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Points {

    static int ALPHAVALUE = 32;
    static float ITSTEPS = 1f / 100f; // 100 iteration steps
    ArrayList<point> points;

    public Points() {
        points = new ArrayList<>();
        points.add(new point(100, 100));
        points.add(new point(200, 150));
        points.add(new point(200, 500));
        points.add(new point(250, 400));
        points.add(new point(300, 300));
        points.add(new point(300, 400));
        points.add(new point(500, 200));
        points.add(new point(500, 500));
    }

    public point GetSplinePoint(float t, point p0, point p1, point p2, point p3) { // returns the calculated point
        int b0, b1, b2, b3;
        b1 = (int) t + 1;
        b2 = b1 + 1;
        b3 = b2 + 1;
        b0 = b1 - 1;

        t = t - (int) t;

        float tt = t * t;
        float ttt = tt * t;

        float q1 = -ttt + 2.0f * tt - t;
        float q2 = 3.0f * ttt - 5.0f * tt + 2.0f;
        float q3 = -3.0f * ttt + 4.0f * tt + t;
        float q4 = ttt - tt;

        float tx = 0.5f * (p0.x * q1 + p1.x * q2 + p2.x * q3 + p3.x * q4);
        float ty = 0.5f * (p0.y * q1 + p1.y * q2 + p2.y * q3 + p3.y * q4);

        return new point((int) tx, (int) ty);
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < points.size(); i++) { // iterate through the array

            // draw straight line
            if (i < points.size() - 1) {
                g2.setColor(new Color(255, 0, 0, ALPHAVALUE));
                g2.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
            }

            // draw Point
            g2.setColor(new Color(255,0,0,ALPHAVALUE));
            g2.fillOval(points.get(i).x - point.SIZE / 4, points.get(i).y - point.SIZE / 4, point.SIZE / 2, point.SIZE / 2);

            // draw Border around Point
            g2.setColor(new Color(0,0,0,ALPHAVALUE));
            g2.drawRect(points.get(i).x - point.SIZE / 2, points.get(i).y - point.SIZE / 2, point.SIZE, point.SIZE);

            // draw Number of Point
            g2.setColor(new Color(0, 0, 0, ALPHAVALUE));
            Draw.drawString(g2, String.valueOf(i), points.get(i).x, points.get(i).y, 20);
        }

        if (points.size() > 2) { // if more than 2 points exist
            for (int i = -1; i < points.size() - 2; i++) {
                for (float t = 0; t <= 1; t += ITSTEPS) {

                    g2.setColor(new Color(0, 0, 255));
                    point p;
                    if (i == -1) { // if it's the first point
                        p = GetSplinePoint(t, points.get(i + 1), points.get(i + 1), points.get(i + 2), points.get(i + 3));
                    } else if (i == points.size() - 3) { // if it's the last point
                        p = GetSplinePoint(t, points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 2));
                    } else { // any other point
                        p = GetSplinePoint(t, points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3));
                    }
                    g2.fillRect(p.x, p.y, 2, 2);
                }
            }
        }
    }

    public point onMousePressed(MouseEvent e) {

        Point pp = e.getPoint();
        System.out.println("X: " + pp.getX() + " Y: " + pp.getY());

        switch (e.getButton()) {
            case 1: { // Left Click to drag a Point
                for (point p : points) {
                    int x = p.x - point.SIZE / 2;
                    int y = p.y - point.SIZE / 2;
                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
                        return p;
                    }
                }
                break;
            }

            case 2: { // Middle Click to add a Point 
                points.add(new point(pp.x, pp.y));
                break;
            }

            case 3: { // Right Click to remove a Point
                point pToRemove = null;
                for (point p : points) {
                    int x = p.x - point.SIZE / 2;
                    int y = p.y - point.SIZE / 2;
                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
                        pToRemove = p;
                    }
                }
                if (pToRemove != null) {
                    points.remove(pToRemove);
                }

                break;
            }
        }
        return null;
    }
}
