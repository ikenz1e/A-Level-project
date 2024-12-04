package AI;

import java.util.ArrayList;

import main.GamePanel;

public class Pathfinder {

    public GamePanel gamePanel;
    private Node[][] nodeList;
    private ArrayList<Node> openList;
    private ArrayList<Node> pathList;
    private Node startNode;
    private Node goalNode;
    private Node currentNode;
    private boolean goalReached;
    private int step;
    private int maxStep = 500;

    public Pathfinder(GamePanel gp){
        this.goalReached = false;
        this.step = 0;
        this.gamePanel = gp;
        instantiateNodes();
    }

    // set all the tiles to be nodes in the nodeList[][] with sizes of max world columns and max world rows
    public void instantiateNodes(){
        // set the size of the nodeList
        nodeList = new Node[gamePanel.getMaxScreenCol()][gamePanel.getMaxScreenRow()];
        // instantiate initial column and initial row
        int col = 0;
        int row = 0;
        
        // loop through all the column and row values
        while (col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()){
            // set 
            nodeList[col][row] = new Node(col,row);
            col++;
            if (col == gamePanel.getMaxScreenCol()){
                col = 0;
                row++;
            }
        }
    }

    public void resetNodes(){
        int col = 0;
        int row = 0;

        while(col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()){
            nodeList[col][row].open = false;
            nodeList[col][row].checked = false;
            nodeList[col][row].solid = false;
            col++;

            if(col == gamePanel.getMaxScreenCol()){
                col = 0;
                row++;
            }
            openList.clear();
            pathList.clear();
            goalReached = false;
            step = 0;
        }
    }

    public void setNodes(int startCol, int startRow, int targetCol, int targetRow) {
        resetNodes();
        startNode = nodeList[startCol][startRow];
        currentNode = startNode;
        goalNode = nodeList[targetCol][targetRow];
        openList.add(currentNode);
        
        int col = 0;
        int row = 0;
        while(col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()){
            int tileNum = gamePanel.tileManager.mapTileNum[col][row];
            if(gamePanel.tileManager.tileArray[tileNum].collidable){
                nodeList[col][row].solid = true;
            }
        }
    }

    public void search(){
        while(!goalReached && step < maxStep){
            int col = currentNode.col;
            int row = currentNode.row;
            currentNode.checked = true;
            openList.remove(currentNode);
            
            // check up node exists
            if(row-1 >= 0){
                openNode(nodeList[col][row-1]);
            }
            // check left node exists
            if(col-1 >= 0){
                openNode(nodeList[col-1][row]);
            }
            // check down node exists
            if(row+1 <= gamePanel.getMaxScreenRow()){
                openNode(nodeList[col][row+1]);
            }
            // check right node exists
            if(col+1 <= gamePanel.getMaxScreenCol()){
                openNode(nodeList[col+1][row]);
            }

            
        }
    }

    public void openNode(Node node){

    }
}