import java.util.Random;

public class Dice {

	/**
	 * A single Random Number Generator for sharing among all Dice
	 */
	static private Random rng = new Random();
	/**
	 * A random Integer routine, supplied for convenience.
	 * Generates a ransom integer on (0, N-1)
	 * @param N
	 * @return ransom integer
	 */
 	static public int randInt(int N){
		return rng.nextInt(N);
	}
	
	private int ndice;
	private int nsides;
	
	/**
	 * Construct Dice from a "Hit Dice" string format
	 * String has format "XdY" where X is number of dice
	 * and Y is number of sides on each die.
	 * If X is absent then it is taken as 1.
	 * Expect an Exception if the String deviates from this format.
	 * @param dstr
	 */
	public Dice(String dstr){
		//
		int dix = dstr.toLowerCase().indexOf("d");
		if (dix==0){
			ndice = 1;
		}else{
			ndice = Integer.parseInt(dstr.substring(0, dix).trim());
		}
		nsides = Integer.parseInt(dstr.substring(dix+1).trim());
	}
	public Dice(){
		ndice = 1;
		nsides = 6;
	}
	/**
	 * Dice copy constructor
	 * @param dold
	 */
	public Dice(Dice dold){  
		ndice = dold.ndice;
		nsides = dold.nsides;
	}
	public Dice(int side){
		ndice = 1;
		nsides = side;
	}
	public Dice(int nd, int ns){
		ndice = nd;
		nsides = ns;
	}
	
	/**
	 * return the number of dice used by this Dice object
	 * @return
	 */
	public int getNDice(){ return ndice;}
	/**
	 * return the number of sides of the dice used by this Dice object.
	 * @return
	 */
	public int getNSides(){ return nsides; }
	
	/**
	 * roll nDice dice, each of nSides, and return sum of the faces.
	 * @return  dice total.
	 */
	public int roll(){
		int sum = 0;
		for (int i=0; i<ndice; i++)
			sum += rng.nextInt(nsides)+1;
		return sum;
	}
	
	
	///////////////////////////////////////
	// test routine:
	
	public static void main(String[] args){
		
		int nd, ns;
		Dice D = null;
	
		//D = new Dice("d6");
		D = new Dice(2,6);
		
		nd = D.getNDice();
		ns = D.getNSides();
		System.out.println("testing " + nd + "d" + ns);
		int [] tally = new int[nd*ns+1];  // initialise?
		
		for(int i=0; i<200; i++)
			tally[D.roll()]++;
		int count = 0;
		for(int i=0; i<tally.length; i++){
			System.out.printf("%3d:  %3d",i,tally[i]);
			System.out.print(" ");
			for(int j=0; j<tally[i]; j++) System.out.print("*");
			System.out.println("");
			count += tally[i];
		}
		System.out.println("total " + count);
	}
}

