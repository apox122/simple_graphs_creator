package data;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */

public class Node implements Serializable {
    private int x;
    private int y;
    private int r;
    private Color color;

    private String personName;


    public Node(int x, int y,String personName) {
        this.x = x;
        this.y = y;
        this.r = 30;
        this.color = Color.WHITE;
        this.personName=personName;
    }
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 30;
        this.color = Color.WHITE;
        this.personName=("");
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMouseOverNode(int mx, int my){
        return (x-mx)*(x-mx)+(y-my)*(y-my)<=r*r;
    }

    public void moveNode(int dx, int dy, Node node){
        node.setX(node.getX()+dx);
        node.setY(node.getY()+dy);
    }

    void draw(Graphics2D graphics){
        graphics.setColor(color);
        graphics.fillOval(x-r,y-r,2*r,2*r);
        graphics.setColor(Color.black);
        graphics.drawOval(x-r,y-r,2*r,2*r);
        graphics.drawString(String.valueOf(personName), x-personName.length()*3,y+5);
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + ", name: " + personName +"}";
    }
}
