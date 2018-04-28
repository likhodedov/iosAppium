package Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoCapture {
    private By type_of_capture= By.name("CameraMode");
    private By cancel_capture= By.name("VideoCapture");
    private By start_capture= By.name("VideoCapture");
    private By change_type_of_camera= By.name("FrontBackFacingCameraChooser");
    private By captured_time= By.xpath("//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/XCUIElementTypeWindow[1]" +
            "/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[3]");
    private By retake_video= By.name("Retake");
    private By play_video= By.name("Play");
    private By use_video= By.name("Use Video");
    private final WebDriver driver;

    public VideoCapture(WebDriver driver) {
        this.driver = driver;
        String s="video";
        if (!s.equals(this.driver.findElement(type_of_capture).getText())){
            throw new IllegalStateException("We can't take pictures from camera now in our app");
        }
    }

    public VideoCapture CaptureVideo (int seconds) throws InterruptedException {
        driver.findElement(start_capture).click();
        Thread.sleep(seconds*1000);
        return this;
    }

    public VideoCapture StopCaptureVideo (){
        driver.findElement(start_capture).click();
        return this;
    }

    public VideoCapture ChangeCamera() {
        driver.findElement(change_type_of_camera).click();
        return this;
    }
    public VideoCapture CancelVideoCapture(){
        driver.findElement(cancel_capture).click();
        return this;
    }

    private String GetCapturedTime (){
        return driver.findElement(captured_time).getText();
    }
}
