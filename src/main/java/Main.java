import Modules.Gallery;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by test_user_1 on 03.07.17.
 */
public class Main {

    By EXAMPLE=By.name("OK");





    public static void main(String[] args) throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        /*
        Для выбора устройства необходимо подставить параметр типа String в метод SelectoriOS
        iPhone_6
        iPhone_7
        iPhone_5s
        iPad_Air_2
        iPad_mini_2
        */

        capabilities=ConfigureDevice.SelectoriOS("iPhone_7");


        WebDriver driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        driver.findElement(By.name("OK")).click();
     //   driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.name("OK")).click();
     //   driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.findElement(By.name("Allow")).click();

        Gallery gallery=new Gallery(driver);
        gallery.ScrolltoTop();
       // gallery.CheckVideoItem(1);
        gallery.CheckVideoItem(3);
        gallery.CheckVideoItem(4);

        //gallery.CheckVideoCapture();

        //driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        driver.quit();
    }

}
