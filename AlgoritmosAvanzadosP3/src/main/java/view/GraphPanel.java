/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import static java.lang.Math.random;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * Panel para pintar los puntos generados.
 */
public class GraphPanel extends JPanel {

    private View vista;
    private int margenLat = 160;
    private int margenVert = 50;

    public GraphPanel(View v) {
        vista = v;
        Border borde = new LineBorder(Color.BLACK, 2);
        setBorder(borde);
        setLayout(null);
        setBounds(margenLat, margenVert,
                vista.getWidth() - (margenLat * 2 + 20),
                vista.getHeight() - (margenVert * 3));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        /*Aqui tendria que ir una llamada al modelo para hacer un get de los
        valores generados para los puntos y luego ya seria ir haciendo llamadas
        a la funcion drawPoint con los valores de cada punto*/
    }

    public void drawPoint(Graphics2D g2d, int x, int y) {
        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 10, 10);
        g2d.setColor(Color.RED);
        g2d.fill(circle);
    }
}
