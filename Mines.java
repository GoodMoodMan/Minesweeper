package mines;

// imports
import java.util.ArrayList;
import java.util.Random;

// The Mines class representing our minesweeper game
public class Mines {
	// saves int values for size of board, number of mines
	// matrix of nodes (created class representing a single spot in the board)
	// boolean value showall
	private int height,width,numMines;
	protected node map[][];
	private boolean showall;
	// constructor getting the size and mine number as input
	// creates a matrix of nodes based on size
	// calls random alloc method
	public Mines(int height, int width, int numMines) {
		this.height=height;
		this.width=width;
		this.numMines=numMines;
		this.map=new node[height][width];
		for (int i=0;i<height;i++) 
			for (int j=0;j<width;j++) 
				map[i][j]=new node(i,j);
		this.random_alloc();
	}
	// the add mines method adds a mine to the selected i,j coordiantes
	// returns false if the mine could not be places (out of range or already mine there)
	public boolean addMine(int i, int j) {
		if (i<height&&j<width&&!map[i][j].mine) {
			map[i][j].mine=true;
			return true;
		}
		return false;
	}
	// the open method opens the selected i,j coordiantes
	// based on mine sweeper rules
	public boolean open(int i, int j) {
		boolean f = false;
		ArrayList<node> arr;
		// if the coordiantes are legal based on game size
		if (is_legal(i,j)) {
			// if already opened, returns false
			if (map[i][j].open)
				return false;
			// if there's no mine
			if (!map[i][j].mine) {
				// opens the selected place
				map[i][j].open=true;
				// checks neighbors of selected place using the node.neighbors method
				// if all neighbors don't have mines, call open on each of them
				arr=map[i][j].neighbors();
				for(node e : arr) {
					if (e.mine)
						f=true;
				}
				if (!f) {
					for(node e : arr) {
						open(e.i, e.j);
					}
				}

				return true;
			}
			
		}
		
		return false;
	}
	// toggle flag method toggles the flag boolean value of selected place
	public void toggleFlag(int x, int y)  {
		if (map[x][y].flag) {
			map[x][y].flag=false;
			return;
		}
		map[x][y].flag=true;
	}
	// isDone method returns true if the game is finished (all non mine locations are open)
	public boolean isDone() {
		boolean f=false;
		for (int i=0;i<height;i++) 
			for (int j=0;j<width;j++) 
				if (!map[i][j].mine&&!map[i][j].open)
					f=true;
		if (!f) 
			return true;
		return false;
	}
	// returns a string form of the selection location
	public String get(int i, int j) {
		return map[i][j].toString();
	}
	// sets showall value to true (get method shows all locations as of they are open)
	public void setShowAll(boolean showAll) {
		this.showall=showAll;
	}
	// to string method returns a string form of the whole board
	public String toString() {
		String str="";
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				str+=this.get(i, j);
			}			
			str+="\n";
		}
		return str;
		
				
	}
	// private boolean is_legal method returns true if input coordiantes are legal based on game size
	private boolean is_legal(int i,int j) {
		if (i<height&&j<width&&i>=0&&j>=0) 
			return true;
		return false;
	}
	// private random alloc method randomly allocates mines across the game board based on nummines
	// around 10% probability for each node until all mines are places
	private void random_alloc() {
		Random r = new Random();
		int count=0;
		for (int i=0;i<height;i++) {
			for (int j=0;j<width;j++) {
				if (r.nextInt(100)<10&&count<numMines) {
					this.map[i][j].mine=true;
					count++;
				}
				
			}
		}
	}
	// node class represents a single location on board
	public class node {
		// saves i,j coordiantes
		// 3 flags for flag,mine,open
		int i,j;
		protected boolean flag;
		protected boolean mine;
		protected boolean open;
		
		// constructor getting i,j as inputs
		public node(int i, int j) {
			this.i=i;
			this.j=j;
		}
		// the neighbors method returns an arraylist of nodes, consisting of the legal neighbors of current node
		public ArrayList<node> neighbors() {
			ArrayList<node> arr = new ArrayList<node>(); 
			// checks every possible neighbor if legal (using is_legal method)
			// if legal, adds to arr
			for (int n=-1;n<2;n++) 
				for(int m=-1; m<2;m++) {
					if (is_legal(i+n,j+m)&&!(m==0&&n==0)) {
						arr.add(map[i+n][j+m]);
					}
				}
			return arr;
		}
		// overridden toString method returns a string form of the current node
		// based on mine sweeper rules
		@Override
		public String toString() {
			int count = 0;
			// if node is not open and showall is false
			if (!open&&!showall) {
				// if node has flag return F, else return .
				if(flag) 
					return "F";
				return ".";
			}
			// if node is open and has mine return X
			if (mine)
				return "X";
			// else, return the count of mined neighbors to current node
			// if count is 0 return blank
			for(node e : this.neighbors()) {
				if (e.mine)
					count++;
			}
			if (count==0)
				return " ";
			return String.valueOf(count);
			
		}
	}

}
