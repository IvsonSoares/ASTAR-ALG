import java.util.Stack;

public class Node {

    public double f = 0;
    public int g = 0;
    public double h = 0;

    public Node previous;

    public int row;
    public int column;

    private char character;

    private Stack<Node> neighbors = new Stack<Node>();

    public boolean wall = false;

    public Node(char character, int column, int row){
        this.character = character;
        this.column = column;
        this.row = row;

        this.setWall();
        if(this.wall){
            this.setCharacter('#');
        }
    }

    public void addNeighbors(Stack<Stack<Node>> grid, int cols, int rows){
        int i = this.column;
        int j = this.row;

        if(i < cols - 1){   
            this.neighbors.push(grid.get(i + 1).get(j));
        }

        if(i > 0){
            this.neighbors.push(grid.get(i - 1).get(j));
        }

        if(j < rows - 1){
            this.neighbors.push(grid.get(i).get(j + 1));
        }

        if(j > 0){
            this.neighbors.push(grid.get(i).get(j - 1));
        }

    }

    //number of obstacles
    public void setWall(){
        double rand = Math.random();
        if(rand < 0.3){
            this.wall = true;
        }
    }

    public boolean getWall(){
        return this.wall;
    }

    public void setPreviousNeighbor(Node neighbor){
        this.previous = neighbor;
    }

    public Node getPreviousNeighbor(){
        return this.previous;
    }


    public String getIJTuple(){
        return "(" + this.getRow() + "," + this.getColumn() + ")";
    }
    

    public void setCharacter(char character){
        this.character = character;
    }

    public char getCharacter(){
        return this.character;
    }

    public int getColumn(){
        return this.column;
    }

    public int getRow(){
        return this.row;
    }

    public Stack<Node> getNeighbors(){

        return this.neighbors;
    }
}
