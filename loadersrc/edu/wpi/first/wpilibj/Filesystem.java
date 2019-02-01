package edu.wpi.first.wpilibj;

import java.io.File;

/**
 * Temp edition for testing without robot access
 */
public class Filesystem {
	public static File getDeployDirectory() {
		return new File("src/deploy/");
	}
}
