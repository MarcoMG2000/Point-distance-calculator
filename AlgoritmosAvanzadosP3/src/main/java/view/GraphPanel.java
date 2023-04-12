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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import model.Punto;

/**
 * Panel para pintar los puntos generados.
 */
public class GraphPanel extends JPanel {

    private View vista;

    public GraphPanel(View v, int width, int height) {
        vista = v;
        Border borde = new LineBorder(Color.BLACK, 2);
        setBorder(borde);
        setLayout(null);
        setBounds(vista.MARGENLAT, vista.MARGENVER,
                width, height);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        if(vista.getModelo().getPuntos() == null){
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        
        Punto[]   puntos     = vista.getModelo().getPuntos();
        Punto[][] soluciones = vista.getModelo().getSoluciones();
        
        /*Aqui tendria que ir una llamada al modelo para hacer un get de los
        valores generados para los puntos y luego ya seria ir haciendo llamadas
        a la funcion drawPoint con los valores de cada punto*/
        
        g2d.setColor(Color.RED);
        for (Punto p: puntos){
            //g2d.fill(new Ellipse2D.Double(p.getX(), p.getY(), 2, 2));
            g2d.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
        }
        
        if(soluciones != null){
            g2d.setColor(Color.BLUE);
            for (int i = 0; i < soluciones.length; i++){
                Punto p1 = soluciones[i][0];
                Punto p2 = soluciones[i][1];

                int x1 = (int) p1.getX();
                int y1 = (int) p1.getY();
                int x2 = (int) p2.getX();
                int y2 = (int) p2.getY();

                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        
        
        
        
    }
}
