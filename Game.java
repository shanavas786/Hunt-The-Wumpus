/**
 * HUNT THE WUMPUS
 * 
 * Game is a wrapper class for a main routine which runs a game of Wumpus.
 * 
 * The following member functions of Cave, Creature, Trap and Log are used:
 * 
 * 		Log.setFileOut();   // or Log.setNullOut() or Log.setConsoleOut()
 * 		Log.println(String);
 * 
 *		Maze.DodecahedralMaze();  
 *			(you can plug in other Maze generators as you desire)
 *
 * 		Maze.populateMaze(Creature[])
 *			Locates each Creature randomly within the Maze
 *
 *		Maze.addATrap(Trap)   and   Maze.addTraps(Trap...)
 *			adds Traps to random empty rooms in the Maze
 *
 *		Creature[] Creature.makeMenagerie(int nNPC, int nPlayer)
 *			a STATIC method of Creature which returns an array of Creatures,
 *			containing the required number of NPC and human Players, 
 *			plus one Wumpus.
 *			You may want to write your own Menagerie routine when you add more
 *			subclasses of Creature
 *
 *		boolean Creature.isAlive()
 *
 *		void Creature.takeATurn()
 *			Make the Creature take its turn.
 *			Exactly what this does will depend entirely upon what kind of Creature this is
 *				(so this member should be declared as ... ? )
 *			In general the turn might consist of choosing a destination to move to
 *				and working with the chosen Cave to effect the move, handling any special
 * 				abilities or behaviours of the specific Creature, etc.
 * 
 * 		Superbat.Superbat()
 * 		BottomlessPit.BottomlessPit()
 * 		LandMine.LandMine()
 * 			Constructors of the three individual Trap types.
 * 			(you will add more trap types when you get to the Extension work,
 * 			 and will want to use their constructors here too)
 * 
 * 
 *
 */
public class Game {

	
	public static void main(String[] args) {
	
		// Direct the Logger to output to file
		Log.setFileOut();
		// or to turn off Logging,  Log.setNullOut();
		

		// Create a Maze using one of the supplied routines
		Maze M = Maze.DodecahedralMaze();
		//Maze M = Maze.K5Maze();
		
		// Create an array of Creatures
		Creature[] zoo = Creature.makeMenagerie(3, 1);
		//Creature[] zoo = Creature.makeMenagerie(0, 1);
		
		// Assign Creatures to empty rooms in the Maze at random
		boolean OK = M.populateMaze(zoo);  
		if (!OK) throw new RuntimeException("could not squeeze all Creatures into Maze.");
		
		// Add traps to empty rooms
		if (true){
			OK = M.addATrap(new SuperBat());
			OK = OK & M.addTraps(new BottomlessPit(), new LandMine(), new LandMine(), new SuperBat());
			if (!OK) throw new RuntimeException("could not seed all Traps into Maze.");
		}
		
		// Output initial layout to screen
		System.out.println("initial layout:\n" + M);
		// also write it to Log file
		Log.println("MAZE:\n" + M);

		// Main loop:
		int nPlayed, nAlive;
		int rnum = 1;
		do{
			nPlayed = 0;
			// Make each Creature take a turn, if still alive.
			for(int i=0; i<zoo.length; i++){
				if ((zoo[i]!=null) & zoo[i].isAlive()){
					System.out.println("\nTurn: " + zoo[i]);//.getName());
					Log.println("TURN: " + zoo[i]);//.getName());
					zoo[i].takeATurn();
					nPlayed++;
				}
			}
			nAlive = 0;
			// loop again to count survivors; Some might've died since playing
			for(int i=0; i<zoo.length; i++){
				nAlive += ((zoo[i]!=null) & zoo[i].isAlive())?1:0;
			}
			// notify end of round to Players, and write to Log.
			System.out.println("\nROUND " + (rnum++) + " COMPLETE.  " + nAlive + " Creatures remain.");
			Log.println("ROUND complete, " + nPlayed + " Creatures had turns, " + nAlive + " survived.");
		} while (nAlive>1);

		// One (or zero!) Creatures remain;  end game.
		System.out.println("Game Over.");
		Log.close();
	}
}

