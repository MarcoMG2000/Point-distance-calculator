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
    private Punto[][] soluciones; // Pila de parejas que forman la solución.
    private Distribution distribucion; // Distribución para generar los puntos.
    private Method metodo; // Método algoritmico para resolver el problema.
    private boolean minimizar; // Opción para minimizar o maximizar la distáncia entre puntos.
    private int cantidadParejas; // Cantidad parejas que guarda con sus distancias.
    private final int ANCHO = 500; // Ancho de la ventana.
    private final int ALTO = 490; // Alto de la ventana.

    // CONSTRUCTORS
    public Model() {
    }

    public Model(View vista, Controller controlador, int n) {
        this.vista = vista;
        this.controlador = controlador;
        this.N = n;
    }

    // CLASS METHODS
    /**
     * Método que genera aleatóriamente los puntos según la distribución
     * elegida.
     */
    public void generarDatos() {
        puntos = new Punto[N];
        Random rnd = new Random();
        switch (this.distribucion) {
            case GAUSSIAN -> {
                for (int i = 0; i < puntos.length; i++) {
                    double x = (rnd.nextGaussian() + 1) * ANCHO / 2;// Campana de Gauss en el centro de la ventana
                    double y = (rnd.nextGaussian() + 1) * ALTO / 2;
                    puntos[i] = new Punto(x, y);
                }
            }
            case CHI2 -> {
                // TODO
                for (int i = 0; i < puntos.length; i++) {
                    if (i < puntos.length / 2) {
                        double x = (rnd.nextGaussian() + 0.5) * ANCHO / 2;// Campana de Gauss en el cuartil izquierdo de la ventana
                        if (x < 0) {
                            x = 0;
                        }
                        double y = (rnd.nextGaussian() + 0.5) * ALTO / 2;
                        if (y < 0) {
                            y = 0;
                        }
                        puntos[i] = new Punto(x, y);
                    } else {
                        double x = (rnd.nextGaussian() + 1.5) * ANCHO / 2;// Campana de Gauss en el cuartil derecho de la ventana
                        if (x > ANCHO) {
                            x = ANCHO;
                        }
                        double y = (rnd.nextGaussian() + 1.5) * ALTO / 2;
                        if (y > ALTO) {
                            y = ALTO;
                        }
                        puntos[i] = new Punto(x, y);
                    }
                }
            }
            default ->
                throw new AssertionError();
        }
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

}
