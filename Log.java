
/**
 * INSERT YOUR CODE WHERE INDICATED.
 */

//import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	private static FileWriter log = null;
	public enum OUTTO {FILE,CONSOLE,NULL};
	private static OUTTO mode = OUTTO.FILE;

	private static String filename = "TestLog.txt";

	static {
		try {
			log = new FileWriter(filename);
		} catch (IOException ex) {
			mode = OUTTO.CONSOLE;
		}
	}

	public static void println(String output) {
			print(output+"\n");
	}


	public static void print(String output) {
		if (mode==OUTTO.FILE) { 
			try{
				log.write(output);
			} catch (IOException ex) {
				mode = OUTTO.CONSOLE;
			}
		} else if (mode==OUTTO.CONSOLE) {
			System.out.print(output);
		}  // else do nothing
	}

	public static void close() {
		try {
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setFileOut() {
		//fileOut = true;
		mode = OUTTO.FILE;
	}
	
	public static void setConsoleOut() {
		//fileOut = false;
		mode = OUTTO.CONSOLE;
	}
	public static void setNullOut(){
		mode = OUTTO.NULL;
	}
}

