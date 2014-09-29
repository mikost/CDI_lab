package eap63class;

import java.io.FileWriter;
import java.io.IOException;

public class MikkosLogger {

	public static void log(String string) {
		FileWriter fw = null; 
		try {
			fw = new FileWriter("/dev/pts/2");
			fw.append(Thread.currentThread() +":"+ string);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
