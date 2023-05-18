package resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ResourceLoader {
	
	static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String fileName) {
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("Images/" + fileName));
	}
	
	public static URL getAudio(String fileName) {
		return rl.getClass().getResource("Audio/" + fileName);
	}
}

