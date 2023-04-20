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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    protected final int MARGENLAT = 300;
    protected final int MARGENVER = 20;

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
        this.setResizable(false);

        this.GraphWidth = 800;
        this.GraphHeight = 800;

        // DIMENSION DEL JFRAME
        setSize(this.GraphWidth + this.MARGENLAT * 2, this.GraphHeight + this.MARGENVER + 40);

        // POSICIONAR EL JFRAME EN EL CENTRO DE LA PANTALLA
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

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

        // Agregamos un listener para capturar el clic  
        graphPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /* Capturamos la imagen 
                   Tener en cuenta: Cuanto mayor sea el numero que se suma y
                   resta a las coordenadas, mayor será la zona que se captura
                 */
                BufferedImage captura = getCaptura(graphPanel, e.getX() - 20, e.getY() - 20, e.getX() + 20, e.getY() + 20);
                Image capturaEscalada = captura.getScaledInstance(captura.getWidth() * 10, captura.getHeight() * 10, Image.SCALE_SMOOTH);
                JLabel capturaLabel = new JLabel(new ImageIcon(capturaEscalada));
                JPanel panelCaptura = new JPanel();
                panelCaptura.setLayout(null);
                panelCaptura.setPreferredSize(new Dimension(capturaEscalada.getWidth(null), capturaEscalada.getHeight(null)));
                panelCaptura.add(capturaLabel, BorderLayout.CENTER);
                JDialog dialogCaptura = new JDialog();
                dialogCaptura.setTitle("Zoom");
                dialogCaptura.setBounds(graphPanel.getX() + 244,
                        graphPanel.getY(), 500, 500);
                dialogCaptura.setResizable(false);
                //dialog.setSize(new Dimension(500, 500));
                dialogCaptura.add(capturaLabel);
                dialogCaptura.setVisible(true);
            }
        });
    }

    // Función para capturar la imagen  
    private BufferedImage getCaptura(JPanel panel, int x1, int y1, int x2, int y2) {
        int width = x2 - x1; // Ancho captura
        int height = y2 - y1; // Alto captura

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        panel.paint(graphics.create(-x1, -y1, panel.getWidth(), panel.getHeight()));
        graphics.dispose();
        return image;
    }

    protected void startClicked() {
        /**
         *  int nSolutions, Method typeSolution, String proximity
         */
        if (this.modelo.exists()) {
            String proximity = leftPanel.getProximity();
            int nSolutions = leftPanel.getQuantityPairs();
            Method typeSolution = leftPanel.getSolution();
        
            this.modelo.setnSoluciones(nSolutions);
            this.modelo.setMinimizar(proximity.equals("Cerca"));
            this.modelo.setMetodo(typeSolution);
            
            this.controlador.start();
            
        } else {
            new Notification("Por favor, Genere los Datos");
        }

    }

    protected void generatePointsClicked() {
        // Obtenemos la configuración actual
        Distribution distribution = leftPanel.getDistribution();
        int n = leftPanel.getQuantityPoints();

        // Reiniciamos el modelo con la configuración obtenida
        this.modelo.reset(distribution, n);

        // Mostramos por pantalla los Puntos
        this.paintGraph();
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
        DecimalFormat df = new DecimalFormat("#.#############");
        DecimalFormat df2 = new DecimalFormat("#.##########");
        Font font = new Font("Arial Black", Font.PLAIN, 12);
        for (int i = 0; i < dist.length; i++) {
            JLabel pntL = new JLabel((i + 1) + " solución.");
            pntL.setFont(font);
            pntL.setBounds(10, i * 80 + 6,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
            JLabel labelX1 = new JLabel("x1: "
                    + df.format(sol[i][0].getX()));
            labelX1.setLayout(null);
            labelX1.setBounds(10, i * 80 + 18,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
            JLabel labelY1 = new JLabel("y1: "
                    + df.format(sol[i][0].getY()));
            labelY1.setLayout(null);
            labelY1.setBounds(10, i * 80 + 30,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
            JLabel labelX2 = new JLabel("x2: "
                    + df.format(sol[i][1].getX()));
            labelX2.setLayout(null);
            labelX2.setBounds(10, i * 80 + 42,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
            JLabel labelY2 = new JLabel("y2: "
                    + df.format(sol[i][1].getY()));
            labelY2.setLayout(null);
            labelY2.setBounds(10, i * 80 + 54,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
            this.rightPanel.soluciones.add(labelX1);
            this.rightPanel.soluciones.add(labelY1);
            this.rightPanel.soluciones.add(labelX2);
            this.rightPanel.soluciones.add(labelY2);
            this.rightPanel.soluciones.add(pntL);

            JLabel distanciaLabel = new JLabel("Distancia: " + df2.format(dist[i]));
            distanciaLabel.setLayout(null);
            distanciaLabel.setBounds(10, i * 80 + 66,
                    this.rightPanel.soluciones.getWidth() - 20, 12);
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
