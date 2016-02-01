import java.util.ArrayList;
import java.util.List;

public class Cave {
	private int caveId;
	private List<Cave> exits;
	private Trap trap;
	private Creature occupant;

	public Cave(int caveId) {
		this.caveId = caveId;
		exits = new ArrayList<Cave>();
	}

	/**
	 * Add an exit cave
	 * @param exit
	 */
	public void addExit(Cave exit) {
		this.exits.add(exit);
	}

	public Cave[] getExits() {
		Cave[] exitArray = new Cave[this.exits.size()];

		int index = 0;
		for(Cave exit : this.exits) {
			exitArray[index++] = exit;
		}
		return exitArray;
	}


	public Cave getRandomExit() {
		int index = Dice.randInt(this.exits.size());
		return this.exits.get(index);
	}
	
	public String contentsString() {
		String ret = "roomId\t:" + this.caveId + "\n"
				+ "Trap\t:" + this.trap + "\n"
				+ "Creature:" + this.occupant + "\n";
		return ret;
	}

	public int getID() {
		return this.caveId;
	}
	
	public boolean hasOccupant() {
		return (this.occupant != null);
	}
	
	public boolean hasTrap() {
		return (this.trap != null);
	}
	
	public boolean setInitialOccupant(Creature occupant) {
		if (this.hasOccupant()) {
			return false;
		} else {
			this.occupant = occupant;
			occupant.setLocation(this);
			return true;
		}	
	}
	
	public boolean setTrap(Trap trap) {
		if (this.hasTrap()) {
			return false;
		} else {
			this.trap = trap;
			return true;
		}
	}

	public void requestOccupancy(Creature creature) {
		if (this.hasTrap() && !(creature instanceof Wumpus)) {
			this.trap.applyOn(creature);
		}

		if(this.hasOccupant() && this.occupant.isAlive()) {
			Creature winner = this.haveBattle(this.occupant, creature);
			if (this.occupant == winner) {
				System.out.println("Creature's move attempt failed:");
				System.out.println("MOVE " + creature + " failed");
				Log.println("MOVE " + creature + " failed");
			} else {
				System.out.println("Creature successfully moves to Cave:");
				System.out.println("MOVE " + creature + "is now at Cave" + this.getID());
				Log.println("MOVE " + creature + "is now at Cave" + this.getID());
				this.setOccupant(winner);				
			}
		} else {
			System.out.println("Creature successfully moves to Cave:");
			System.out.println("MOVE " + creature + "is now at Cave" + this.getID());
			Log.println("MOVE " + creature + "is now at Cave" + this.getID());
			this.setOccupant(creature);			
		}
	}

	private void setOccupant(Creature creature){
		this.occupant = creature;
		creature.setLocation(this);
	}

	private Creature haveBattle(Creature c1, Creature c2) {

		System.out.println("Battle begins:\n BATTLE " + c1 + " vs "+ c2);
		Log.println("BATTLE " + c1 + " vs "+ c2);

		do {
			System.out.println("Battle round:");
			Log.println("Battle round:");

			c1.receiveDamage(c2.attack());
			//change the attacker
			Creature temp = c1;
			c1 = c2;
			c2 = temp;
		}while(c2.isAlive());

		//out of the loop c2 is dead
		System.out.println("Battle complete:\n VICTOR " + c2);
		Log.println("VICTOR " + c2);
		return c1;
	}

	public void getWarnings() {
		for(Cave exit : this.exits) {
			if(exit.hasTrap()) {
				System.out.println(exit.trap.getWarning());
			}
			
			if(this.occupant instanceof Wumpus){
				System.out.println("You smell a wumpus");
			}
		}
	}

	public String toString() {
		return this.caveId + "";
	}
}
