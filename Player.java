import java.util.Scanner;

public class Player extends Creature {

	private static Scanner scanner = new Scanner(System.in);

	public Player(String name) {
		super(name, "5d5", "5d5");
	}

	@Override
	public void takeATurn() {
		System.out.print(this + " You are in " + this.location.getID() + ", Exits ");
		Cave[] exits = this.location.getExits();

		for(Cave cave : exits) {
			System.out.print(cave.getID() + " ");
		}
		System.out.println();

		this.location.getWarnings();

		Cave moveTo = this.getMoveToCave(exits);
		moveTo.requestOccupancy(this);
	}

	@Override
	public int attack() {
		int score = this.battleDice.roll();
		System.out.print(this + " inflicts " + score + " damage on ");
		Log.print(this + " inflicts " + score + " damage on ");
		return score;
	}
	

	private Cave getMoveToCave(Cave[] exits){
		Log.println("Move to where ? :");

		int input = scanner.nextInt();

		for(Cave cave : exits) {
			if (cave.getID() == input) {
				return cave;
			}
		}
		System.out.println("Invalid input");
		return null;
	}

}
