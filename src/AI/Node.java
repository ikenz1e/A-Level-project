package AI;

// class used for nodes in the A* pathfinding algorithm found in the Pathfinder class
public class Node {

    // the parent node
    public Node parent;
    // the coordinates in the form (col, row)
    public int col;
    public int row;
    // the 3 costs used in A*
    public int gCost;
    public int hCost;
    public int fCost;
    // is the node a collidable tile
    public boolean solid;
    // is it open for the NPC to walk on
    public boolean open;
    // has the node been checked
    public boolean checked;

    // constructor, taking the coordinates as paramaters
    public Node(int nodeCol, int nodeRow){
        // assign the coordinates, using the parameters
        this.col = nodeCol;
        this.row = nodeRow;
        // node has not been checked by default
        this.checked = false;
    }
}
