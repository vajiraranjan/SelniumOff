package com.Sapient.ProjectName.helper.Wait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Sapient.ProjectName.helper.logger.LoggerHelper;

public class WaitHelper {
	
	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(WaitHelper.class);
	public WaitHelper(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void setImplicitWait(long timeout,TimeUnit unit)
	{
		log.info("Implicit wait has been set to be: " +timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit);
	}
	
	private WebDriverWait getWait(int TimeOutInSeconds, int PollingEveryInMiliSec)
	{
		WebDriverWait wait= new WebDriverWait(driver, TimeOutInSeconds);
		wait.pollingEvery(Duration.ofMillis(PollingEveryInMiliSec));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
		
	}
	
	public void WaitForElementVisibleWithPollingTime(WebElement element, int timeOutInSeconds, int pollingEveryInMiliSec)
	{
		log.info("Waiting for :" +element.toString()+ " for :" + timeOutInSeconds+" seconds" );
		WebDriverWait wait= getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(element));
		log.info("element is visible now");
	}
	
	public void WaitForElementClickable(WebElement element, int timeOutInSeconds)
	{
		log.info("Waiting for :" +element.toString()+ " for :" + timeOutInSeconds+" seconds" );
		WebDriverWait wait= new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		log.info("element is clickable now");
	}
	
	public boolean waitForElementNotPresent(WebElement element, long timeOutInSeconds)
	{
		log.info("Waiting for :" +element.toString()+ " for :" + timeOutInSeconds+" seconds" );
		WebDriverWait wait= new WebDriverWait(driver, timeOutInSeconds);
		boolean status=wait.until(ExpectedConditions.invisibilityOf(element));
		log.info("element is invisible now");
		return status;
	}
	
	public void waitForframeToBeAvailableAndSwitchToIt(WebElement element, long timeOutInSeconds)
	{
		log.info("Waiting for :" +element.toString()+ " for :" + timeOutInSeconds+" seconds" );
		WebDriverWait wait= new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
		log.info("frame is available and switched");
		
	}
	
	private Wait<WebDriver> getfluentWait(int timeOutInSeconds, int pollingEveryInMiliSec)
	{
		Wait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOutInSeconds))
				.pollingEvery(Duration.ofMillis(pollingEveryInMiliSec)).ignoring(NoSuchElementException.class);
		return fWait;
	}
	
	public WebElement waitForElement(WebElement element,int timeOutSeconds, int pollingEveryInMiliSec)
	{
		Wait<WebDriver> fwait = getfluentWait(timeOutSeconds, pollingEveryInMiliSec);
		fwait.until(ExpectedConditions.visibilityOf(element));
		return element;
	}
	
	public void pageLoadTime(long timeout, TimeUnit unit)
	{
		log.info("Waiting for page to load for :" +unit);
		driver.manage().timeouts().pageLoadTimeout(timeout, unit);
		log.info("page is loaded");
	}

}
