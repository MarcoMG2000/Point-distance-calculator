/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package model;

import view.View;
import controller.Controller;
import java.util.Random;

/**
 * Modelo de la aplicación, aquí se guardan todos los datos necesarios para su
 * correcta operación.
 */
public class Model {

    // PUNTEROS DEL PATRÓN MVC
    private View vista;
    private Controller controlador;

    private int N; // Número de puntos
    private Punto[] puntos; // Puntos generados según la distribución.
    private Punto[][] soluciones; // Parejas que forman la solución.
    private Double[] distancias; // Distancia entre los puntos de soluciones.
    private Distribution distribucion; // Distribución para generar los puntos.
    private Method metodo; // Método algoritmico para resolver el problema.
    private boolean minimizar; // Opción para minimizar o maximizar la distáncia entre puntos.
    private int cantidadParejas; // Cantidad parejas que guarda con sus distancias.
    private int ANCHO; // Ancho de la ventana.
    private int ALTO; // Alto de la ventana.

    // CONSTRUCTORS
    public Model() {
    }

    public Model(View vista, Controller controlador, int n) {
        this.vista = vista;
        this.controlador = controlador;
        this.N = n;
        this.generarDatos();
        ANCHO = vista.getGraphWidth();
        ALTO = vista.getGraphHeight();
    }

    // CLASS METHODS
    /**
     * Método que genera aleatóriamente los puntos según la distribución
     * elegida.
     */
    private void generarDatos() {
        ANCHO = vista.getGraphWidth();
        ALTO = vista.getGraphHeight();
        puntos = new Punto[N];
        Random rnd = new Random();
        switch (this.distribucion) {
            case GAUSSIAN -> {
                double[] xg = distribucioGaussiana(N);
                double[] yg = distribucioGaussiana(N);
                for (int i = 0; i < puntos.length; i++) {
                    double x = (xg[i] + 1) * ANCHO / 2;// Campana de Gauss en el centro de la ventana
                    double y = (yg[i] + 1) * ALTO / 2;
                    puntos[i] = new Punto(x, y);
                }
            }
            case CHI2 -> {
                double[] xg = distribucioChi2(N);
                double[] yg = distribucioChi2(N);
                for (int i = 0; i < puntos.length; i++) {
                    double x = (xg[i] + 1) * ANCHO / 2;
                    double y = (yg[i] + 1) * ALTO / 2;
                    puntos[i] = new Punto(x, y);
                }
            }
            case UNIFORME -> {
                for (int i = 0; i < puntos.length; i++) {
                    double x = rnd.nextDouble() * ANCHO;
                    double y = rnd.nextDouble() * ALTO;
                    puntos[i] = new Punto(x, y);
                }
            }
            default ->
                throw new AssertionError();
        }
    }

    private double[] distribucioGaussiana(int n) {
        Random rand = new Random();
        double[] v = new double[n];
        double maxAbs = 0;
        for (int i = 0; i < v.length; i++) {
            v[i] = rand.nextGaussian();
            if (Math.abs(v[i]) > maxAbs) {
                maxAbs = Math.abs(v[i]);
            }
        }
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i] / maxAbs;
        }
        return v;
    }

    private double[] distribucioChi2(int n) {
        Random rand = new Random();
        double[] v = new double[n];
        double maxAbs = 0;
        for (int i = 0; i < v.length; i++) {
            if (i < v.length / 2) {
                v[i] = rand.nextGaussian() - 1.5;

            } else {
                v[i] = rand.nextGaussian() + 1.5;
            }
            if (Math.abs(v[i]) > maxAbs) {
                maxAbs = Math.abs(v[i]);
            }
        }
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i] / maxAbs;
        }
        return v;
    }

    /**
     * Inicializa los atributos soluciones y distancias para
     */
    private void initSoluciones() {

        Double distancia;
        if (minimizar) {
            distancia = Double.MAX_VALUE;
        } else {
            distancia = Double.MIN_VALUE;
        }

        Punto[] puntosAux = new Punto[2];
        puntosAux[0] = new Punto(0d, 0d);
        puntosAux[1] = new Punto(300d, 300d);

        for (int i = 0; i < distancias.length; i++) {
            distancias[i] = distancia;
            soluciones[i] = puntosAux;
        }

    }

    /**
     * Comprueba si el punto forma parte de la solución y lo añade de ser así.
     *
     * @param puntos posible pareja de puntos de la solución.
     */
    public void pushSolucion(Punto[] puntos) {

        // Buscamos la primera de las distancias que sea menor/mayor a la 
        // distancia entre los puntos de la posible solución que no esté repetido
        int k = -1;
        boolean repetido = false;
        boolean esSolucion = false;
        Double distancia = Punto.distancia(puntos[0], puntos[1]);
        for (int i = 0; i < distancias.length && !esSolucion && !repetido; i++) {
            // Comprobamos si es una posible solución
            if ((distancia < distancias[i] && minimizar) || (distancia > distancias[i] && !minimizar)) {
                k = i;
                esSolucion = true;
            }
            // Comprobamos si la solución está repetida
            if ((puntos[0].equals(soluciones[i][0]) && puntos[1].equals(soluciones[i][1]))
                    || (puntos[1].equals(soluciones[i][0]) && puntos[0].equals(soluciones[i][1]))) {
                repetido = true;
            }
        }

        // Si es una posible solución y no está repetido lo insertamos
        if (esSolucion && !repetido) {
            // Movemos una posición todas las distancias y puntos desde k
            for (int j = distancias.length - 1; j > k; j--) {
                distancias[j] = distancias[j - 1];
                soluciones[j] = soluciones[j - 1];
            }
            // Guardamos la nueva solución y su distancia
            distancias[k] = distancia;
            soluciones[k] = puntos;
        }

    }

    // GETTERS & SETTERS
    public View getVista() {
        return vista;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

    public Controller getControlador() {
        return controlador;
    }

    public void setControlador(Controller controlador) {
        this.controlador = controlador;
    }

    public Punto[] getPuntos() {
        return puntos;
    }

    public void setPuntos(Punto[] puntos) {
        this.puntos = puntos;
    }

    public Distribution getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(Distribution distribucion) {
        this.distribucion = distribucion;
    }

    public boolean isMinimizar() {
        return minimizar;
    }

    public void setMinimizar(boolean minimizar) {
        this.minimizar = minimizar;
    }

    public Method getMetodo() {
        return metodo;
    }

    public void setMetodo(Method metodo) {
        this.metodo = metodo;
    }

    public int getCantidadParejas() {
        return cantidadParejas;
    }

    public Double[] getDistancias() {
        return distancias;
    }

    public void setCantidadParejas(int cantidadParejas) {
        this.cantidadParejas = cantidadParejas;
    }

    public Punto[][] getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(Punto[][] soluciones) {
        this.soluciones = soluciones;
    }

    public void reset(Distribution distribution, int n, int nSolutions, Method typeSolution, String proximity) {
        this.cantidadParejas = nSolutions;
        this.distribucion = distribution;
        this.N = n;
        this.metodo = typeSolution;
        this.minimizar = false;
        if (proximity.equals("Cerca")) {
            this.minimizar = true;
        }
        this.soluciones = new Punto[nSolutions][2];
        this.distancias = new Double[nSolutions];
        this.initSoluciones();
        this.generarDatos();
    }

}
