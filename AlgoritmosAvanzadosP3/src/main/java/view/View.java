/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package view;

import model.Model;
import controller.Controller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.LineBorder;
import model.Distribution;
import model.Method;
import model.Punto;

/**
 * Vista de la aplicación, aquí interactuaremos con la aplicación y
 * visualizaremos todos los datos y los resultados de las operaciónes.
 */
public class View extends JFrame {

    // PUNTEROS DEL PATRÓN MVC
    private Controller controlador;
    private Model modelo;

    // CONSTANTES DE LA VISTA
    protected final int MARGENLAT = 200;
    protected final int MARGENVER = 150;

    // VARIABLES DEL JPanel
    private int GraphWidth;
    private int GraphHeight;

    private LeftLateralPanel leftPanel;
    private RightLateralPanel rightPanel;
    private GraphPanel graphPanel;

    // CONSTRUCTORS
    public View() {
    }

    public View(Controller controlador, Model modelo) {
        this.controlador = controlador;
        this.modelo = modelo;
    }

    // CLASS METHODS
    /**
     * Clase que inicializa la ventana principal y añade todos los elementos al
     * JFrame.
     */
    public void mostrar() {
        this.setTitle("Práctica 3 - Algoritmos Avanzados");
        this.setLayout(null);

        this.GraphWidth = 650;
        this.GraphHeight = 450;

        // DIMENSION DEL JFRAME
        setSize(this.GraphWidth + this.MARGENLAT * 2, this.GraphHeight + this.MARGENVER + 40);

        // POSICIONAR EL JFRAME EN EL CENTRO DE LA PANTALLA
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        // TITTLE PANEL
        JPanel title = new JPanel();
        title.setBounds(10, 10, getWidth() - 20, this.MARGENVER - 20);
        title.setBackground(Color.WHITE);
        title.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel titleLabel = new JLabel("Titulo");
        title.add(titleLabel);

        this.add(title);

        // GRAPH PANEL
        graphPanel = new GraphPanel(this, GraphWidth, GraphHeight);
        this.add(graphPanel);

        // PANELES LATERALES
        leftPanel = new LeftLateralPanel(this);
        this.add(leftPanel);

        rightPanel = new RightLateralPanel(this);
        this.add(rightPanel);

        // ÚLTIMOS AJUSTES
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    protected void startClicked() {
        Distribution distribution = leftPanel.getDistribution();
        String proximity = leftPanel.getProximity();
        int nSolutions = leftPanel.getQuantityPairs();
        int n = leftPanel.getQuantityPoints();
        Method typeSolution = leftPanel.getSolution();

        this.modelo.reset(distribution, n, nSolutions, typeSolution, proximity);
        System.out.println(modelo.getPuntos().length);
        this.controlador.start();
    }

    public void paintGraph() {
        this.graphPanel.repaint();
    }

    public void setTime(long nanoseconds) {
        rightPanel.setTime(nanoseconds);
    }

    public void setBestResult() {
        this.rightPanel.soluciones.removeAll();
        Punto[][] sol = this.modelo.getSoluciones();
        Double[] dist = this.modelo.getDistancias();
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df2 = new DecimalFormat("#.########");
        Font font = new Font("Arial", Font.PLAIN, 8);
        for (int i = 0; i < dist.length; i++) {
            JLabel solucion1Label = new JLabel("{["
                    + df.format(sol[i][0].getX())
                    + "],[" + df.format(sol[i][0].getY())
                    + "]} - {[" + df.format(sol[i][1].getX())
                    + "],[" + df.format(sol[i][1].getY()) + "]}");
            solucion1Label.setFont(font);
            solucion1Label.setLayout(null);
            solucion1Label.setBounds(10, i * 34 + 2,
                    this.rightPanel.soluciones.getWidth() - 20, 20);
            this.rightPanel.soluciones.add(solucion1Label);
            
            JLabel distanciaLabel = new JLabel("Distancia: "+df2.format(dist[i]));
            distanciaLabel.setLayout(null);
            distanciaLabel.setBounds(10, i * 34 + 18,
                    this.rightPanel.soluciones.getWidth() - 20, 20);
            this.rightPanel.soluciones.add(distanciaLabel);
        }
        this.rightPanel.repaint();
    }

    // GETTERS & SETTERS
    public Controller getControlador() {
        return controlador;
    }

    public void setControlador(Controller controlador) {
        this.controlador = controlador;
    }

    public Model getModelo() {
        return modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public int getGraphWidth() {
        return GraphWidth;
    }

    public int getGraphHeight() {
        return GraphHeight;
    }

}
