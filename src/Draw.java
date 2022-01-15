
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

public class Draw extends JLabel implements MouseListener, MouseMotionListener{
    
    public static int WIDTH = 600, HEIGHT = WIDTH;
    point p;
    
    Points points;
    
    public Draw() {
        points = new Points();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(new Color(255, 255, 255));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        
        points.draw(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        p = points.onMousePressed(e);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(p != null) {
            p.x = e.getPoint().x;
            p.y = e.getPoint().y;
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
    public static void drawString(Graphics g, String s, int x, int y, int size) {
        Font f = new Font("Calibri", Font.PLAIN, size);
        g.setFont(f);
        g.drawString(s, x, y + size);
    }
}