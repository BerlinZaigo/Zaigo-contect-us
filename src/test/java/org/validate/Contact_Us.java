package org.validate;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Contact_Us {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.zaigoinfotech.com/");
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Faker faker = new Faker();
		Random random = new Random();
		long tenDigitPhoneNumber = 1000000000L + random.nextLong(9000000000L);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		By contact_us = By.xpath("//*[@id='mega-menu-item-9135']/a");
		By your_name = By.xpath("//*[@id='0357a3fe-69d9-442f-a379-1a1b5e5dae86']");
		By your_email = By.xpath("//*[@id='2ebd1d00-0281-427f-9910-5bb4c013a26b']");
		By phone_no = By.xpath("//*[@id='05324d2c-239a-4f91-924a-f7c74cd2e639']");
		By about_project = By.xpath("//*[@id='7a551e36-6524-49e8-8a15-2429685d962e']");
		By submit = By.xpath(
				"/html/body/div[1]/section[2]/div/div/div[2]/div/div/div/div/div/div/form/div/div[4]/div[5]/div/div/button");

		wait.until(ExpectedConditions.visibilityOfElementLocated(contact_us));
		WebElement contact = driver.findElement(contact_us);
		contact.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(your_name));
		WebElement name = driver.findElement(your_name);
		name.sendKeys(faker.name().fullName());

		wait.until(ExpectedConditions.visibilityOfElementLocated(your_email));
		WebElement email = driver.findElement(your_email);
		email.sendKeys(faker.internet().emailAddress());

		wait.until(ExpectedConditions.visibilityOfElementLocated(phone_no));
		WebElement ph_no = driver.findElement(phone_no);
		ph_no.sendKeys(String.valueOf(tenDigitPhoneNumber));

		wait.until(ExpectedConditions.visibilityOfElementLocated(about_project));
		WebElement abt_project = driver.findElement(about_project);
		abt_project.sendKeys(String.valueOf(faker.company()));

		boolean clicked = false;
		do {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(submit));
				WebElement fnl_submit = driver.findElement(submit);
				js.executeScript("arguments[0].scrollIntoView(true);", fnl_submit);
				fnl_submit.click();
				clicked = true; // Exit the loop if clicked
			} catch (ElementClickInterceptedException e) {
				js.executeScript("window.scrollBy(0, 200);");
			}
		} while (!clicked);

		wait.until(ExpectedConditions.urlContains("thank-you"));

		if (driver.getCurrentUrl().contains("thank-you")) {
			System.out.println("Form submitted");
		} else {
			System.out.println("Form not submitted");
		}

		driver.quit();

	}
}
