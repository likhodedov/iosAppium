package Tools;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;


public class Touches {


    //TODO: непонятное позиционирование, так как область элемента сикбара больше чем сам сикбар
    public static void Swipe(WebElement element, AppiumDriver driver, double position)
    {
        Double ratio = element.getSize().getWidth()*position;
        int factor = ratio.intValue();
        TouchAction action = new TouchAction(driver);
        action.longPress(element.getLocation().getX(),element.getLocation().getY()).moveTo(element.getLocation().getX()+factor,element.getLocation().getY());
        action.release().perform();

//        TouchAction action = new TouchAction(driver);
//        action.perform();
//        Distribute.message(startx+" "+starty);
//        Distribute.message("Entering swipe");
//        Distribute.message("Swipe from "+startx +" " +starty +"to" +endx +" " +endy );
//        action.press(startx,starty).moveTo(endx,endy).release().perform();
    }

    public static void LongClick(AndroidDriver driver, By element, int index, int clickCount, int X, int Y) {
        WebElement webElement = driver.findElement(element);
//        TouchActions actions=new TouchActions(driver);
//
//        actions.longPress(webElement).release().perform();
    }
    public static void TouchElement(WebElement element, IOSDriver driver, double position){
        TouchAction action= new TouchAction(driver);
        Double l = element.getSize().getWidth()*position;
        int newposition = l.intValue();
        action.press(element,newposition,element.getLocation().getY());

    }

    public static void LongPress(WebElement element, IOSDriver driver, int time) {
        TouchAction action = new TouchAction(driver);
//        action.longPress(element, Duration.ofSeconds(time)).perform();
    }

    public static void ScrollTools(WebElement element1, WebElement element2, IOSDriver driver) {
        TouchAction action = new TouchAction((MobileDriver)driver);
        action.press(element1).waitAction(2000).moveTo(element2);
        action.release().perform();
    }


}
