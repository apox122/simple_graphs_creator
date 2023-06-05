package data;


import gui.GraphException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */

public class Graph implements Serializable {

    private List<Node> nodes;
    private List<Edge> edges;


    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
    }
    public void addNode(Node node){nodes.add(node);}

    public void addEdge(Edge edge){edges.add(edge);}

    public void removeNode(Node node){nodes.remove(node);}

    public void removeEdge(Edge edge){edges.remove(edge);}

    public void draw(Graphics2D g){
        for (Edge edge : edges) {
            edge.draw(g);
        }
        for(Node node : nodes){
            node.draw(g);
        }

    }


    public void moveAll(int dx, int dy) {
        for (Node node: getNodes()) {
            node.moveNode(dx, dy, node);
        }
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public static void saveGraphToFile(File file, Graph graph) {
        try (var nw = new FileOutputStream(file);
             var nw2= new ObjectOutputStream(nw))
        {
            nw2.writeObject(graph);
        } catch (FileNotFoundException exp)
        {
            throw new GraphException("Nie znaleziono pliku: " + file.getAbsolutePath());
        } catch (IOException exp)
        {
            throw new GraphException("Nie udalo sie zapisac do pliku wystapil blad");
        }
    }

    public static Graph readGraphFromFile(File file) {
        try (var nw = new FileInputStream(file);
             var nw2= new ObjectInputStream(nw)) {
            return (Graph) nw2.readObject() ;
        } catch (FileNotFoundException e)
        {
            throw new GraphException("Nie znaleziono pliku: " + file.getAbsolutePath());
        } catch (IOException e)
        {
            throw new GraphException("Wystapil blad podczas odczytu pliku");
        } catch (ClassNotFoundException e) {

            throw new GraphException("Wystapil blad (brak klasy)");
        }
    }
}
