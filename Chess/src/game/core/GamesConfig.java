package game.core;

import java.io.File;
import java.io.IOException;

public class GamesConfig {
	public static String projectRoot;
	public static String pgnRoot;

	static {
		File f = new File(".");

		try {
			projectRoot = f.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!projectRoot.endsWith("Chess"))
			projectRoot += "/Chess";

		System.out.println("Project root: " + projectRoot);

		pgnRoot = projectRoot + "/pgn"; // .replace(".", "pgn");

		System.out.println("PGN root: " + pgnRoot);
		System.out.println();
	}
}
