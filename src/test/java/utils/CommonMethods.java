package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import steps.PageInitializer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class CommonMethods extends PageInitializer {

	public static WebDriver driver;

	// -------------------- Browser Management --------------------

	/**
	 * Opens a browser and navigates to the specified URL.
	 * Reads browser type and URL from the configuration file.
	 */
	public static void openBrowser() {
		ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);

		String browser = ConfigReader.getPropertyValue("browser").toLowerCase();
		switch (browser) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				throw new RuntimeException("Invalid browser name: " + browser);
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
		driver.get(ConfigReader.getPropertyValue("url"));
		initializePageObjects();
	}

	/**
	 * Closes the browser if it is open.
	 */
	public static void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}

	// -------------------- Utility Methods --------------------

	/**
	 * Returns the current timestamp in the specified format.
	 *
	 * @param pattern Format string (e.g., "yyyy-MM-dd-HH-mm-ss")
	 * @return Formatted timestamp string
	 */
	public static String getTimeStamp(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	/**
	 * Takes a screenshot and saves it to the specified folder.
	 *
	 * @param filename Name of the screenshot file
	 * @return Screenshot as a byte array for reporting
	 */
	public static byte[] takeScreenshot(String filename) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
		File screenshotFile = ts.getScreenshotAs(OutputType.FILE);

		try {
			String filePath = Constants.SCREENSHOT_FILEPATH + filename + " " + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png";
			FileUtils.copyFile(screenshotFile, new File(filePath));
		} catch (IOException e) {
			System.err.println("Error saving screenshot: " + e.getMessage());
		}

		return screenshotBytes;
	}

	/**
	 * Generates a random date between 1970 and 2019 in the format "yyyy-MM-dd".
	 *
	 * @return Randomly generated date as a string
	 */
	public static String getRandomDate() {
		GregorianCalendar gc = new GregorianCalendar();
		int year = randBetween(1970, 2019);
		gc.set(Calendar.YEAR, year);
		int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);

		String day = gc.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + gc.get(Calendar.DAY_OF_MONTH) : String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
		String month = (gc.get(Calendar.MONTH) + 1) < 10 ? "0" + (gc.get(Calendar.MONTH) + 1) : String.valueOf(gc.get(Calendar.MONTH) + 1);

		return gc.get(Calendar.YEAR) + "-" + month + "-" + day;
	}

	/**
	 * Generates a random integer between the specified start and end (inclusive).
	 *
	 * @param start Start of the range
	 * @param end   End of the range
	 * @return Random integer between start and end
	 */
	public static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/**
	 * Generates a random string of alphabets.
	 *
	 * @return Random alphabetic string
	 */
	public static String randomAlphabets() {
		Random random = new Random();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			char randomChar = (char) ('a' + random.nextInt(26));
			result.append(randomChar);
		}
		return result.toString();
	}

	// -------------------- Waiting and Clicking --------------------

	/**
	 * Returns a WebDriverWait object with a predefined timeout.
	 */
	public static WebDriverWait getWait() {
		return new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
	}

	/**
	 * Waits until an element is clickable.
	 *
	 * @param element WebElement to wait for
	 */
	public static void waitForClickability(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Clicks on a WebElement after ensuring it is clickable.
	 *
	 * @param element WebElement to click
	 */
	public static void click(WebElement element) {
		waitForClickability(element);
		element.click();
	}

	/**
	 * Performs a JavaScript click on a WebElement.
	 *
	 * @param element WebElement to click
	 */
	public static void jsClick(WebElement element) {
		getJSExecutor().executeScript("arguments[0].click();", element);
	}

	// -------------------- Dropdown Handling --------------------

	/**
	 * Selects a dropdown value by visible text.
	 *
	 * @param element      Dropdown WebElement
	 * @param textToSelect Text to select
	 */
	public static void selectDdValue(WebElement element, String textToSelect) {
		try {
			new Select(element).selectByVisibleText(textToSelect);
		} catch (UnexpectedTagNameException e) {
			System.err.println("Error selecting dropdown value: " + e.getMessage());
		}
	}

	// -------------------- Alert Handling --------------------

	/**
	 * Accepts an alert if present.
	 */
	public static void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (NoAlertPresentException e) {
			System.err.println("No alert to accept: " + e.getMessage());
		}
	}

	/**
	 * Retrieves the text from an alert.
	 *
	 * @return Alert text
	 */
	public static String getAlertText() {
		try {
			return driver.switchTo().alert().getText();
		} catch (NoAlertPresentException e) {
			System.err.println("No alert to retrieve text from: " + e.getMessage());
			return null;
		}
	}

	// -------------------- Frame Handling --------------------

	/**
	 * Switches to a frame by name, ID, WebElement, or index.
	 *
	 * @param identifier Frame identifier (name, ID, WebElement, or index)
	 */
	public static void switchToFrame(Object identifier) {
		try {
			if (identifier instanceof String) {
				driver.switchTo().frame((String) identifier);
			} else if (identifier instanceof WebElement) {
				driver.switchTo().frame((WebElement) identifier);
			} else if (identifier instanceof Integer) {
				driver.switchTo().frame((int) identifier);
			}
		} catch (NoSuchFrameException e) {
			System.err.println("Frame not found: " + e.getMessage());
		}
	}

	// -------------------- Window Handling --------------------

	/**
	 * Switches focus to a child window.
	 */
	public static void switchToChildWindow() {
		String mainWindow = driver.getWindowHandle();
		for (String window : driver.getWindowHandles()) {
			if (!window.equals(mainWindow)) {
				driver.switchTo().window(window);
				break;
			}
		}
	}

	/**
	 * Returns a JavascriptExecutor instance.
	 */
	public static JavascriptExecutor getJSExecutor() {
		return (JavascriptExecutor) driver;
	}

	/**
	 * Sends text to a WebElement after clearing it.
	 *
	 * @param element WebElement to send text to
	 * @param text    Text to send
	 */
	public static void sendText(WebElement element, String text) {
		if (element != null) {
			element.clear();
			element.sendKeys(text);
		} else {
			System.err.println("WebElement is null. Cannot send text.");
		}
	}

	/**
	 * Sends text to a WebElement and presses a key.
	 *
	 * @param element WebElement to send text to
	 * @param text    Text to send
	 * @param key     Key to press after sending text
	 */
	public static void sendText(WebElement element, String text, Keys key) {
		if (element != null) {
			element.clear();
			element.sendKeys(text, key);
		} else {
			System.err.println("WebElement is null. Cannot send text and press key.");
		}
	}

	/**
	 * Scrolls the page vertically by the specified number of pixels using JavaScript.
	 * If no value is provided, it defaults to scrolling by 200 pixels.
	 *
	 * @param pixels Number of pixels to scroll. Positive for downward, negative for upward.
	 */
	public static void jsScroll(int pixels) {
		try {
			getJSExecutor().executeScript("window.scrollBy(0, arguments[0]);", pixels);
		} catch (Exception e) {
			System.err.println("Error while scrolling: " + e.getMessage());
		}
	}

	/**
	 * Scrolls the page vertically by 200 pixels by default using JavaScript.
	 */
	public static void jsScroll() {
		jsScroll(200);
	}

	/**
	 * Moves the mouse cursor to the specified WebElement using Actions.
	 * If the WebElement is not displayed or interactable, logs an appropriate error.
	 *
	 * @param element The WebElement to move the cursor to.
	 */
	public static void moveCursor(WebElement element) {
		try {
			if (element.isDisplayed() && element.isEnabled()) {
				Actions action = new Actions(driver);
				action.moveToElement(element).perform();
				System.out.println("Cursor moved to the element: " + element.toString());
			} else {
				System.err.println("Element is not displayed or not enabled: " + element.toString());
			}
		} catch (Exception e) {
			System.err.println("Error moving cursor to element: " + e.getMessage());
		}
	}

}
