/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Panel lateral derecho de la ventana principal.
 */
public class RightLateralPanel extends JPanel {

    private View vista;
    private int x, y, width, height;
    private JButton startB;
    public JPanel soluciones;
    private TimePanel timePanel;

    public RightLateralPanel(View v) {
        this.vista = v;
        init();
    }

    private void init() {
        this.setLayout(null);

        this.x = this.vista.getWidth() + 10 - this.vista.MARGENLAT;
        this.y = this.vista.MARGENVER;
        this.width = this.vista.MARGENLAT - 20;
        this.height = this.vista.getHeight() - this.vista.MARGENVER - 40;

        this.setBounds(x, y, width, height);
        this.setBackground(Color.red);
        this.setBorder(new LineBorder(Color.BLACK, 2));

        // TIPO DE DISTRIBUCIÓN
        JLabel timeLabel = new JLabel("Tiempo de Ejecución (ns)");
        timeLabel.setLayout(null);
        timeLabel.setBounds(10, 10, width - 10, 30);
        this.add(timeLabel);

        timePanel = new TimePanel(10, 50, width - 20, 30);
        this.add(timePanel);

        // START BUTTON
        this.startB = new JButton("Start");
        this.startB.setLayout(null);
        this.startB.setBounds(10, height - 100, width - 20, 90);
        this.add(startB);

        // SOLUCIONES
        JLabel resultLabel = new JLabel("Distancias solución/es");
        resultLabel.setLayout(null);
        resultLabel.setBounds(10, this.getHeight() / 4 - 40, width - 10, 30);
        this.add(resultLabel);

        soluciones = new JPanel();
        soluciones.setLayout(null);
        soluciones.setBounds(10, this.getHeight() / 3 - 50, width - 20, 252);
        this.add(soluciones);

        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                vista.startClicked();
            }

        });

        this.setVisible(true);
    }

    private class TimePanel extends JPanel {

        private JLabel timeLabel;

        private TimePanel(int x, int y, int width, int height) {
            this.setBounds(x, y, width, height);

            this.timeLabel = new JLabel("");
            this.add(timeLabel);
        }

        public String getTime() {
            return this.timeLabel.getText();
        }

        public void setTime(long nanoseconds) {
            this.timeLabel.setText(String.valueOf(nanoseconds));
        }
    }

    public void setTime(long nanoseconds) {
        this.timePanel.setTime(nanoseconds);
    }

}
