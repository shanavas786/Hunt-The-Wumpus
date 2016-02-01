
public class SuperBat extends Trap{

	public SuperBat() {
		super("SuperBat");
	}

	@Override
	void applyOn(Creature creature) {
		System.out.println("Trap sprung/triggered:");
		System.out.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());
		Log.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());

		System.out.println("*flap* *flap* *flap*");

		Cave cave = creature.location.getRandomExit();

		cave.requestOccupancy(creature);
	}

	@Override
	String getWarning() {
		return "Bats nearby";
	}
}
