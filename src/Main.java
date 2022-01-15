
import java.awt.Dimension;
import javax.swing.JFrame;

public class Main {

    public static JFrame jf;
    public static Draw draw;

    public static void createFrame(String title) {
        jf = new JFrame(title);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        draw = new Draw();
        draw.addMouseListener(draw);
        draw.addMouseMotionListener(draw);
        
        draw.setPreferredSize(new Dimension(Draw.WIDTH, Draw.HEIGHT));
        draw.setVisible(true);
        jf.getContentPane().add(draw); // add draw to jf
        jf.pack(); // size the frame

        jf.setLocationRelativeTo(null);
        jf.requestFocus();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        createFrame("Splines");
    }
}
