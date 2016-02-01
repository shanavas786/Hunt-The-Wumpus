
public abstract class Creature {

	protected int hitPoints;
	protected Dice battleDice;
	protected String name;
	protected Cave location;

	public Creature(String name, String hp, String battleDice) {
		this.name = name;
		this.hitPoints = (new Dice(hp)).roll();
		this.battleDice = new Dice(battleDice);
	}

	public static Creature[] makeMenagerie(int inpc, int pl){
		int zooLength = inpc + pl + 1;
		Creature[] zoo = new Creature[zooLength];

		//create NPCs
		for (int i=0; i<inpc; i++) {
			zoo[--zooLength] = new NPC("NPC_" + (i + 1));
		}

		//create Players
		for (int i=0; i<pl; i++) {
			zoo[--zooLength] = new Player("Player_" + (i + 1));
		}

		//Create Wumpus
		zoo[--zooLength] = new Wumpus();

		return zoo;
	}

	public String getName() {
		return this.name;
	}
	public boolean isAlive() {
		return this.hitPoints > 0;
	}
	
	public abstract void takeATurn();

	public String toString() {
		return this.name + " (HP:" + this.hitPoints + ")";
	}
	
	public void setLocation(Cave cave) {
		this.location = cave;
	}

	public void die() {
		this.hitPoints = 0;
		this.location = null;
		System.out.println("Creature dies:");
		Log.println("DEATH " + this.getName());
	}

	public void receiveDamage(int damage) {
		this.hitPoints -= damage;
		System.out.println(this);
		Log.println(this.toString());

		if(this.hitPoints <= 0) {
			this.die();
		}
	}

	abstract public int attack();

}
