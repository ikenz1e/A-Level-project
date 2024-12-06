package AI;

import java.util.ArrayList;

import main.GamePanel;

public class Pathfinder {

    // get an instance of the game panel
    public GamePanel gamePanel;
    // 2D list of all the nodes
    private Node[][] nodeList;
    // arrays of type Node for the open List and path List
    private ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    // start, goal and current nodes
    private Node startNode;
    private Node goalNode;
    private Node currentNode;
    // has the goal been reached - defensive
    private boolean goalReached;
    // number of steps and max steps to prevent an infinite loop
    private int step;
    private int maxStep = 500;


    // constructor - pass in gamepanel as a parameter
    public Pathfinder(GamePanel gp){
        // goal has not been reached by default
        this.goalReached = false;
        // no steps have been taken
        this.step = 0;
        // assign the instance of the game panel
        this.gamePanel = gp;
        // instantiate the nodes
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
            // set the node at index [col][row] to a new node with coordinates (col,row)
            nodeList[col][row] = new Node(col,row);
            col++;
            // if we have reached the max limit for columns, go back to the start and go to the next row
            if (col == gamePanel.getMaxScreenCol()){
                col = 0;
                row++;
            }
        }
    }

    // restNodes() method
    public void resetNodes(){
        int col = 0;
        int row = 0;

        // loop through all the possible columns and rows
        while(col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()){
            // set open, checked and solid attributes of the node at the current coordinates to false
            nodeList[col][row].open = false;
            nodeList[col][row].checked = false;
            nodeList[col][row].solid = false;
            // increment the column
            col++;

            // if it is at the end of the map
            if(col == gamePanel.getMaxScreenCol()){
                // go back to the start 
                col = 0;
                // go to the next row
                row++;
            }
            // set lists and attributes back to default
            openList.clear();
            pathList.clear();
            goalReached = false;
        }
        step = 0;
    }

    // setNodes method, taking the coordinates of the starting node and the target node
    public void setNodes(int startCol, int startRow, int targetCol, int targetRow) {
        // begin by resetting the nodes
        resetNodes();
        // assing the start, current and goal nodes
        startNode = nodeList[startCol][startRow];
        currentNode = startNode;
        goalNode = nodeList[targetCol][targetRow];
        // add the current node to the open list
        openList.add(currentNode);
        
        // start at (0,0)
        int col = 0;
        int row = 0;
        // loop through all columns and rows
        while(col < gamePanel.getMaxScreenCol() && row < gamePanel.getMaxScreenRow()){
            // get tile number of the current tile
            int tileNum = gamePanel.tileManager.mapTileNum[col][row];
            // if the tile is collidable
            if(gamePanel.tileManager.tileArray[tileNum].collidable){
                // set it to solid - the NPC cannot walk through, therefore cannot be part of the path
                nodeList[col][row].solid = true;
            }
            col++;
            if (col == gamePanel.getMaxScreenCol()){
                col = 0;
                row++;
            }
        }
    }

    // search() method
    public boolean search(){
        // while the goal hasnt been reached and the maximum amount of steps hasnt been met
        while(!goalReached && step < maxStep){
            // get the coordinates of the current node
            int col = currentNode.col;
            int row = currentNode.row;
            // set it to checked
            currentNode.checked = true;
            // remove it from the open list
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
            
            // calculate the index of the best node and the f cost of the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;
            
            // go through all the nodes in the openList - linear search
            for(int i=0; i < openList.size(); i++){
                // if it has a lower f cost than the current best fcost
                if(openList.get(i).fCost < bestNodeFCost){
                    // set the best index to the current index
                    bestNodeIndex = i;
                    // set the best f cost the the fCost of the node in the current index
                    bestNodeFCost = openList.get(i).fCost;
                    // if the f cost is equal to the best f cost
                }else if(openList.get(i).fCost == bestNodeFCost){
                    // if the g cost is lower than the best nodes g cost
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        // set the index of the best node to the current index
                        bestNodeIndex = i;
                    }
                }
            }

            // if the opem list is empty, end the loop
            if(openList.size() == 0){
                break;
            }

            // set the current node to the best node
            currentNode = openList.get(bestNodeIndex);

            // if the goal node has been reached
            if(currentNode == goalNode){
                goalReached = true;
                // get the path
                trackPath();
            }

            // increment the step
            step++;            
        }

        // return if the goal was reached or not
        return goalReached;
    }

    // openNode method, takes a node of type Node as parameter
    public void openNode(Node node){
        // if the node isnt open and checked and solid 
        if(!node.open && !node.checked && !node.solid){
            // set the node to open, set the parent to be the current node and add it to the open list
            node.open=true;
            node.parent = currentNode;
            openList.add(node);
        }
    }

    // trackPath method
    public void trackPath(){
        // set the current to the goal - start from the end
        Node current = goalNode;
        // loop while the current node isnt the start node
        while(current != startNode){
            // add the current node to the path list
            pathList.add(current);
            // set the current to the parent
            current = current.parent;
        }
    }
}