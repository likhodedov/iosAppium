package Tools;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * Created by test_user_1 on 30.11.17.
 */
public class GlobalScreenshoter implements GlobalScreenshots {

    ScreenerList globalScreenerList;

    public GlobalScreenshoter(){
        this.globalScreenerList = new ScreenerList();
    }

    @Override
    public void BeforeScreenGlobal(IOSDriver driver, boolean inverted_logic) throws InterruptedException {
        String before = ScreenShoter.BeforeScreenshot(GlobalScreenshots.fromClass,driver);
        globalScreenerList.AddBefore(before,inverted_logic);
    }

    @Override
    public void AfterScreenGlobal(IOSDriver driver, boolean inverted_logic) throws InterruptedException {
        String after = ScreenShoter.AfterScreenshot(GlobalScreenshots.fromClass,driver);
        globalScreenerList.AddAfter(after,inverted_logic);
    }

    @Override
    public void SubmitScreenshots() {
        try {
            globalScreenerList.processList();
        } finally {
            globalScreenerList.clearList();
        }
    }
}
