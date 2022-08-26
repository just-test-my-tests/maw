package utils;

import driver.DriverSingleton;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {
    Logger log = LogManager.getLogger();
    private static final String PATH_TO_SCREENSHOTS_FOLDER =".//target/screenshots/";

    public void onTestFailure(ITestResult result){
        makeScreenShot();
    }
    public void makeScreenShot(){
        try{
            File screenshot = ((TakesScreenshot) DriverSingleton.getInstance()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFileToDirectory(screenshot, new File(PATH_TO_SCREENSHOTS_FOLDER));
            log.info("Taken screenshots <a href='screenshots/"+screenshot.getName()+"'>"+screenshot.getName()+"</a>");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
