package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	OptionsManager optionsManager;
	/**
	 * This method is used to init the driver on the basis of given browsername.
	 * 
	 * @param browserName
	 * @return it returns driver
	 */
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		System.out.println("Browser name is : " + browserName);

		isHighlight= prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
			//driver = new FirefoxDriver(optionsManager.getFirfoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirfoxOptions()));
			break;
		case "edge":
			//driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
			System.out.println(AppError.INVALID_BROWSER_MESG + browserName + " is invalid");
			throw new BrowserException(AppError.INVALID_BROWSER_MESG);
		}
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is used to init the properties from the config file
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */

	// mvn clean install -Denv="qa"
	public Properties initProp() {
		
		prop = new Properties();// create object of Properties class
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");
		System.out.println("Runing tests on env: " + envName);
		
try {
		if (envName == null) {
			System.out.println("env is null....hence running tests on QA env");
			ip = new FileInputStream(".\\src\\test\\resourcess\\config\\qa.config.properties");
		}
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream(".\\src\\test\\resourcess\\config\\qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream(".\\src\\test\\resourcess\\config\\dev.config.properties");
				break;
			default:
				System.out.println("please pass the right env name... " + envName);
				throw new FrameworkException("Invalid Env Name");
			}
		}
		prop.load(ip);
}catch (FileNotFoundException e) {
	
	e.printStackTrace();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return prop;
}

//		try {
//			ip = new FileInputStream(".\\src\\test\\resourcess\\config\\config.properties");// object of FileInputStream
//																							// class is
//																							// actual
//																							// making
//																							// connection
//																							// with the
//																							// file.
//			prop.load(ip);
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//return prop;
//}
//}
/**
 * take screenshot
 */

public static String getScreenshot(String methodName) {
	File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
	String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
			+ ".png";
	File destination = new File(path);
	try {
		FileHandler.copy(srcFile, destination);
	} catch (IOException e) {
		e.printStackTrace();
	}

	return path;
}
}

