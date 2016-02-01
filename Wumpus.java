
public class Wumpus extends Creature {

	private boolean asleep;

	public Wumpus() {
		super("Wumpus", "8d8", "3d6");
		this.asleep = true;
	}

	@Override
	public void takeATurn() {
		if (this.asleep) {
			System.out.println(this.getName() + " can be heard snoring in the distance");			
		} else {
			Cave exit = this.location.getRandomExit();
			exit.requestOccupancy(this);
			this.asleep = true;
		}
	}

	@Override
	public void receiveDamage(int damage) {
		this.asleep = false;
		super.receiveDamage(damage);
	}

	@Override
	public int attack() {
		int score = this.battleDice.roll();
		System.out.print(this + " inflicts " + score + " damage on ");
		Log.print(this + " inflicts " + score + " damage on ");
		return score;
	}
}
