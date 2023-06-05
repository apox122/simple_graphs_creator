package data;

import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;

/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */
public class Edge implements Serializable {

    private Node firstNode;
    private Node secondNode;

    private Color color;

    public Edge(Node firstNode, Node secondNode) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.color=Color.BLACK;
    }


    void draw(Graphics2D graphics) {
        graphics.setStroke(new BasicStroke((float) 2.5));
        graphics.setColor(color);
        graphics.drawLine(firstNode.getX(), firstNode.getY(), secondNode.getX(), secondNode.getY());

    }

    private double[] getLine(){
        int x = firstNode.getX();
        int y = firstNode.getY();
        int y1 = secondNode.getY();
        int x1 = secondNode.getX();
        double a = (double) (y-y1) / (x - x1);
        double b= (double)((x*y1) - (x1*y))/(x-x1);
        return new double[]{a,b};

    }

    public boolean isMouseOverEdge(int mx, int my){
        double[] line = getLine();
        double a=line[0];
        double b=line[1];
        double y=a*mx+b;
        return (Math.abs(y - my) <= 10);

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(Node secondNode) {
        this.secondNode = secondNode;
    }

    @Override
    public String toString() {
        return "First point: "+getFirstNode() + " Second point: "+ getSecondNode();
    }
}