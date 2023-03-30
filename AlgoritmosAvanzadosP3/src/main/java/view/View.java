/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url 
 */
package view;

import model.Model;
import controller.Controller;
import javax.swing.JFrame;


/**
 * Vista de la aplicación, aquí interactuaremos con la aplicación y 
 * visualizaremos todos los datos y los resultados de las operaciónes.
 */
public class View extends JFrame {
    
    // PUNTEROS DEL PATRÓN MVC
    private Controller controlador;
    private Model modelo;
    
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
        // TODO: implementar método.
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
    
}
