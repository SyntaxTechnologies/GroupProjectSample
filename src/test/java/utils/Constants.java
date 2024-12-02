package utils;

public class Constants {

	// -------------------- File Paths --------------------

	/** Path to the configuration file */
	public static final String CONFIGURATION_FILEPATH =
			getValidatedFilePath("/src/test/resources/config/config.properties");

	/** Path to the screenshots directory */
	public static final String SCREENSHOT_FILEPATH =
			System.getProperty("user.dir") + "/screenshots/";

	/** Path to the test data Excel file */
	public static final String TESTDATA_FILEPATH =
			getValidatedFilePath("/src/test/resources/testdata/HrmsData.xlsx");

	/** Path to the Bug Busters test image */
	public static final String BUG_BUSTERS_PICTURE_FILEPATH =
			getValidatedFilePath("/src/test/resources/testdata/bugbusters.PNG");

	// -------------------- Timeouts --------------------

	/** Implicit wait time (in seconds) */
	public static final int IMPLICIT_WAIT = 10;

	/** Explicit wait time (in seconds) */
	public static final int EXPLICIT_WAIT = 20;

	// -------------------- Helper Methods --------------------

	/**
	 * Validates the file path to ensure it exists in the system.
	 * Logs a warning if the file does not exist.
	 *
	 * @param relativePath The relative path to validate.
	 * @return The absolute path based on the provided relative path.
	 */
	private static String getValidatedFilePath(String relativePath) {
		String absolutePath = System.getProperty("user.dir") + relativePath;
		java.io.File file = new java.io.File(absolutePath);

		if (!file.exists()) {
			System.err.println("Warning: File or directory does not exist - " + absolutePath);
		}
		return absolutePath;
	}
}
