/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package controller;

import java.util.Arrays;
import model.Distribution;
import model.Method;
import model.Model;
import model.Punto;
import view.View;

/**
 * Controlador de la aplicación, aquí se procesarán las funciones y los cálculos
 * de la aplicación.
 */
public class Controller {

    // PUNTEROS DEL PATRÓN MVC
    private Model modelo;
    private View vista;

    // CONSTRUCTORS
    public Controller() {
    }

    public Controller(Model modelo, View vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    // CLASS METHODS
    /**
     * Método que busca los n puntos más cercanos o lejanos entre si de entre
     * los puntos generados en el modelo, contiene dos métodos de calcular los 
     * puntos, uno utilizando fuerza bruta y otro utilizando divide y vencerás.
     */
    public void start() {
        Punto[] puntos = modelo.getPuntos();
        System.out.println(puntos.length);
        // Distribution distribucion = modelo.getDistribucion();
        Method metodo = modelo.getMetodo();
        boolean minimizar = modelo.isMinimizar();
        int cantidadParejas = modelo.getCantidadParejas();

        // Inicializamos el array de soluciones
        Punto[][] soluciones = new Punto[cantidadParejas][2];
        modelo.setSoluciones(soluciones);

        switch (metodo) {
            case FUERZA_BRUTA ->
                encontrarParejasBF(puntos, minimizar);
            case DIVIDE_Y_VENCERAS -> {
                //Arrays.sort(puntos);
                Arrays.sort(puntos, 0, puntos.length - 1, (a, b)
                        -> Double.compare(a.getX(), b.getX()));
                encontrarParejasDC(puntos, minimizar);
                break;
            }
            default ->
                throw new AssertionError();
        }
        this.vista.paintGraph();
        
    }

    /**
     * Método para encontrar las n parejas más cercanas de puntos cuya distancia
     * es la mínima o máxima entre todos los puntos, el método utilzado es la
     * fuerza bruta (Brute Force).
     * 
     * @param puntos array de puntos sobre los que operar.
     * @param minimizar si se busca la distancia mínima o máxima entre puntos.
     */
    private void encontrarParejasBF(Punto[] puntos, boolean minimizar) {
        Double min = Double.MAX_VALUE;
        Double max = Double.MIN_VALUE;

        // Buscamos las distancias de todos los puntos entre ellos
        Double distancia;
        for (int i = 0; i < puntos.length; i++) {
            for (int j = i + 1; j < puntos.length; j++) {
                distancia = Punto.distancia(puntos[i], puntos[j]);
                if (distancia < min && minimizar) {
                    // Guardamos la última solución encontrada
                    Punto[] posibleSolucion = {puntos[i], puntos[j]};
                    modelo.pushSolucion(posibleSolucion);
                } else if (distancia > max && !minimizar) {
                    // Guardamos la última posible solución encontrada
                    Punto[] posibleSolucion = {puntos[i], puntos[j]};
                    modelo.pushSolucion(posibleSolucion);
                }
            }
        }
        System.out.println("Finished");
        System.out.println(modelo.getSoluciones());
    }

    /**
     * Método para encontrar las n parejas más cercanas de puntos cuya distancia
     * es la mínima o máxima entre todos los puntos, el método utilzado es la
     * divide y vencerás (Divide & Conquer), este método inicializa el algoritmo
     * recursivo que encuentra la solución.
     * 
     * @param puntos array de puntos sobre los que operar.
     * @param minimizar si se busca la distancia mínima o máxima entre puntos.
     */
    private double encontrarParejasDC(Punto[] puntos, boolean minimizar) {
        return encontrarParejasDC(puntos, 0, puntos.length - 1, minimizar);
    }

    /**
     * Método recursivo que soluciona el problema de encontrar las n parejas de
     * cículos más cercanos/lejanos.
     * 
     * @param puntos array de puntos sobre los que operar.
     * @param left indice de la parte izquierda del array que se está operando.
     * @param right indice de la parte derecha del array que se está operando.
     * @param minimizar si se busca la distancia mínima o máxima entre puntos.
     * @return
     */
    private double encontrarParejasDC(Punto[] puntos, int left, int right, boolean minimizar) {
        if (right - left == 2) {
            return Punto.distancia(puntos[left], puntos[right]);
        } else if (right - left < 2) {
            return Double.MAX_VALUE;
        } else {
            // Partimos los datos por la mitad
            int middle = (right + left) / 2;
            double distanceLeft = encontrarParejasDC(puntos, left, middle - 1, minimizar);
            double distanceRight = encontrarParejasDC(puntos, middle, right, minimizar);

            // Obtenemos la mínima/máxima distancia entre los puntos del array
            // izquierdo y los puntos del array derecho
            double distance;
            if (minimizar) {
                distance = Math.min(distanceLeft, distanceRight);
            } else {
                distance = Math.max(distanceLeft, distanceRight);
            }

            // Guardamos los puntos que caen en la porción 2*distance con el
            // centro en middle
            Punto[] porcion = new Punto[right - left + 1];
            int j = 0;
            for (int i = left; i <= right; i++) {
                if (Math.abs(puntos[i].getX() - puntos[middle].getX()) < distance && minimizar) {
                    porcion[j++] = puntos[i];
                } else if (Math.abs(puntos[i].getX() - puntos[middle].getX()) > distance && !minimizar) {
                    porcion[j++] = puntos[i];
                }
            }

            // Ordenamos la porción por la coordenada y
            Arrays.sort(porcion, 0, j, (a, b)
                    -> Double.compare(a.getY(), b.getY()));

            // Buscamos los puntos mas cercanos/lejanos en la sección
            for (int i = 0; i < j; i++) {
                for (int k = i + 1; k < j && porcion[k].getY() - porcion[i].getY() < distance; k++) {
                    double distanceAux = Punto.distancia(porcion[i], porcion[k]);
                    if (distanceAux < distance && minimizar) {
                        distance = distanceAux;
                        modelo.pushSolucion(puntos);
                    } else if (distanceAux > distance && !minimizar) {
                        distance = distanceAux;
                        // Guardamos la última posible solución encontrada
                        Punto[] posibleSolucion = {puntos[i], puntos[j]};
                        modelo.pushSolucion(posibleSolucion);
                    }
                }
            }
            return distance;
        }
    }

    // SETTERS & GETTERS
    public Model getModelo() {
        return modelo;
    }

    public void setModelo(Model modelo) {
        this.modelo = modelo;
    }

    public View getVista() {
        return vista;
    }

    public void setVista(View vista) {
        this.vista = vista;
    }

    public void start(String typeSolution, String proximity) {
    }

}
