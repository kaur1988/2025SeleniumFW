package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class RegisterPageTest extends BaseTest {
	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	public String getRandomEmail() {
		return "uiautomation"+System.currentTimeMillis()+"@open.com";
	}
//	@DataProvider
//	public Object[][] userRegisterData() {
//		return new Object[][] {}
	
	
	
	//(dataProvider="userRegisterData")		
	@Test
	public void userRegisterTest() {
		Assert.assertTrue(registerPage.userRegisteration("Kamalpreet", "Kaur", getRandomEmail(),"954876895", "kamal@112345", "yes"));
		
	}
}
