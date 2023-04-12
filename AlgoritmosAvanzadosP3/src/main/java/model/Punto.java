/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package model;

/**
 * Clase para representar puntos en dos dimensiones, utilizando la interfaz
 * comparable para poder ordenarlos por la coordenada x e implementando el
 * método para medir la distancia entre dos puntos.
 */
public class Punto implements Comparable<Punto> {

    private Double x, y;
    
    private Double distanciaEsquina;


    private static final Double MARGEN = 0.0001; // Margen para considerar dos puntos iguales.
    // CONSTRUCTORS
    public Punto(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    // CLASS METHODS
    @Override
    public int compareTo(Punto o) {
        if (o.x > this.x - MARGEN && o.x < this.x + MARGEN) {
            return 0;
        } else if (this.x < o.x) {
            return -1;
        } else {
            return 1;
        }
    }

    public static double distancia(Punto p1, Punto p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // SETTERS & GETTERS
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public void setDistanciaEsquina (Punto pEsquina){
        this.distanciaEsquina = Punto.distancia(this, pEsquina);
    }
    
    public void setDistanciaEsquina (Double d){
        this.distanciaEsquina = d;
    }
    
    public double getDistanciaEsquina(){
        return this.distanciaEsquina;
    }
    
    
    @Override
    public String toString(){
        return "{Punto: x=" + this.x + " , y=" + this.y + " }";
    }

}
