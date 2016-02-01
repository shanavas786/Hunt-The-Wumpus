
public class NPC extends Creature {
	public NPC(String name) {
		super(name, "7d7", "2d6");
	}

	
	public void takeATurn() {
		Cave exit = this.location.getRandomExit();
		exit.requestOccupancy(this);
	}

	@Override
	public int attack() {
		int score = this.battleDice.roll();
		System.out.print(this + " inflicts " + score + " damage on ");
		Log.print(this + " inflicts " + score + " damage on ");
		return score;
	}

}
