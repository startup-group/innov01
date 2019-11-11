package com.taoke;

import java.io.IOException;
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
	private WebDriver driver;
	
	
	//主函数入口
	public static void main(String[] args) throws InterruptedException, IOException{
		logger.setLevel(Level.INFO);
		TaoKe myTaoke = new TaoKe();
		myTaoke.login();
		
		String pageUrl = "http://www.dataoke.com/qlist/?cid=1&px=zx&page=";
		for(int i = 1; i<=2; i++){
			myTaoke.getPageData(pageUrl+i);
		}
	}
	
	
	/**
	 * 登陆网站
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void login() throws InterruptedException, IOException{
		logger.setLevel(Level.INFO);
		
//		System.setProperty("webdriver.chrome.driver", "res//chromedriver");
//		WebDriver driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", "res//geckodriver");
		driver = new FirefoxDriver();
		
		String str = readDataFromConsole("输入URL地址：");  
        logger.info("输入的地址为 : " + str);
		driver.get(str);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By
				.xpath("//input[@data-login='username']"));
		
		element.click();
		element.sendKeys("zhuxinglun@126.com");
		element = driver.findElement(By.xpath("//input[@data-id='pwd']"));
		element.sendKeys("Autumn#123");
		element = driver.findElement(By.cssSelector("a.submit-btn.login-btn"));
		element.click();
		
//		driver.quit();
	}
	
	/**
	 * 获取大淘客页面商品ID
	 * @param pageUrl
	 */
	public void getPageData(String pageUrl){
		logger.setLevel(Level.INFO);
		logger.info(pageUrl);
		driver.get(pageUrl);
		
		List <WebElement> ees = driver.findElements(By.xpath("//div[starts-with(@id,'goods-items_')]"));
		
		System.out.println(ees.size());
		
		for(WebElement e : ees){
			String s = e.getAttribute("id").substring(12);
			logger.info(s);
		}
		
	}
	
	public void getDetailData(String id){
		String url = "http://www.dataoke.com/item?id=" + id;
		driver.get(url);
		
	}
	
	/**
	 * 控制台输入
	 * @param prompt
	 * @return
	 */
	private static String readDataFromConsole(String prompt) {  
		System.out.println(prompt);
        Scanner scanner = new Scanner(System.in);  
        String str = scanner.nextLine(); 
        scanner.close();
        return str;
    }

}
