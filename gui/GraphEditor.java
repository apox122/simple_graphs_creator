package gui;

import data.Graph;
import data.Node;
import gui.GraphPanel;
import data.Edge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */

public class GraphEditor extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Graphics2D Graphics2D;

    private static final String APP_TITLE = "Prosty edytor grafow";

    private static final String APP_AUTHOR = "Autor: Jakub Frydrych\n  Data: Grudzien 2022";

    private static final String APP_INSTRUCTION =
            "                  O P I S   P R O G R A M U \n\n"+
                    "Aktywna klawisze:\n" +
                    "   strzalki ==> przesuwanie wszystkich k�\n" +
                    "   SHIFT + strzalki ==> szybkie przesuwanie wszystkich k�\n\n" +
                    "ponadto gdy kursor wskazuje kolo:\n" +
                    "   DEL   ==> kasowanie kola\n" +
                    "   +, -   ==> powiekszanie, pomniejszanie ko�a\n" +
                    "   r,g,b ==> zmiana koloru kola\n\n" +
                    "Operacje myszka:\n" +
                    "   przeciaganie ==> przesuwanie wszystkich k�\n" +
                    "   PPM ==> tworzenie nowego kola w niejscu kursora\n" +
                    "ponadto gdy kursor wskazuje kolo:\n" +
                    "   przeciaganie ==> przesuwanie kola\n" +
                    "   PPM ==> zmiana koloru kola\n" +
                    "                     lub usuwanie kola\n";
            ;
    private Graph graph=new Graph();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuGraph = new JMenu("Graph");
    private JMenu menuHelp = new JMenu("Help");
    private JMenuItem menuNew = new JMenuItem("New", KeyEvent.VK_N);
    private JMenuItem menuShowExample = new JMenuItem("Example", KeyEvent.VK_X);
    private JMenuItem menuExit = new JMenuItem("Exit", KeyEvent.VK_E);
    private JMenuItem menuListOfNodes = new JMenuItem("List of Nodes", KeyEvent.VK_N);

    private JMenuItem menuListOfEdges = new JMenuItem("List of Edges", KeyEvent.VK_0);
    private JMenuItem menuAuthor = new JMenuItem("Author", KeyEvent.VK_A);
    private JMenuItem menuInstruction = new JMenuItem("Instruction", KeyEvent.VK_I);

    private JMenuItem saveToFile = new JMenuItem("Save graph to file", KeyEvent.VK_S);

    private JMenuItem getGraphFromFile = new JMenuItem("Get graph from file", KeyEvent.VK_S);

    private GraphPanel panel;



    private void showListOfNodes(Graph graph) {
        List<Node> nodes = graph.getNodes();
        int i = 0;
        StringBuilder message = new StringBuilder("Liczba wezlow " +nodes.size() + "\n");
        for (Node node : nodes) {
            message.append(node +" " +node.getColor() + "    ");
            if(++i%2==0){
                message.append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista wezlow", JOptionPane.PLAIN_MESSAGE);
    }
    private void showListOfEdges(Graph graph) {
        List<Edge> edges = graph.getEdges();
        int i = 0;
        StringBuilder message = new StringBuilder("Liczba wezlow " +edges.size() + "\n");
        for (Edge edge : edges) {
            message.append(edge+" " +edge.getColor() + "    ");
            if(++i%1==0){
                message.append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, message, APP_TITLE + " - Lista krawedzi", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        new GraphEditor();
    }

    public GraphEditor(){
        panel = new GraphPanel(graph);
        setTitle("Edytor Grafow ");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setContentPane(panel);
        createMenu();
        showBuildinExample();
        setVisible(true);

    }

    private void showBuildinExample() {
        Node n1 = new Node(100, 150, "Ania");
        Node n2 = new Node(200, 250,"Bartek");
        Node n3 = new Node(400, 350, "Jakub");
        n3.setColor(Color.yellow);
        Edge e1=new Edge(n1, n2);
        Edge e2=new Edge(n2, n3);
        n2.setColor(Color.GREEN);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(e1);
        graph.addNode(n3);
        graph.addEdge(e2);
        panel.setGraph(graph);
    }

    private void createMenu() {
        menuNew.addActionListener(this);
        menuShowExample.addActionListener(this);
        menuExit.addActionListener(this);
        menuListOfNodes.addActionListener(this);
        menuAuthor.addActionListener(this);
        menuInstruction.addActionListener(this);
        menuListOfEdges.addActionListener(this);
        getGraphFromFile.addActionListener(this);
        saveToFile.addActionListener(this);

        menuGraph.setMnemonic(KeyEvent.VK_G);
        menuGraph.add(menuNew);
        menuGraph.add(menuShowExample);
        menuGraph.addSeparator();
        menuGraph.add(menuListOfNodes);
        menuGraph.add(menuListOfEdges);
        menuGraph.addSeparator();
        menuGraph.add(getGraphFromFile);
        menuGraph.add(saveToFile);
        menuGraph.addSeparator();
        menuGraph.add(menuExit);

        menuHelp.setMnemonic(KeyEvent.VK_H);
        menuHelp.add(menuInstruction);
        menuHelp.add(menuAuthor);

        menuBar.add(menuGraph);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == menuNew) {
            panel.setGraph(new Graph());
        }
        if (source == getGraphFromFile) {
                getGraphFromFile();
        }
        if (source == saveToFile) {
            saveGraph();
        }
        if (source == menuShowExample) {
            showBuildinExample();
        }
        if (source == menuListOfNodes) {
            showListOfNodes(panel.getGraph());
        }
        if (source == menuListOfEdges) {
            showListOfEdges(panel.getGraph());
        }
        if (source == menuAuthor) {
            JOptionPane.showMessageDialog(this, APP_AUTHOR, APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
        }
        if (source == menuInstruction) {
            JOptionPane.showMessageDialog(this, APP_INSTRUCTION, APP_TITLE, JOptionPane.PLAIN_MESSAGE);
        }
        if (source == menuExit) {
            System.exit(0);
        }
    }
    private void saveGraph(){
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./"));
        fileChooser.showSaveDialog(this);
        File file = fileChooser.getSelectedFile();
        if(file==null){
            return;
        }
        Graph.saveGraphToFile(file,graph);
        JOptionPane.showMessageDialog(this,
                "Zapisano graf",
                "Zapis udany",
                JOptionPane.INFORMATION_MESSAGE);
    }
    private void getGraphFromFile(){
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./"));
        fileChooser.showSaveDialog(this);
        File file = fileChooser.getSelectedFile();
        if(file==null){
            return;
        }
        graph= Graph.readGraphFromFile(file);
        panel.setGraph(graph);
        JOptionPane.showMessageDialog(this,
                "Odczytano graf",
                "Odczyt udany",
                JOptionPane.INFORMATION_MESSAGE);
        repaint();
    }

}