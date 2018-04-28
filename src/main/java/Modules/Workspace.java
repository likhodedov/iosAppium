package Modules;

import Modules.Enums.AspectRatio;
import Tools.ScreenShoter;
import Tools.ScreenerList;
import Tools.Touches;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class Workspace implements IWorkpace {
    private IOSDriver driver;
    private int duration;
    private WebDriver.Timeouts timeout;
    private ScreenerList screenerList;

    String audioButton = "audioButton";
    String rotateButton = "rotateButton";
    String stickersButton = "stickersButton";
    String textButton = "textButton";
    String speedButton = "speedButton";
    String colorButton = "colorButton";
    String transitionButton = "transitionButton";
    String duplicateButton = "duplicateButton";
    String deleteButton = "deleteButton";
    String currentDuration = "currentDurationText";
    String totalDuration = "totalDurationText";
    String closeProject = "cross image";
    String videoPlayer = "videoPlayer";

    String undoButton = "undoButton";





    public Workspace(IOSDriver driver) {
        this.duration = 0;
        this.driver = driver;
        this.timeout = driver.manage().timeouts();
        this.screenerList = new ScreenerList();

//        while (driver.findElementsByAccessibilityId(audioButton).size() == 0)
//            timeout.implicitlyWait(100, TimeUnit.MILLISECONDS);
    }

    @Override
    public void openSettings() {

    }

    @Override
    public void closeProject() {
        driver.findElementByAccessibilityId(closeProject).click();
        driver.findElementByXPath("//XCUIElementTypeButton[@name=\"ОК\"]").click();
    }

    @Override
    public void saveVideo() {

    }

    @Override
    public void changeAspectRatio(AspectRatio ratio) {

    }

    @Override
    public void changeSeekBarPosition() {

    }

    @Override
    public void openAudio() {
        findButtonAtPage(audioButton);
        driver.findElementByAccessibilityId(audioButton).click();
    }

    @Override
    public void rotateVideo() {
        findButtonAtPage(rotateButton);
        driver.findElementByAccessibilityId(rotateButton).click();
    }

    @Override
    public void openStickers() {
        findButtonAtPage(stickersButton);
        driver.findElementByAccessibilityId(stickersButton).click();
    }

    @Override
    public void openText() {
        findButtonAtPage(textButton);
        driver.findElementByAccessibilityId(textButton).click();
    }

    @Override
    public void openSpeed() {
        findButtonAtPage(speedButton);
        driver.findElementByAccessibilityId(speedButton).click();
    }

    @Override
    public void openColor() {
        findButtonAtPage(colorButton);
        driver.findElementByAccessibilityId(colorButton).click();
    }

    @Override
    public void openTransitions() {
        findButtonAtPage(transitionButton);
        driver.findElementByAccessibilityId(transitionButton).click();
    }

    @Override
    public void duplicateVideo() {
        findButtonAtPage(duplicateButton);
        driver.findElementByAccessibilityId(duplicateButton).click();
    }

    @Override
    public void deleteVideo() {
        findButtonAtPage(deleteButton);
        driver.findElementByAccessibilityId(deleteButton).click();
    }

    @Override
    public void cutVideo() {

    }

    @Override
    public void addVideo() {

    }

    @Override
    public void undoAction() {
        driver.findElementByAccessibilityId(undoButton).click();
    }

    @Override
    public void playVideo() {

    }

    @Override
    public int getDuration() {
        duration = parseTime(driver.findElementByAccessibilityId(totalDuration).getText());
        return duration;
    }

    @Override
    public WebElement getPlayer() {
        return driver.findElementByAccessibilityId(videoPlayer);
    }


    @Override
    public boolean isUndoAvaliable() {
        return driver.findElementByAccessibilityId(undoButton).isEnabled();
    }

    @Override
    public void changeTimlinePosition(double n) {

    }

    @Override
    public void closeTipAboutSwipe() {

    }

    private int parseTime(String inputString){
        String[] time= inputString.split(":");
        String[] seconds = time[1].split("\\.");
        return Integer.parseInt(time[0])*60+Integer.parseInt(seconds[0]);
    }

    private void findButtonAtPage(String id_element){
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        if ((driver.findElementsByAccessibilityId(id_element).size()==0)){
            if (driver.findElementsByAccessibilityId(deleteButton).size()==0)
                Touches.ScrollTools(driver.findElementByAccessibilityId(stickersButton), driver.findElementByAccessibilityId(audioButton), driver);
            else if (driver.findElementsByAccessibilityId(audioButton).size()==0)
                Touches.ScrollTools(driver.findElementByAccessibilityId(colorButton), driver.findElementByAccessibilityId(deleteButton), driver);
        }
    }

    @Override
    public void BeforeScreenLocal(WebElement element, boolean inverted_logic) throws InterruptedException {
        String className = this.getClass().getSimpleName();
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        String before= ScreenShoter.BeforeScreenshot(className, driver, element);
        screenerList.AddBefore(before,inverted_logic);
    }

    @Override
    public void AfterScreenLocal(WebElement element, boolean inverted_logic) throws InterruptedException {
        String className = this.getClass().getSimpleName();
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        String after=ScreenShoter.AfterScreenshot(className,driver,element);
        screenerList.AddAfter(after,inverted_logic);
    }

    @Override
    public void SubmitScreenshots() {
        screenerList.processList();
    }
}
