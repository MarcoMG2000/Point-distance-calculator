/**
 * Practica 3 Algoritmos Avanzados - Ing Informática UIB
 *
 * @date 23/04/2023
 * @author jfher, JordiSM, peremarc, MarcoMG
 * @url
 */
package algoritmosavanzadosp3;

import controller.Controller;
import model.Model;
import view.View;

/**
 * Clase principal desde la que iniciamos la aplicación.
 */
public class AlgoritmosAvanzadosP3 {

    public static void main(String[] args) {
        MVCInit();
    }

    /**
     * Establece los punteros entre las distintas clases del patrón MVC para que
     * se puedan comunicar entre ellas.
     */
    private static void MVCInit() {
        Model modelo = new Model();
        View vista = new View();
        Controller controlador = new Controller();

        modelo.setVista(vista);
        modelo.setControlador(controlador);

        vista.setModelo(modelo);
        vista.setControlador(controlador);

        controlador.setModelo(modelo);
        controlador.setVista(vista);

        vista.mostrar();
    }

}
