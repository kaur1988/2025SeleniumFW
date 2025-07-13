package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil; // Here default value of eleUtil is null. if you start using eleUtil. it will
									// give you NPE.
	// so thats why we have to initialized the eleUtil in side the page const.

	// 1. private By locators: page objects

	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.cssSelector("img.img-responsive");
	private By registerLink = By.linkText("Register");

	// 2. Public Page Const... whenever someone create a object of login page we
	// will supply the driver and asme driver will given to element util.

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	// 3. Public Page Actions/Methods

	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE,
				AppConstants.DEFAULT_SHORT_TIME_OUT);
		// String title = driver.getTitle(); // here driver value is null then you will
		// get NPE to resolve this add cont
		System.out.println("login page Title: " + title);
		return title;
	}

	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContainsAndReturn(AppConstants.LOGIN_PAGE_FRACTION_URL,
				AppConstants.DEFAULT_SHORT_TIME_OUT);
		// String url = driver.getCurrentUrl();
		System.out.println("login page Title: " + url);
		return url;
	}

	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
		// return driver.findElement(forgotPwdLink).isDisplayed();
	}

	public boolean isLogoExist() {
		return eleUtil.isElementDisplayed(logo);
		// return driver.findElement(logo).isDisplayed();
	}

	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);

//		String actPageTitle= eleUtil.waitForTitleContainsAndReturn(AppConstants.ACCOUNTS_PAGE_TITLE,AppConstants.DEFAULT_SHORT_TIME_OUT);
//		//driver.findElement(username).sendKeys(userName);
//		//driver.findElement(password).sendKeys(pwd);
//		//driver.findElement(loginBtn).click();
//		//String accountPageTitle= driver.getTitle();
//		System.out.println("Account page title : " + actPageTitle);
//		return actPageTitle;
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
