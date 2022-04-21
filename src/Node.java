import java.util.Stack;

public class Node {

    public double fScore = 0;
    public int    gScore = 0;
    public double h = 0;

    public Node camefrom;

    public int row;
    public int column;

    private char character;

    private Stack<Node> neighbors = new Stack<Node>();

    public boolean obstacle = false;

    public Node(char character, int column, int row){
        this.character = character;
        this.column = column;
        this.row = row;

        this.setObstacle();
        if(this.obstacle){
            this.setCharacter('#');
        }
    }

    public void addNeighbors(Stack<Stack<Node>> matrix, int cols, int rows){
        int i = this.column;
        int j = this.row;

        if(i < cols - 1){   
            this.neighbors.push(matrix.get(i + 1).get(j));
        }

        if(i > 0){
            this.neighbors.push(matrix.get(i - 1).get(j));
        }

        if(j < rows - 1){
            this.neighbors.push(matrix.get(i).get(j + 1));
        }

        if(j > 0){
            this.neighbors.push(matrix.get(i).get(j - 1));
        }

    }

    //number of obstacles
    public void setObstacle(){
        double rand = Math.random();
        if(rand < 0.3){
            this.obstacle = true;
        }
    }

    public boolean getObstacle(){
        return this.obstacle;
    }

    public void setCameFrom(Node camefrom){
        this.camefrom = camefrom;
    }

    public Node getCameFrom(){
        return this.camefrom;
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
