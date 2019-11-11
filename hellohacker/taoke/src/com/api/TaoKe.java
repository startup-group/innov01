package com.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TaoKe {
	private static Logger logger = Logger.getLogger(TaoKe.class); 
	private static WebDriver driver;
	
	
	//主函数入口
	public static void main(String[] args) throws InterruptedException, IOException, SQLException{
		logger.setLevel(Level.INFO);
		TaoKe myTaoke = new TaoKe();
		myTaoke.login("http://www.dataoke.com/login");
		String baseURL = "http://www.dataoke.com/item?id=";
		
		List<String> list = TaoKeAPI.getTongYongGoods("select ID from data_t where Jihua_Link=''");
		for(String s : list){
			getPageData(baseURL + s);
			System.out.println(s);
		}
	}
	
	
	/**
	 * 登陆网站
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void login(String url) throws InterruptedException, IOException{
		logger.setLevel(Level.INFO);
		
//		System.setProperty("webdriver.chrome.driver", "res//chromedriver");
//		WebDriver driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", "res//geckodriver");
		driver = new FirefoxDriver();
		
		driver.get(url);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By
				.xpath("//input[@data-login='username']"));
		
		element.click();
		element.sendKeys("zhuxinglun@126.com");
		element = driver.findElement(By.xpath("//input[@data-id='pwd']"));
		element.sendKeys("Autumn#123");
		element = driver.findElement(By.cssSelector("a.submit-btn.login-btn"));
		element.click();
	}
	
	
	
	/**
	 * 获取大淘客页面商品ID
	 * @param pageUrl
	 */
	public static void getPageData(String pageUrl){
		logger.setLevel(Level.INFO);
		logger.info(pageUrl);
		driver.get(pageUrl);
//		WebElement e = driver.findElement("");
		
	}
}
