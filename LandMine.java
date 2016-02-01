
public class LandMine extends Trap {
	private Dice battleDice;
	public LandMine(){
		super("LandMine");
		this.battleDice = new Dice("2d6");
	}

	@Override
	void applyOn(Creature creature) {
		System.out.println("Trap sprung/triggered:");
		System.out.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());
		Log.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());

		if(this.isArmed) {
			System.out.println("Click... BOOM");
			creature.receiveDamage(this.battleDice.roll());
		} else {
			System.out.println("Trap Ineffective");
			Log.println("Trap Ineffective");
		}
		//TODO
	}

	@Override
	String getWarning() {
		return "You smell explosives";
	}
}
