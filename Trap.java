
public abstract class Trap {

	protected String name;
	protected boolean isArmed;
	
	public Trap(String name) {
		this.name = name;
		this.isArmed = true;
	}

	protected void disArm() {
		this.isArmed = false;
	}

	public String toString() {
		return this.name;
	}

	//applies trap on a creature
	abstract void applyOn(Creature creature);
	
	abstract String getWarning();
}
