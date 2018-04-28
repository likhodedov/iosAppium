package Tools;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public interface GlobalScreenshots {
    String fromClass = "Global";
    public void BeforeScreenGlobal(IOSDriver driver, boolean inverted_logic) throws InterruptedException;
    public void AfterScreenGlobal(IOSDriver driver, boolean inverted_logic) throws InterruptedException;
    public void SubmitScreenshots();
}
