
public class Puzzle {
	static int size = 7;
	static int[][] board = new int[size][size];
	static int moveTimes = 0;
	
	//initialize board, 4 elements at each corner are empty
	//other elements will be 1 at beginning
	//the one in center: board[4][4] is 0 at beginning
	public Puzzle(){
		//initialize board, 4 elements at each corner are empty
	board[0][0] = -1;
	board[1][0] = -1;
	board[0][1] = -1;
	board[1][1] = -1;
	
	board[0][5] = -1;
	board[0][6] = -1;
	board[1][5] = -1;
	board[1][6] = -1;
	
	board[5][0] = -1;
	board[5][1] = -1;
	board[6][0] = -1;
	board[6][1] = -1;
	
	board[5][5] = -1;
	board[6][5] = -1;
	board[5][6] = -1;
	board[6][6] = -1;
	
	//initialize other elements to 1. check each element before assign value to it
	//if the value is -1, do nothing. otherwise assign 1 to the element
	for(int i=0;i<size;i++){
		for(int j=0;j<size;j++){
			if(board[i][j]!=-1){
				board[i][j]=1 ;
			}
		}
	}//end of for loop
	
	//assign 1 to the element in the center of board
	board[size/2][size/2]=0;
	}//end of constructor
	
	
	//looping this matrix and print out every element in board
	//value == -1: print empty space
	//value == 0: print o
	//value == 1: print x
	public void printBoard(){
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				switch(board[i][j]){
				case -1: System.out.print(" ");
				break;
				case 0:  System.out.print("O");
				break;
				case 1:  System.out.print("X");
				break;
				}
			}
			System.out.print("\n");
		}
		System.out.println();
	}//end of print method
	
	/* This is a recursive method that keep converting 1 to 0 at each direction until hit the boundary
	 * precondition: solution haven't been found
	 * postcondition: keep going at one direction, until the operation cannot be done
	 * Backtrack: shut down one way, and change into another direction and continue search
	 */
	public boolean findSolution(){
		
		//System.out.println("move: "+move);
		 
		if((conditionF())) return true;//return true if hit the condition 
	
			for(int i=0; i<size;i++){
				for(int j=0;j<size;j++){
					 for(int d=1;d<5;d++){//try 4 directions
						
					     if(movable(i,j,d)==d){
						    move(i,j,d);
						moveTimes++;
						printBoard();
						 
						         
						              if(findSolution()){
						            	  //System.out.println("move: "+move);
							                    return true;
						}else{
							 back(i,j,d);
							 printBoard();
						}
						
						}//end of jump condition
						
					 }//end of direction searching loop
				
					}//end of y	
				 }//end of x	
			 
		   
		
		
		return false;
	}
	
	/* move methods with direction:
	 * set next two vertices to 0
	 * set current vertex to 1
	 */
	public void move(int i, int j, int d){
		int temp = board[i][j];
		if(d==3){//move to the up
		    board[i-1][j] = 0;
		    board[i][j] = board[i-2][j];
		    board[i-2][j] = temp; 
		    
		}else if(d==4){//move to the down
			board[i+1][j] = 0;
			board[i][j] = board[i+2][j];
			board[i+2][j] = temp;
			
		}else if(d==1){//move to the left
		    board[i][j-1] = 0; 
		    board[i][j] = board[i][j-2]; 
		    board[i][j-2] = temp; 
		   
		}else if(d==2) {//move to the right
			board[i][j+1] = 0;
			board[i][j] = board[i][j+2] ;
			board[i][j+2] = temp;
			
		}
	}
	
	
	
	//check the matrix that determine solution had been found or not
	//two conditions: 
	//1: all the elements that will show on screen, should be O
	//2: if the method check the center, it should fulfill the following condition
	public boolean conditionF(){
		for(int i=0; i<size;i++){
			for(int j=0;j<size;j++){
				if(i!=size/2 && j!=size/2 ){//condition 1: all the elements that will show on screen, should be O 
					if( board[i][j]>0){
						return false;
						}
				}else if(i==size/2 && j==size/2){//if the method check the center, it should fulfill the following condition
					if(board[i][j] != 1)
						return false;
				}
			}
		}
		return true; // everything is fine, return true
	}
	
	//check the current spot is movable to a certain direction
	//there are two conditions:
	//1: index is in bound(including the corner that cannot be touched
	//2: there are some pegs that can be removed at the certain direction
	//return 1 2 3 4 that stand for left right up down
	//return 0 if the spot is no where to go, and -1 means boundary
	public int movable(int x, int y){
		if(board[x][y] == -1){//Boundary elements
			return -1;
		}else if(y-2>=0 && board[x][y]==1 && board[x][y-2]==0 && board[x][y-1]==1 && board[x][y]!=-1){//check left
			return 1;
		}else if(y+2<size && board[x][y]==1 && board[x][y+2]==0 && board[x][y+1]==1 && board[x][y]!=-1){//check right
			return 2;
		}else if(x-2>=0 && board[x][y]==1 && board[x-2][y]==0 && board[x-1][y]==1 && board[x][y]!=-1){//check up
			return 3;
		}else if(x+2<size && board[x][y]==1 && board[x+2][y]==0 && board[x+1][y]==1 && board[x][y]!=-1){//check down
			return 4;
		}else{
			return 0;
		}
	}
	
	//check current spot at certain direction
	public int movable(int x, int y, int d){
		switch(d){
		case 1: if(y-2>=0 && board[x][y]==1 && board[x][y-2]==0 && board[x][y-1]==1 && board[x][y]!=-1){//check left
			return 1;
		}
		case 2: if(y+2<size && board[x][y]==1 && board[x][y+2]==0 && board[x][y+1]==1 && board[x][y]!=-1){//check right
			return 2;
		}
		case 3: if(x-2>=0 && board[x][y]==1 && board[x-2][y]==0 && board[x-1][y]==1 && board[x][y]!=-1){//check up
			return 3;
		}case 4: 
			if(x+2<size && board[x][y]==1 && board[x+2][y]==0 && board[x+1][y]==1 && board[x][y]!=-1){//check down
			return 4;
		}
	    default: return 0;
		}
	}
	
	//backtrack step, reverse the move step before if failed complete the whole
	//4numbers stand for 4 cases: left, right, up, down 
	public void back(int x, int y, int d){
		switch (d){
		case 1://back from left
			board[x][y]=1;
			board[x][y-1]=1;
			board[x][y-2]=0;
			break;
		case 2://back from right
			board[x][y]=1;
			board[x][y+1]=1;
			board[x][y+2]=0;
			break;
		case 3://back from up
			board[x][y]=1;
			board[x-1][y]=1;
			board[x-2][y]=0;
			break;
		case 4://back from down
			board[x][y]=1;
			board[x+1][y]=1;
			board[x+2][y]=0;
			break;
		
		}
	}
	
	//main method
	public static void main(String[] args){
		Puzzle p = new Puzzle();
		p.printBoard();
		System.out.println(p.findSolution());
		System.out.println("Used "+moveTimes+" times to find the solution.");
	}
	

}
