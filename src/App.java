//                  PSEUDOCODE LINK ON WIKIPEDIA
// https://en.wikipedia.org/wiki/A*_search_algorithm#Pseudocode

//                 Gif Animated
//https://upload.wikimedia.org/wikipedia/commons/c/c2/Astarpathfinding.gif



//               FINDING OPTIMAL PATH OF A MAZZLE, STACK IMPLEMENTATION
// ****IF THE START OR THE END is blocked there is no way out

import java.util.Stack;

public class App {
    public static void main(String[] args) throws Exception {

        
        Stack<Stack<Node>> matrix = new Stack<Stack<Node>>();

        var cols = 20;
        var rows = 20;

        //Stack of Stacks<Nodes>
        for(int i = 0; i < cols; i++){
            matrix.push(new Stack<Node>());
        }

        //
        Stack<Node> openSet = new Stack<Node>();
        Stack<Node> closedSet = new Stack<Node>();

        Node start;
        Node end;

        //Initialization of all matrix nodes
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                matrix.get(i).push(new Node(' ', i, j));
            }
        }

        //Each node can get up to 4 neighbors
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                matrix.get(i).get(j).addNeighbors(matrix, cols, rows);
            }
        }

        //Start pos
        start = matrix.get(1).get(1);
        start.setCharacter('S');
        start.obstacle = false;

        //End pos
        end = matrix.get(rows - 2).get(cols - 2);
        end.setCharacter('E');
        end.obstacle = false;

        printMatrix(matrix, cols, rows);
        System.out.println();
        System.out.println();

        //push start node wherever it is
        openSet.push(matrix.get(start.getColumn()).get(start.getRow()));
        

        boolean loop = true;

        while(loop){

            //stack of nodes to evaluate
            if(openSet.size() > 0){

                int optimal   = 0;

                for(int i = 0; i < openSet.size(); i++){
                    if(openSet.get(i).fScore < openSet.get(optimal).fScore){
                        optimal  = i;
                    }
                }

                Node current = openSet.get(optimal);

                // Getting optimal path
                if(current.equals(end)){
                    Stack<Node> path = new Stack<Node>();
                    Node temp = current;

                    path.push(temp);
                    //getting all parents nodes
                    while(temp.camefrom != null){
                        path.push(temp.camefrom);
                        temp = temp.camefrom;
                    }

                    //mark evaluated nodes
                    markEvaluatedNodes(closedSet);


                    for(int i = 1; i < path.size() - 1; i++){
                        path.get(i).setCharacter('.');
                    }
                    

                    //final matrix result
                    printMatrix(matrix, cols, rows);

                    //Print optimal path stack nodes(x,y)
                    for(int i = 0; i < path.size(); i++){
                        System.out.println(path.get(i).getIJTuple());
                    }
                    break;
                } // end of getting optimal path


                //evaluated node get out
                openSet.remove(openSet.indexOf(current));

                //evaluated node get in
                closedSet.push(current);

                Stack<Node> neighbors = current.getNeighbors();

                for(int i = 0; i < neighbors.size(); i++){
                    Node neighbor = neighbors.get(i);
                    
                    //check if it was not evaluated and if it is not a obstacle
                    if((!closedSet.contains(neighbor)) && (!neighbor.getObstacle())){
                        int tempG = current.gScore + 1;
                        
                        if(openSet.contains(neighbor)){
                            //set new optmal f score
                            // link parent node
                            if(tempG < neighbor.gScore){
                                neighbor.gScore = tempG;
                                neighbor.h = heuristic(neighbor, end);
                                neighbor.fScore = neighbor.gScore + neighbor.h;
                                neighbor.setCameFrom(current);
                            }
                        } else {
                            //set new f score
                            // link parent node
                            neighbor.gScore = tempG;
                            neighbor.h = heuristic(neighbor, end);
                            neighbor.fScore = neighbor.gScore + neighbor.h;
                            neighbor.setCameFrom(current);
                            openSet.push(neighbor);
                        }
                        
                    } // end of node check
                    
                }

            } else {
                System.out.println("No way out");
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

    public static void printMatrix(Stack<Stack<Node>> grid, int cols, int rows){
      
        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){

                //borders
                grid.get(0).get(j).setCharacter('#');
                grid.get(0).get(j).obstacle = true;

                grid.get(i).get(0).setCharacter('#');
                grid.get(i).get(0).obstacle = true;

                grid.get(i).get(rows - 1).setCharacter('#');
                grid.get(i).get(rows - 1).obstacle = true;

                grid.get(cols - 1).get(j).setCharacter('#');
                grid.get(cols - 1).get(j).obstacle = true;
                //end borders

                System.out.print(grid.get(i).get(j).getCharacter());
                System.out.print(' ');

            }
            System.out.println();
        }
    }
                
    public static void markEvaluatedNodes(Stack<Node> closedSet){
        for(int i = 0; i < closedSet.size(); i++){
            if(!(closedSet.get(i).getCharacter() == 'S') && (!(closedSet.get(i).getCharacter() == 'E'))){
                closedSet.get(i).setCharacter('-');
            }
        }
    }  
}
