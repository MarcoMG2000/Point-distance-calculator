/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url 
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Ventana de diálogo que notifica un mensaje a forma de pop-up.
 */
public class Notification extends JDialog {

    private Timer timer; // Tiempo que se muestra la notificación.

    public Notification(String message) {
        setUndecorated(true);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(0, 0, 0, 170));
        getContentPane().setPreferredSize(new Dimension(300, 80));
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        pack();

        Point center = new Point(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - getWidth() / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - getHeight() / 2);
        setLocation(center);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setBounds(0, 0, getWidth(), getHeight());
        getContentPane().add(label);
        timer = new Timer(1000, (ActionEvent e) -> {
            setVisible(false);
            dispose();
            timer.stop();
        });
        timer.start();
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
    }

}
