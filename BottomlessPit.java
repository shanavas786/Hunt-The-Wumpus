
public class BottomlessPit extends Trap {
	public BottomlessPit(){
		super("BottomlessPit");
	}

	@Override
	void applyOn(Creature creature) {
		System.out.println("Trap sprung/triggered:");
		System.out.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());
		Log.println("TRAP " + this + " in Cave " + 
							creature.location + " sprung by " + creature.getName());
		System.out.println("AAAAaaaahhhh....");
		creature.die();
	}

	@Override
	String getWarning() {
		return "You feel a draft";
	}
}
