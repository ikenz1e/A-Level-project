package AI;

public class Node {

    public Node parent;
    public int col;
    public int row;
    public int gCost;
    public int hCost;
    public int fCost;
    public boolean solid;
    public boolean open;
    public boolean checked = false;

    public Node(int nodeCol, int nodeRow){
        this.col = nodeCol;
        this.row = nodeRow;
    }
}
