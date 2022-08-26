package test;
import driver.DriverSingleton;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;
import java.time.Duration;

@Listeners(TestListener.class)
public class FirstTests {
    WebDriver driver;
    @BeforeTest
    public void setUp(){

        driver = DriverSingleton.getInstance();
    }

    @Test
    public void keyboardAndMouseInputTest(){
        driver.get("https://formy-project.herokuapp.com/keypress");
        String testInput = "Cheese!";
        WebElement fullNameField = driver.findElement(By.id("name"));
        fullNameField.click(); fullNameField.sendKeys(testInput);
        driver.findElement(By.id("button")).click();
        Assert.assertEquals(fullNameField.getText(), testInput);
    }

    @Test
    public void pageScrollTest(){
        driver.get("https://formy-project.herokuapp.com/scroll");
        WebElement fullNameField = driver.findElement(By.id("name"));
        new Actions(driver).scrollToElement(fullNameField).perform();
        fullNameField.sendKeys("Cheese!");
        WebElement dateField = driver.findElement(By.id("date"));
        dateField.sendKeys("08/23/22");
    }

    @Test
    public void switchToTabTest() throws InterruptedException {
        driver.get("https://formy-project.herokuapp.com/switch-window");
        driver.findElement(By.id("new-tab-button")).click();
        String originalWindowHandle = driver.getWindowHandle();
        for (String handle: driver.getWindowHandles()){
            driver.switchTo().window(handle);
        }
        Thread.sleep(2000);
        driver.switchTo().window(originalWindowHandle);
    }

    @Test
    public void switchToAlertTest(){
        driver.get("https://formy-project.herokuapp.com/switch-window");
        driver.findElement(By.id("alert-button")).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    public void javascriptExecutorTest(){
        driver.get("https://formy-project.herokuapp.com/modal");
        driver.findElement(By.id("modal-button")).click();
        WebElement closeBtn = driver.findElement(By.id("close-button"));
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//        jsExecutor.executeScript("arguments[0].click();", closeBtn);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(closeBtn))
                .click();
    }

    @Test
    public void dragAndDropTest(){
        driver.get("https://formy-project.herokuapp.com/dragdrop");
        WebElement image = driver.findElement(By.id("image"));
        WebElement box = driver.findElement(By.id("box"));
        new Actions(driver).dragAndDrop(image, box).perform();
    }

    @Test
    public void radioBtnTest() throws InterruptedException {
        driver.get("https://formy-project.herokuapp.com/radiobutton");
        WebElement radioBtn1 = driver.findElement(By.id("radio-button-1"));
        WebElement radioBtn2 = driver.findElement(By.cssSelector("input[value='option2']"));
        WebElement radioBtn3 = driver.findElement(By.xpath("//input[@value='option3']"));
        radioBtn3.click();
        Thread.sleep(2000);
        radioBtn2.click();
        Thread.sleep(2000);
        radioBtn1.click();
    }


    @Test
    public void dropdownTest(){
        driver.get("https://formy-project.herokuapp.com/dropdown");
        driver.findElement(By.id("dropdownMenuButton")).click();
        driver.findElement(By.xpath("//*[@class='dropdown show']//a[@class='dropdown-item' and text()='Drag and Drop']"))
                .click();
    }

    @Test
    public void uploadFileTest(){
        driver.get("https://formy-project.herokuapp.com/fileupload");
        WebElement fileUploadField = driver.findElement(By.id("file-upload-field"));
        fileUploadField.sendKeys("hello.txt");
    }

    @Test
    public void putAllTogetherTest(){
        driver.get("https://formy-project.herokuapp.com/form");
        driver.findElement(By.id("first-name")).sendKeys("Mary");
        driver.findElement(By.id("last-name")).sendKeys("Green");
        driver.findElement(By.id("job-title")).sendKeys("qa tester");
        driver.findElement(By.id("radio-button-1")).click();
        driver.findElement(By.id("checkbox-2")).click();
        driver.findElement(By.id("select-menu")).click();
        driver.findElement(By.cssSelector("[id='select-menu'] option[value='1']")).click();
        driver.findElement(By.id("datepicker")).sendKeys("08/24/2022");
        driver.findElement(By.xpath("//*[contains(@class, 'btn-primary')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
        Assert.assertEquals(successAlert.getText(), "The form was successfully submitted!");
    }

    @AfterTest
    public void shutDown(){
        DriverSingleton.shutdownDriver();
    }
}
