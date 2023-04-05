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
import java.util.Arrays;
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
    private Punto[][] soluciones; // Pila de parejas que forman la solución.
    private Distribution distribucion; // Distribución para generar los puntos.
    private Method metodo; // Método algoritmico para resolver el problema.
    private boolean minimizar; // Opción para minimizar o maximizar la distáncia entre puntos.
    private int cantidadParejas; // Cantidad parejas que guarda con sus distancias.
    private final int ANCHO = 850; // Ancho de la ventana.
    private final int ALTO = 650; // Alto de la ventana.
    
    // CONSTRUCTORS
    public Model() {
    }

    public Model(View vista, Controller controlador, int n) {
        this.vista = vista;
        this.controlador = controlador;
        this.N = n;
        this.generarDatos(n);
    }

    // CLASS METHODS
    /**
     * Método que genera aleatóriamente los puntos según la distribución
     * elegida.
     */
    public void generarDatos(int n) {
        puntos = new Punto[n];
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
                v[i] = rand.nextGaussian() - 0.5;

            } else {
                v[i] = rand.nextGaussian() + 0.5;
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
     * Introduce un punto en la pila de puntos que pueden formar parte de la
     * solución.
     *
     * @param puntos posible pareja de puntos de la solución.
     */
    public void pushSolucion(Punto[] puntos) {
        System.arraycopy(soluciones, 0, soluciones, 1, cantidadParejas - 1);
        this.soluciones[0] = puntos;
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
        if(proximity.equals("Cerca")){
            this.minimizar = true;
        }
        this.soluciones = new Punto[nSolutions][2];
        
        this.generarDatos(n);
    }

}
