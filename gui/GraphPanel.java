package gui;

import data.Edge;
import data.Graph;
import data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
JAKUB FRYDRYCH 06.12.2002
263991
Prosty edytor gragów, LAB4
 */
public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private Graph graph;

    private int mouseX = 0;
    private int mouseY = 0;

    protected int mouseCursor = Cursor.DEFAULT_CURSOR;

    private boolean mouseButtonLeft = false;

    private boolean mouseButtonRigth = false;

    protected Node nodeUnderCursor = null;

    private Edge edgeUnderCursor;

    private boolean startAddingEdge=false;
    private Node nodeToAddEdge=null;




    public Node findNode(int mx, int my) {
        for (Node node : graph.getNodes()) {
            if (node.isMouseOverNode(mx, my)) {
                return node;
            }
        }
        return null;
    }

    private Node findNode(MouseEvent event) {
        return findNode(event.getX(), event.getY());
    }

    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == 1) {
            mouseButtonLeft=true;
            if(startAddingEdge && nodeUnderCursor!=null && nodeUnderCursor!=nodeToAddEdge){
                graph.addEdge(new Edge(nodeUnderCursor,nodeToAddEdge));
                startAddingEdge=false;
                nodeToAddEdge=null;
            }
        }   if (event.getButton() == 3) {
            mouseButtonRigth= true;
        }
    }



    @Override
    public void mouseReleased(MouseEvent event) {

        if (event.getButton() == 3) {
            mouseButtonRigth = false;
        }
        if (event.getButton() == 1) {
            mouseButtonRigth = false;
        }

        nodeUnderCursor = findNode(event);

        if (event.getButton()==3){
            if (nodeUnderCursor!=null){
                createPopupMenuNode(event,nodeUnderCursor);
            }
            if (edgeUnderCursor!=null){
                createPopupMenuEdge(event,edgeUnderCursor);
            }
            if (nodeUnderCursor==null && edgeUnderCursor==null){
                createPopupMenu(event);
            }
        }
    }

    private void createPopupMenu(MouseEvent event) {
        JMenuItem menuItemAddNode;
        JPopupMenu popupMenu=new JPopupMenu();
        menuItemAddNode=new JMenuItem("Add node");
        menuItemAddNode.addActionListener(x->{
            graph.addNode(new Node(event.getX(), event.getY()));
            repaint();
        });
        popupMenu.add(menuItemAddNode);
        popupMenu.show(event.getComponent(), event.getX(), event.getY());
    }

    private void createPopupMenuEdge(MouseEvent event, Edge edgeUnder) {
        JMenuItem menuItem;
        JPopupMenu popupMenu=new JPopupMenu();
        menuItem=new JMenuItem("Remove edge");
        menuItem.addActionListener(x-> {
            graph.removeEdge(edgeUnder);
            repaint();
        });
        popupMenu.add(menuItem);

        menuItem=new JMenuItem("Change edge color");
        menuItem.addActionListener(x -> {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    edgeUnder.getColor());
            if (newColor != null) {
                edgeUnder.setColor(newColor);
            }
            repaint();
        });
        popupMenu.add(menuItem);
        popupMenu.show(event.getComponent(), event.getX(), event.getY());


    }

    private void createPopupMenuNode(MouseEvent event, Node nodeUnder) {
        JMenuItem menuItem;
        JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Change node color");
        menuItem.addActionListener((a) -> {
            Color newColor = JColorChooser.showDialog(
                    this,
                    "Choose Background Color",
                    nodeUnder.getColor());
            if (newColor != null) {
                nodeUnder.setColor(newColor);
            }
            repaint();

        });

        popup.add(menuItem);
        menuItem = new JMenuItem("Remove this node");

        menuItem.addActionListener((action) -> {
            graph.removeNode(nodeUnder);
            repaint();
        });
        popup.add(menuItem);

        menuItem=new JMenuItem("Add edge from this node");
        menuItem.addActionListener(x->{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            nodeToAddEdge=nodeUnder;
            startAddingEdge=true;

        });
        popup.add(menuItem);
        menuItem=new JMenuItem("Change node name");
        menuItem.addActionListener(x->{
            changeNodeName(nodeUnder);
        });
        popup.add(menuItem);
        popup.show(event.getComponent(), event.getX(), event.getY());
    }

    public void changeNodeName(Node node){
        String personName=JOptionPane.showInputDialog(this, "Wpisz nowa nazwe", JOptionPane.INFORMATION_MESSAGE);
        if (personName==null){
            personName="--";
        }
        else {
            node.setPersonName(personName);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {


    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (mouseButtonLeft){
            if (nodeUnderCursor!=null){
                nodeUnderCursor.moveNode(event.getX()-mouseX, event.getY()-mouseY,nodeUnderCursor);
            }
            else if (edgeUnderCursor!=null){
                Node firstNode=edgeUnderCursor.getFirstNode();
                Node secondNode=edgeUnderCursor.getSecondNode();
                firstNode.moveNode(event.getX()-mouseX,event.getY()-mouseY,firstNode);
                secondNode.moveNode(event.getX()-mouseX,event.getY()-mouseY,secondNode);
            }
            else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                graph.moveAll(event.getX()-mouseX,event.getY()-mouseY);
            }
        }
        mouseX = event.getX();
        mouseY = event.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        nodeUnderCursor = findNode(mouseX, mouseY);
        edgeUnderCursor = findEdgeOnCoursor(mouseX, mouseY);
        if (nodeUnderCursor != null) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
        } else if (edgeUnderCursor != null) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
        else if (startAddingEdge){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        repaint();


    }

    private Edge findEdgeOnCoursor(int mouseX, int mouseY) {
        Edge edgeUnder = null;
        for (Edge edge : graph.getEdges()) {
            if(edge.isMouseOverEdge(mouseX,mouseY)&& nodeUnderCursor==null){
                edgeUnder=edge;
                return edgeUnder;
            }
        }
        return edgeUnder;
    }


    @Override
    public void keyTyped(KeyEvent event) {
        char znak = event.getKeyChar();
        if (nodeUnderCursor != null) {
            switch (znak) {
                case 'r':
                    nodeUnderCursor.setColor(Color.RED);
                    break;
                case 'g':
                    nodeUnderCursor.setColor(Color.GREEN);
                    break;
                case 'b':
                    nodeUnderCursor.setColor(Color.BLUE);
                    break;
                case '+':
                    int r = nodeUnderCursor.getR() + 10;
                    nodeUnderCursor.setR(r);
                    break;
                case '-':
                    r = nodeUnderCursor.getR() - 10;
                    if (r >= 10) nodeUnderCursor.setR(r);
                    break;
            }
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        {  int dist;
            if (event.isShiftDown()) dist = 10;
            else dist = 1;
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    graph.moveAll(-dist, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    graph.moveAll(dist, 0);
                    break;
                case KeyEvent.VK_UP:
                    graph.moveAll(0, -dist);
                    break;
                case KeyEvent.VK_DOWN:
                    graph.moveAll(0, dist);
                    break;
                case KeyEvent.VK_DELETE:
                    if (nodeUnderCursor != null) {
                        graph.removeNode(nodeUnderCursor);
                        nodeUnderCursor = null;
                    }
                    break;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public GraphPanel(Graph graph) {
        this.graph=graph;
        this.addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintComponent((Graphics2D) g);
    }

    protected void paintComponent(Graphics2D g2d){
        super.paintComponent(g2d);
        if (graph == null) return;
        graph.draw(g2d);
    }

    public void setGraph(Graph graph) {this.graph=graph;
    }

    public Graph getGraph() {
        return graph;
    }
}
