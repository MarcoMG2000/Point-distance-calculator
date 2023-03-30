/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url 
 */
package controller;

import model.Model;
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
    
}
