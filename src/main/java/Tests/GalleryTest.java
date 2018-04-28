package Tests;

import Modules.Enums.AspectRatio;
import Modules.Gallery;
import Modules.Workspace;
import Tools.ConfigureDevice;
import Tools.Distribute;
import Tools.GlobalScreenshoter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class GalleryTest {
    private static IOSDriver driver;
    GlobalScreenshoter globalScreenshoter;

    @BeforeClass
    @Parameters("Device_ID")
    public void Setup(String Device_ID) throws MalformedURLException {
        globalScreenshoter = new GlobalScreenshoter();
        try {
            DesiredCapabilities capabilities = ConfigureDevice.getDevice(Device_ID);
            driver = new IOSDriver(new URL("http://127.0.0.1:5000/wd/hub"), capabilities);
            Distribute.message("INFO: Driver for:" + Device_ID + " created");
        } catch (Exception e) {
            System.err.println("ERROR: PHONE NOT CONNECTED, SEE APPIUM LOGS");
        }
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test(alwaysRun = true)
    public void CreateProjectWithOneVideo() {
        Distribute.message("TESTCASE: Create the project with one video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.applyHelp();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        int a = gallery.getVideoDuration();
        Workspace workspace = new Workspace(driver);
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(a-b) > gallery.getCountVideoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");
    }

    @Test(dependsOnMethods = { "CreateProjectWithOneVideo" }, alwaysRun = true)
    public void CreateProjectWithSomeVideos() {
        Distribute.message("TESTCASE: Create the project with some videos");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.checkVideoItem(2);
        gallery.addVideosToProject();
        int a = gallery.getVideoDuration();
        Workspace workspace = new Workspace(driver);
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(a-b) > gallery.getCountVideoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");
    }

    @Test(dependsOnMethods = { "CreateProjectWithSomeVideos" }, alwaysRun = true)
    public void CheckVideoGalleryisEmpty() {
        Distribute.message("TESTCASE: Check the Video Gallery is empty");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        int a = gallery.getCountAvaliableVideos();
        gallery.closeGallery();
        if (a<=0) Distribute.message("Галерея пуста");
        else Distribute.message("В галерее есть видео");
    }

    @Test(dependsOnMethods = { "CheckVideoGalleryisEmpty" }, alwaysRun = true)
    public void CheckPhotoGalleryisEmpty(){
        Distribute.message("TESTCASE: Check the Photo Gallery is empty");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        int a = gallery.getCountAvaliablePhotos();
        gallery.closeGallery();
        if (a<=0) Distribute.message("Галерея пуста");
        else Distribute.message("В галерее есть фото");
    }

    @Test(dependsOnMethods = { "CheckPhotoGalleryisEmpty" }, alwaysRun = true)
    public void CreateProjectWithOnePhotos() throws InterruptedException {
        Distribute.message("TESTCASE: Create the project with one photo");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        int a = gallery.getPhotoDuration();
        Workspace workspace = new Workspace(driver);
        int b = workspace.getDuration();
        workspace.closeProject();
        if (a!=b) Assert.fail("Не сопоставлется время фото");
    }

    @Test(dependsOnMethods = { "CreateProjectWithOnePhotos" }, alwaysRun = true)
    public void CreateProjectWithSomePhotos() throws InterruptedException {
        Distribute.message("TESTCASE: Create the project with some photo");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkPhotoItem(1);
        gallery.checkPhotoItem(2);
        gallery.addPhotosToProject();
        int a = gallery.getPhotoDuration();
        Workspace workspace = new Workspace(driver);
        int b = workspace.getDuration();
        workspace.closeProject();
        if (a != b) Assert.fail("Не сопоставлется время нескольких фото");
    }

    @Test(dependsOnMethods = { "CreateProjectWithSomePhotos" }, alwaysRun = true)
    public void DeletePhotoInGallery() throws InterruptedException {
        Distribute.message("TESTCASE: Delete photo in Gallery");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        globalScreenshoter.BeforeScreenGlobal(driver,true);
        gallery.deletePhotoItem(1);
        globalScreenshoter.AfterScreenGlobal(driver,true);
        gallery.closeGallery();
        globalScreenshoter.SubmitScreenshots();
    }

    @Test(dependsOnMethods = { "DeletePhotoInGallery" }, alwaysRun = true)
    public void AddAllVisibleVideos() throws InterruptedException {
        Distribute.message("TESTCASE: Add all visible videos to project");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.checkAllVideos();
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = gallery.getVideoDuration();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(a-b) > gallery.getCountVideoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");

    }


    @Test(dependsOnMethods = { "AddAllVisibleVideos" }, alwaysRun = true)
    public void OpenClosePhotoGalleryAndAddVideoToProject() throws InterruptedException {
        Distribute.message("TESTCASE: Open and close photo gallery, after that add video to project");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        globalScreenshoter.BeforeScreenGlobal(driver,true);
        gallery.closeGallery();
        globalScreenshoter.AfterScreenGlobal(driver,true);
        gallery.openVideoGallery();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = gallery.getVideoDuration();
        int b = workspace.getDuration();
        workspace.closeProject();
        globalScreenshoter.SubmitScreenshots();
        if (Math.abs(a-b) > gallery.getCountVideoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");
    }

    @Test(dependsOnMethods = { "OpenClosePhotoGalleryAndAddVideoToProject" }, alwaysRun = true)
    public void ChangingCheckedVideoItem() throws InterruptedException {
        Distribute.message("TESTCASE: Difference between checked and non-checked video item");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.BeforeScreenLocal(gallery.getVideoItem(1),true);
        gallery.checkVideoItem(1);
        gallery.AfterScreenLocal(gallery.getVideoItem(1),true);
        gallery.closeGallery();
        gallery.SubmitScreenshots();
    }

    @Test(dependsOnMethods = { "ChangingCheckedVideoItem" }, alwaysRun = true)
    public void AddAllVisiblePhotos() throws InterruptedException {
        Distribute.message("TESTCASE: Check all visible photos at gallery and add it to project");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.checkAllPhotos();
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = gallery.getPhotoDuration();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(a-b) > gallery.getCountPhotoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");


    }

    @Test(dependsOnMethods = { "AddAllVisiblePhotos" }, alwaysRun = true)
    public void ChangingPhotoItem() throws InterruptedException {
        Distribute.message("TESTCASE: Difference between checked and non-checked photo item");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.BeforeScreenLocal(gallery.getPhotoItem(1),true);
        gallery.checkPhotoItem(1);
        gallery.AfterScreenLocal(gallery.getPhotoItem(1),true);
        gallery.closeGallery();
        gallery.SubmitScreenshots();

    }

    @Test (dependsOnMethods = {"ChangingPhotoItem"}, alwaysRun = true)
    public void OpenCloseVideoGalleryAndAddPhotoToProject() throws InterruptedException {
        Distribute.message("TESTCASE: Open and close video gallery, after that add photo to project");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        globalScreenshoter.BeforeScreenGlobal(driver,true);
        gallery.closeGallery();
        globalScreenshoter.AfterScreenGlobal(driver,true);
        gallery.openPhotoGallery();
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = gallery.getPhotoDuration();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(a-b) > gallery.getCountPhotoItems()) Assert.fail("Не сопоставляются время из галереи и рабочей области");

    }



}
