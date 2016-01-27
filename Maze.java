/**
 * MAZE 
 * 
 * Manage a list of Caves and allow links to be made between individual Caves.
 * 
 * Creation of Mazes is done via static member functions.  One cannot make a Maze 
 * via a constructor call except within a static member of this class.  In order to
 * construct Mazes of your own design, you must write a static member function here.
 * 
 * This class assumes that the classes Cave, Creature and Trap exist, 
 * and assumes the existence of the following member functions of Cave:
 * 
 * 		Cave.Cave(int i)
 * 			Cave constructor.  Makes a new empty Cave with ID number i.
 * 
 * 		boolean Cave.addexit(Cave c)
 * 			adds Cave c to the list of this Cave's exits.
 * 			return true if c was added successfully, or was already on the exit list.
 * 			return false if c couldn't be added (eg this' list of exits is full)
 * 
 * 		String Cave.contentsString()
 * 			provides info about the Cave and its contents as a String.
 * 			This should be the Cave's ID number and a list of exits,
 * 				followed by the name of any occupying Creature on a new line,
 * 				followed by the type of any traps on a new line.
 * 			(see the sample run & Log to see how the Maze is printed out)
 * 			HINT:  write a Cave.toString() method to print the first line (ID + exits).
 * 				   also write toString methods for Creature and Trap
 * 				   and use all of these to help write Cave.contentsString() 
 *  
 *  	int Cave.getID()
 *  		return the ID number of the Cave
 *  
 *  	boolean Cave.hasOccupant()
 *  		true if the Cave contains a Creature, false if not
 *  
 *  	boolean Cave.hasTrap()
 *			true if the Cave contains a Trap, false if not
 *
 *  	boolean Cave.setTrap(Trap t)
 *  		If this Cave has no trap then set its trap to t and return true
 *  		Otherwise return false.
 *
 *		public boolean Cave.setInitialOccupant(Creature creature)  
 *			Setup initial occupancy of Cave (and Creature location)
 * 			If Cave is unoccupied AND Creature as no location,
 *   			then setup occupant of Cave and location of Creature accordingly,
 *   			and return true.
 * 			Return false if either Cave had occupant or Creature had location.
 * 
 * 
 *
 */
public class Maze {

	// Each of these static members generates a different Maze.  
	// Try them all, or write your own!
	
	/**
	 * Generates a square maze of 4 caves
	 * @return  the Maze
	 */
	public static Maze SquareMaze(){
		return HypercubeMaze(2);
	}
	/** Generates a cubic maze of 8 rooms
	 * @return  the Maze
	 */
	public static Maze CubeMaze(){
		return HypercubeMaze(3);
	}
	/** 
	 * Generates a tesseract (4-D hypercube) maze of 16 rooms
	 * @return  the Maze
	 */
	public static Maze TesseractMaze(){
		return HypercubeMaze(4);
	}
	/**
	 * Generate a Hypercubic Maze of 2, 3 or 4 dimensions
	 * @param dim  2,3 or 4
	 * @return The Maze object
	 */
	public static Maze HypercubeMaze(int dim){
		// this should work for any dims, 
		// However limiting to 4 'coz we constrain max #exits per room.
		if (dim>4) throw new RuntimeException("dim too large");
		int nnodes = 2<<(dim-1);
		Maze M = new Maze(nnodes);
		for (int i=0; i<nnodes; i++){
			for (int j=0; j<dim; j++){
				M.join(i, i^(1<<j));
			}
		}
		return M;
	}
	/**
	 * Generate the canonical Wumpus Maze of 20 rooms, each having 3 exits
	 * @return  the Maze object
	 */
	public static Maze DodecahedralMaze(){  
		Maze M = new Maze(20);
		int[][] pts = {{1,2},{1,5},{1,0},{2,3},{2,18},{3,4},{3,16},
				{4,5},{4,14},{5,6},{6,7},{6,13},{7,8},{7,0},
				{8,9},{8,12},{9,10},{9,19},{10,11},{10,17},
				{11,12},{11,15},{12,13},{13,14},{14,15},
				{15,16},{16,17},{17,18},{18,19},{19,0}};
		for(int i=0; i<pts.length; i++)
			M.join2(pts[i][0], pts[i][1]);
		return M;
	}
	/**
	 * Generate a Maze being a complete graph of 5 nodes
	 * Each Cave connects to every other Cave.
	 * @return  the Maze object.
	 */
	public static Maze K5Maze(){ 
		Maze M = new Maze(5);
		for(int i=0; i<M.nRooms(); i++)
			for(int j=i+1; j<M.nRooms(); j++)
				M.join2(i, j);
		return M;
	}
	
	
	// WRITE YOUR OWN MAZE!
	/*
	public static Maze MyOwnMaze(){
		
		// Create the Caves
		int nrooms = X;  // or pass this in as a parameter
		Maze M = new Maze(nrooms);
		
		// join the Caves
		// ... one-way tunnels like this:
		M.join(from, to);
		// ... and use join2 for two-way tunnels:
		M.join2(from, to);
		// use as many of the above statements as you need.
		
		// finally return the Maze
		return M;
		// this will need to be populated with Creatures and Traps
		// or the game will be a bit dull!
	}
	*/

	///////////////////////////////////////////////////////////////
	
	// an array to store each Cave in the Maze.
	// NOTE that the ID number of a Cave need not correspond to its index in the array.
	// To retrieve a cave with a given ID use the getCaveID() member,
	// DON'T rely on the array index being the same as the ID!
	private Cave[] rooms;
	
	/**
	 * construct a Maze of x dead-end Caves (no exits yet).
	 * This constructor is PRIVATE so Mazes cannot be created directly.
	 * They can only be constructed via one of the static members 
	 * (CubicMaze, DodecahedralMaze, etc)
	 * @param x
	 */
	private Maze(int x){
		rooms = new Cave[x];
		for (int i=0; i<rooms.length; i++){
			rooms[i] = new Cave(i);
		}
	}
	
	/**
	 * return the number of Caves in this Maze
	 * @return  
	 */
	public int nRooms(){
		return rooms.length;
	}

	
	/**
	 * specify one Cave as an exit from another Cave.
	 * 
	 * This requires Cave.addExit(Cave)
	 * 
	 * @param from
	 * @param to
	 */
	private void join(Cave from, Cave to)
	{
		from.addExit(to);
	}
	private void join(int ixFrom, int ixTo)
	{
		this.join(rooms[ixFrom],rooms[ixTo]);
	}
	/** 
	 * specify two Caves as mutual neighbours;  
	 * Each is an exit of the other.
	 * 
	 * This requires Cave.addExit(Cave)
	 * 
	 * @param from
	 * @param to
	 */
	private void join2(Cave from, Cave to)
	{
		from.addExit(to);
		to.addExit(from);
	}
	private void join2(int ixFrom, int ixTo){
		this.join2(rooms[ixFrom], rooms[ixTo]);
	}
	
	/** 
	 * Format Maze as a String by formatting contents of each Cave in turn.
	 *
 	 * Requires Cave.contentsString()
 	 * 
 	 * @return the String. 
	 */
	public String toString(){
		String ret = "";
		for(int i=0; i<rooms.length; i++){
			ret += rooms[i].contentsString();
		}
		return ret;
	}
	
	// Access to Caves
		
	/**
	 * Return the room element which has the given ID 
	 * (the ID might not be the same as the index of rooms)
	 * or return null if no room has that ID.
	 * 
	 * Requires Cave.getID()
	 * 
	 * @param ID
	 * @return Cave having that ID
	 */
	Cave getCaveID(int ID){
		for(int i=0; i<rooms.length; i++)
			if (rooms[i].getID()==ID) return rooms[i];
		return null;
	}
	
	/**
	 * Choose a Cave from rooms at random.
	 * @return  the Cave
	 */
	private Cave getRandomCave(){
		int ix = Dice.randInt(rooms.length);
		return rooms[ix];
	}
	/**
	 * Return a randomly chosen Cave with no occupant or trap.
	 * Or return null if no such Cave found.
	 * 
	 * This requires Cave.hasOccupant()  and Cave.hasTrap()
	 * 
	 * @return	the Cave
	 */
	private Cave getRandomEmptyCave(){
		int count = 0;
		Cave cv;
		boolean OK, test;
		// pick Cave at random until we get an empty one, or until we give up
		do{
			cv = getRandomCave();
			test =  cv.hasOccupant() || cv.hasTrap();
			OK = (++count<10*rooms.length);
		}while (test & OK); 
		if (test)
			return null;
		else
			return cv;
	}
	
	/**
	 * add each Creature in an array to the Maze, at randomly chosen locations.
	 * 
	 * This requires Cave.setInitialOccupant(Creature)
	 *
	 * @param cr 	array of Creatures
	 * @return		true if ALL creatures successfully added, else false
	 */
	public boolean populateMaze(Creature[] cr){
		boolean OK = true;
		for(int i=0; i<cr.length; i++){
			Cave space = getRandomEmptyCave();
			//OK &= cr[i].moveToNewCave(space);
			OK &= space.setInitialOccupant(cr[i]);
		}
		return OK;
	}
	/**
	 * add one trap to the Maze at a random location.
	 * 
	 * This requires Cave.setTrap(Trap);
	 * 
	 * @param atrap  the Trap
	 * @return 		true if successful
	 */
	public boolean addATrap(Trap atrap){
		Cave c = getRandomEmptyCave();
		if (c!=null){
			c.setTrap(atrap);
			return true;
		} else return false;
	}
	/**
	 * add a variable number of traps to the Maze, separated by commas:
	 *    addTraps(trap1, trap2, trap3, ...etc )
	 *    
	 * @param traps  variable-length array of Traps, i.e. Trap...
	 * @return 	true if all traps successfully added.
	 */
	public boolean addTraps(Trap...traps ){
		boolean OK = true;
		for(int i=0; i<traps.length; i++)
			OK &= addATrap(traps[i]);
		return OK;
	}
	
}
