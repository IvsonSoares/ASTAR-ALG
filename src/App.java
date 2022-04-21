import java.util.Stack;

public class App {
    public static void main(String[] args) throws Exception {

        
        Stack<Stack<Node>> grid = new Stack<Stack<Node>>();

        var cols = 20;
        var rows = 20;


        for(int i = 0; i < cols; i++){
            grid.push(new Stack<Node>());
        }

        Stack<Node> openSet = new Stack<Node>();
        Stack<Node> closedSet = new Stack<Node>();

        Node start;
        Node end;

        

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                grid.get(i).push(new Node(' ', i, j));
                
            }
        }

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                grid.get(i).get(j).addNeighbors(grid, cols, rows);
            }
        }

        start = grid.get(1).get(1);
        start.setCharacter('S');

        end = grid.get(rows - 4).get(cols - 8);
        end.setCharacter('E');

        printGrid(grid, cols, rows);
        System.out.println();
        System.out.println();
        
        openSet.push(grid.get(start.getColumn()).get(start.getRow()));
        

        boolean loop = true;

        while(loop){

            if(openSet.size() > 0){

                int winner = 0;
                for(int i = 0; i < openSet.size(); i++){
                    if(openSet.get(i).f < openSet.get(winner).f){
                        winner = i;
                    }
                }

                Node current = openSet.get(winner);
                if(!(current.getCharacter() == 'S') && (!(current.getCharacter() == 'E'))){
                    current.setCharacter('-');
                }
                 
                
                if(current.equals(end)){
                    Stack<Node> path = new Stack<Node>();
                    Node temp = current;

                    path.push(temp);
                    
                    while(temp.previous != null){
                        path.push(temp.previous);
                        temp = temp.previous;
                    }

                    
                    for(int i = 1; i < path.size() - 1; i++){
                        path.get(i).setCharacter('.');
                    }

                    printGrid(grid, cols, rows);
                    System.out.println("Done");
                    for(int i = 0; i < path.size(); i++){
                        System.out.println(path.get(i).getIJTuple());
                    }
                    break;
                }

                openSet.remove(openSet.indexOf(current));
                closedSet.push(current);

                Stack<Node> neighbors = current.getNeighbors();

                for(int i = 0; i < neighbors.size(); i++){
                    Node neighbor = neighbors.get(i);

                    if((!closedSet.contains(neighbor)) && (!neighbor.getWall())){
                        int tempG = current.g + 1;

                        if(openSet.contains(neighbor)){
                            if(tempG < neighbor.g){
                                neighbor.g = tempG;
                            }
                        } else {
                            neighbor.g = tempG;
                            openSet.push(neighbor);
                        }

                        neighbor.h = heuristic(neighbor, end);
                        neighbor.f = neighbor.g + neighbor.h;

                        neighbor.setPreviousNeighbor(current);

                    }
                    
                }

            } else {
                System.out.println("NO SOLUTION");
                break;
                //no solution
            }
        }
    }

    public static double heuristic(Node neighbor, Node end){
        int nJ = neighbor.getColumn();
        int nI = neighbor.getRow();
        int eJ = end.getColumn();
        int eI = end.getRow();
        double pythagoreanTheorem = Math.sqrt(Math.pow((eJ - nJ), 2) + Math.pow((eI - nI), 2));
        return pythagoreanTheorem;
    }

    public static void printGrid(Stack<Stack<Node>> grid, int cols, int rows){
      
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                grid.get(0).get(j).setCharacter('#');
                grid.get(0).get(j).wall = true;

                grid.get(i).get(0).setCharacter('#');
                grid.get(i).get(0).wall = true;

                grid.get(i).get(rows - 1).setCharacter('#');
                grid.get(i).get(rows - 1).wall = true;

                grid.get(cols - 1).get(j).setCharacter('#');
                grid.get(cols - 1).get(j).wall = true;

                System.out.print(grid.get(i).get(j).getCharacter());
                System.out.print(' ');

            }
            System.out.println();
        }
    }
}
