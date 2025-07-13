package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class LoginPageTest extends BaseTest {

	@Test
	public void loginPageTitleTest() {
		String acttitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(acttitle,AppConstants.LOGIN_PAGE_TITLE);
	}

	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}

	@Test
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Test
	public void logoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}

	@Test(priority=Integer.MAX_VALUE)
	public void loginTest() throws InterruptedException {
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	//	String accountPageTitle = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Thread.sleep(9000);
	Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
		
	}

}
