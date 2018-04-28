package Tools;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenShoter {

    public static String BeforeScreenshot(String fromclass, IOSDriver driver) throws InterruptedException {
        //timeout.implicitlyWait(30, TimeUnit.SECONDS);
        Thread.sleep(5000);
        DateFormat dateFormat;
        String destDir = "screenshots";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        new File(destDir).mkdirs();
        String destFile ="Before_"+fromclass+"_"+dateFormat.format(new Date()) + ".png";
        try {

            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Distribute.message("SCREENSHOT: Made before screenshot at " + fromclass);
        return destDir + "/" + destFile;
    }

    public static String BeforeScreenshot(String fromclass, IOSDriver driver, WebElement element) throws InterruptedException {
        //timeout.implicitlyWait(30, TimeUnit.SECONDS);
        Thread.sleep(5000);
        DateFormat dateFormat;
        String destDir = "screenshots";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File(destDir + "/" + "test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        new File(destDir).mkdirs();
        String destFile ="Before_"+fromclass+"_"+dateFormat.format(new Date()) + ".png";
        try {
            BufferedImage fullImg = ImageIO.read(scrFile);
            // Get the location of element on the page
            Point point = element.getLocation();

            // Get width and height of the element
            int eleWidth = (element.getSize().getWidth()-1)*ConfigureDevice.resize_factor;
            int eleHeight = (element.getSize().getHeight()-1)*ConfigureDevice.resize_factor;
            int x= point.getX()*ConfigureDevice.resize_factor;
            int y = point.getY()*ConfigureDevice.resize_factor;
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage elementScreenshot= fullImg.getSubimage(x, y,
                    eleWidth, eleHeight);
            ImageIO.write(elementScreenshot, "png", scrFile);
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Distribute.message("SCREENSHOT: Made before screenshot at " + fromclass);
        return destDir + "/" + destFile;
    }

    public static String AfterScreenshot(String fromclass, AppiumDriver driver) throws InterruptedException {
        Thread.sleep(5000);
        DateFormat dateFormat;
        String destDir = "screenshots";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        new File(destDir).mkdirs();
        String destFile ="After_"+fromclass+"_"+dateFormat.format(new Date()) + ".png";
        try {

            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Distribute.message("SCREENSHOT: Made after screenshot at " + fromclass);
        return destDir + "/" + destFile;
    }



    public static String AfterScreenshot(String fromclass, AppiumDriver driver, WebElement element) throws InterruptedException {
        //timeout.implicitlyWait(30, TimeUnit.SECONDS);
        Thread.sleep(5000);
        DateFormat dateFormat;
        String destDir = "screenshots";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        new File(destDir).mkdirs();
        String destFile ="After_"+fromclass+"_"+dateFormat.format(new Date()) + ".png";
        try {
            BufferedImage fullImg = ImageIO.read(scrFile);
            // Get the location of element on the page
            Point point = element.getLocation();

// Get width and height of the element
            int eleWidth = (element.getSize().getWidth()-1)*ConfigureDevice.resize_factor;
            int eleHeight = (element.getSize().getHeight()-1)*ConfigureDevice.resize_factor;
            int x= point.getX()*ConfigureDevice.resize_factor;
            int y = point.getY()*ConfigureDevice.resize_factor;
            // Crop the entire page screenshot to get only element screenshot
            BufferedImage elementScreenshot= fullImg.getSubimage(x, y,
                    eleWidth, eleHeight);
            ImageIO.write(elementScreenshot, "png", scrFile);
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));

        } catch (IOException e) {
            e.printStackTrace();
        }

        Distribute.message("SCREENSHOT: Made after screenshot at " + fromclass);
        return destDir + "/" + destFile;
    }





}
