package com.taoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TaoKe {
	public void test() throws InterruptedException, IOException{
		
		System.setProperty("webdriver.chrome.driver", "res//chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://www.dataoke.com/login");
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By
				.xpath("//input[@data-login='username']"));
		
		element.click();
		element.sendKeys("zhuxinglun@126.com");
		element = driver.findElement(By.xpath("//input[@data-id='pwd']"));
		element.sendKeys("Autumn#123");
		element = driver.findElement(By.cssSelector("a.submit-btn.login-btn"));
		element.click();
		
		//领券直播页面
		driver.get("http://www.dataoke.com/qlist/");
		List goodsTypeList = new ArrayList();
		
		element = driver.findElement(By.className("goods-type-main"));
		
		
		System.out.println(element.getText());
//		driver.quit();
	}

}
