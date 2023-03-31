/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.geom.Point2D;

/**
 *
 * @author lostr
 */
public class Punto extends Point2D.Double implements Comparable<Punto> {

    @Override
    public int compareTo(Punto o) {
        if(this.x < o.x) return -1;
        else if (this.x > o.x) return 1;
        return 0;
    }
    
}
