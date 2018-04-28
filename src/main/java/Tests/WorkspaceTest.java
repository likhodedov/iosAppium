package Tests;


import Modules.Enums.AspectRatio;
import Modules.Gallery;
import Modules.Workspace;
import Tools.ConfigureDevice;
import Tools.Distribute;
import Tools.GlobalScreenshoter;
import Tools.GlobalScreenshots;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WorkspaceTest {

    private static IOSDriver driver;
    GlobalScreenshots globalScreenshoter;

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
    public void RotateVideo() throws InterruptedException {
        Distribute.message("TESTCASE: Rotate video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.applyHelp();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.BeforeScreenLocal(workspace.getPlayer(), true);
        workspace.rotateVideo();
        workspace.AfterScreenLocal(workspace.getPlayer(), true);
        workspace.closeProject();
        workspace.SubmitScreenshots();
    }

    @Test(dependsOnMethods = {"RotateVideo"}, alwaysRun = true)
    public void AvaliableCancelButtonAfterRotate() throws InterruptedException {
        Distribute.message("TESTCASE: Rotate video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.rotateVideo();
        boolean temp = workspace.isUndoAvaliable();
        workspace.closeProject();
        Assert.assertTrue(temp);
    }

    @Test(dependsOnMethods = {"AvaliableCancelButtonAfterRotate"}, alwaysRun = true)
    public void RotateVideoAndUndo() throws InterruptedException {
        Distribute.message("TESTCASE: Rotate video and Undo");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.BeforeScreenLocal(workspace.getPlayer(), false);
        workspace.rotateVideo();
        workspace.undoAction();
        workspace.AfterScreenLocal(workspace.getPlayer(), false);
        workspace.closeProject();
        workspace.SubmitScreenshots();
    }

    @Test(dependsOnMethods = {"RotateVideoAndUndo"}, alwaysRun = true)
    public void AddVideoAtWorkspaceToProjectWithVideo() throws InterruptedException {
        Distribute.message("TESTCASE: Add video at Workspace to project with video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        gallery.openVideoGallery();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b - (a * 2)) > 1)
            Assert.fail("Не сопоставляются длительности видео после добавления видео в проект");
    }

    @Test (dependsOnMethods = {"AddVideoAtWorkspaceToProjectWithVideo"}, alwaysRun = true)
    public void AddPhotoAtWorkspaceToProjectWithVideo() throws InterruptedException {
        Distribute.message("TESTCASE: Add photo at Workspace to project with video");
        Gallery gallery=new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        gallery.openPhotoGallery();
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (a+5 != b) Assert.fail("Не сопоставляются длительности");
    }

    @Test (dependsOnMethods = {"AddPhotoAtWorkspaceToProjectWithVideo"}, alwaysRun = true)
    public void AddPhotoAtWorkspaceToProjectWithPhoto() throws InterruptedException {
        Distribute.message("TESTCASE: Add photo at Workspace to project with photo");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        gallery.openPhotoGallery();
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (a*2 != b) Assert.fail("Не сопоставляются длительности");
    }

    @Test (dependsOnMethods = {"AddPhotoAtWorkspaceToProjectWithPhoto"}, alwaysRun = true)
    public void AddVideoAtWorkspaceToProjectWithPhoto() throws InterruptedException {
        Distribute.message("TESTCASE: Add video at Workspace to project with photo");
        Gallery gallery = new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        gallery.openVideoGallery();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (a == b) Assert.fail("Не сопоставляются длительности");
    }

    @Test (dependsOnMethods = {"AddVideoAtWorkspaceToProjectWithPhoto"}, alwaysRun = true)
    public void AvaliableCancelButtonAfterAddingVideo() throws InterruptedException {
        Distribute.message("TESTCASE: Avaliable Cancel button after adding video");
        Gallery gallery=new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        gallery.openVideoGallery();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        boolean temp = workspace.isUndoAvaliable();
        workspace.closeProject();
        Assert.assertTrue(temp);
    }


    @Test (dependsOnMethods = {"AvaliableCancelButtonAfterAddingVideo"}, alwaysRun = true)
    public void AddVideoAtWorkspaceToProjectAndUndo() throws InterruptedException {
        Distribute.message("TESTCASE: Add video at Workspace to project and Undo");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        gallery.openVideoGallery();
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        workspace.undoAction();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b-a) >= 2) Assert.fail("Не сопоставляются длительности видео после добавления и отмены");
    }

    @Test (dependsOnMethods = {"AddVideoAtWorkspaceToProjectAndUndo"}, alwaysRun = true)
    public void ChangeAddButtonAfterClicking() throws  InterruptedException {
        Distribute.message("TESTCASE: Change add button after clicking");
        Gallery gallery = new Gallery(driver);
        gallery.BeforeScreenLocal(gallery.getAddButton(),true);
        gallery.addButtonClick();
        gallery.AfterScreenLocal(gallery.getAddButton(),true);
        gallery.addButtonClick();
        gallery.SubmitScreenshots();
    }

    @Test (dependsOnMethods = {"ChangeAddButtonAfterClicking"}, alwaysRun = true)
    public void VisiblePhotoAndVideoBUttonAfterClickingAdd() throws  InterruptedException {
        Distribute.message("TESTCASE: Visibility of photo and video button after clicking add button");
        Gallery gallery = new Gallery(driver);
        gallery.addButtonClick();
        boolean result = gallery.isAddPhotoDisplayed() && gallery.isAddVideoDisplayed();
        gallery.addButtonClick();
        Assert.assertTrue(result);
    }

    @Test (dependsOnMethods = {"VisiblePhotoAndVideoBUttonAfterClickingAdd"}, alwaysRun = true)
    public void DuplicateVideo() throws  InterruptedException {
        Distribute.message("TESTCASE: Duplicate video");
        Gallery gallery=new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b-(a*2)) > 1) Assert.fail("Не сопоставляются длительности видео после дублирования");
    }

    @Test (dependsOnMethods = {"DuplicateVideo"}, alwaysRun = true)
    public void DuplicatePhoto() throws  InterruptedException {
        Distribute.message("TESTCASE: Duplicate photo");
        Gallery gallery=new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b-(a*2)) != 0) Assert.fail("Не сопоставляются длительности видео после дублирования");
    }

    @Test (dependsOnMethods = {"DuplicatePhoto"}, alwaysRun = true)
    public void DoubleDuplicatePhoto() throws  InterruptedException {
        Distribute.message("TESTCASE: Double duplicate photo");
        Gallery gallery=new Gallery(driver);
        gallery.openPhotoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkPhotoItem(1);
        gallery.addPhotosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        workspace.duplicateVideo();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b-(a*3)) != 0) Assert.fail("Не сопоставляются длительности видео после дублирования");
    }


    @Test (dependsOnMethods = {"DoubleDuplicatePhoto"}, alwaysRun = true)
    public void AvaliableCancelAfterDuplicateVideo() throws  InterruptedException {
        Distribute.message("TESTCASE: Avaliable undo after duplicate video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.duplicateVideo();
        boolean temp = workspace.isUndoAvaliable();
        workspace.closeProject();
        Assert.assertTrue(temp);
    }

    @Test (dependsOnMethods = {"AvaliableCancelAfterDuplicateVideo"}, alwaysRun = true)
    public void DuplicateVideoAndUndo() throws InterruptedException {
        Distribute.message("TESTCASE: Duplicate video and Undo");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        workspace.undoAction();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b - a) > 1) Assert.fail("Не сопоставляются длительности видео после дублирования и Undo");
    }

    @Test (dependsOnMethods = {"DuplicateVideoAndUndo"}, alwaysRun = true)
    public void DeleteVideoAtWorkspace() throws InterruptedException {
        Distribute.message("TESTCASE: Delete video at Workspace");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        workspace.deleteVideo();
        int b = workspace.getDuration();
        workspace.closeProject();
        if (Math.abs(b-a) > 1) Assert.fail("Не сопоставляются длительности видео после удаления");
    }


    @Test (dependsOnMethods = {"DeleteVideoAtWorkspace"}, alwaysRun = true)
    public void DeleteLastClipOnTimeline() throws InterruptedException {
        Distribute.message("TESTCASE: Delete last clip on timeline at Workspace");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        globalScreenshoter.BeforeScreenGlobal(driver,true);
        workspace.deleteVideo();
        globalScreenshoter.AfterScreenGlobal(driver,true);
        globalScreenshoter.SubmitScreenshots();
    }


    @Test (dependsOnMethods = {"DeleteLastClipOnTimeline"}, alwaysRun = true)
    public void AvaliableButtonCancelAfterDelete() throws InterruptedException {
        Distribute.message("TESTCASE: Avaliable undo after deleting video");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.checkVideoItem(2);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.deleteVideo();
        boolean temp = workspace.isUndoAvaliable();
        workspace.closeProject();
        Assert.assertTrue(temp);
    }


    @Test (dependsOnMethods = {"AvaliableButtonCancelAfterDelete"}, alwaysRun = true)
    public void DeleteVideoAtWorkspaceWithUndo() throws InterruptedException {
        Distribute.message("TESTCASE: Delete video at Workspace with Undo");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        int a = workspace.getDuration();
        workspace.duplicateVideo();
        workspace.deleteVideo();
        int b = workspace.getDuration();
        if (Math.abs(b-a*2) > 1) Assert.fail("Не сопоставляются длительности видео после удаления и копирования");
        workspace.closeProject();
    }

    @Test (dependsOnMethods = {"DeleteVideoAtWorkspaceWithUndo"}, alwaysRun = true)
    public void OpenCloseSwipeTip() throws InterruptedException {
        Distribute.message("TESTCASE: Open and close SwipeTip");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace = new Workspace(driver);
        workspace.changeTimlinePosition(0.5);
        workspace.cutVideo();
        globalScreenshoter.BeforeScreenGlobal(driver,true);
        workspace.closeTipAboutSwipe();
        globalScreenshoter.AfterScreenGlobal(driver,true);
        workspace.closeProject();
        globalScreenshoter.SubmitScreenshots();
    }

    @Test (dependsOnMethods = {"OpenCloseSwipeTip"}, alwaysRun = true)
    public void SetSquareVideoAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Set aspect ratio: square (1:1) video and check difference");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int fHeight = workspace.getPlayer().getSize().getHeight();
        int fWidth = workspace.getPlayer().getSize().getWidth();
        workspace.changeAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        int sHeight = workspace.getPlayer().getSize().getHeight();
        int sWidth = workspace.getPlayer().getSize().getWidth();
        boolean differ = ((fHeight != sHeight)|(fWidth != sWidth));
        workspace.closeProject();
        Assert.assertTrue(differ);
    }


    @Test (dependsOnMethods = {"SetSquareVideoAspectRatio"}, alwaysRun = true)
    public void SetVerticalVideoInstagramAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Set aspect ratio: vertical for instagram (4:5) 3 and check difference");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int fHeight = workspace.getPlayer().getSize().getHeight();
        int fWidth = workspace.getPlayer().getSize().getWidth();
        workspace.changeAspectRatio(AspectRatio.VERTICAL_INSTAGRAM);
        int sHeight = workspace.getPlayer().getSize().getHeight();
        int sWidth = workspace.getPlayer().getSize().getWidth();
        boolean differ = ((fHeight != sHeight)|(fWidth != sWidth));
        workspace.closeProject();
        Assert.assertTrue(differ);
    }

    @Test (dependsOnMethods = {"SetVerticalVideoInstagramAspectRatio"}, alwaysRun = true)
    public void SetVerticalVideoAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Set aspect ratio: vertical (9:16) video and check difference");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.SQUARE_INSTAGRAM);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int fHeight = workspace.getPlayer().getSize().getHeight();
        int fWidth = workspace.getPlayer().getSize().getWidth();
        workspace.changeAspectRatio(AspectRatio.VERTICAL_PHONE);
        int sHeight = workspace.getPlayer().getSize().getHeight();
        int sWidth = workspace.getPlayer().getSize().getWidth();
        boolean differ = ((fHeight != sHeight)|(fWidth != sWidth));
        workspace.closeProject();
        Assert.assertTrue(differ);
    }

    @Test (dependsOnMethods = {"SetVerticalVideoAspectRatio"}, alwaysRun = true)
    public void SetStandartWidescreenAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Set aspect ratio: widescreen (16:9) video and check difference");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int fHeight = workspace.getPlayer().getSize().getHeight();
        int fWidth = workspace.getPlayer().getSize().getWidth();
        workspace.changeAspectRatio(AspectRatio.WIDESCREEN);
        int sHeight = workspace.getPlayer().getSize().getHeight();
        int sWidth = workspace.getPlayer().getSize().getWidth();
        boolean differ = ((fHeight != sHeight)|(fWidth != sWidth));
        workspace.closeProject();
        Assert.assertTrue(differ);
    }

    @Test (dependsOnMethods = {"SetStandartWidescreenAspectRatio"}, alwaysRun = true)
    public void AvaliableUndoAfterSettingAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Avaliable undo after setting aspect ratio");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        workspace.changeAspectRatio(AspectRatio.WIDESCREEN);
        boolean temp = workspace.isUndoAvaliable();
        workspace.closeProject();
        Assert.assertTrue(temp);

    }

    @Test (dependsOnMethods = {"AvaliableUndoAfterSettingAspectRatio"}, alwaysRun = true)
    public void UndoAspectRatio() throws InterruptedException {
        Distribute.message("TESTCASE: Undo setting aspect ratio");
        Gallery gallery = new Gallery(driver);
        gallery.openVideoGallery();
        gallery.selectAspectRatio(AspectRatio.VERTICAL_PHONE);
        gallery.checkVideoItem(1);
        gallery.addVideosToProject();
        Workspace workspace=new Workspace(driver);
        int fHeight = workspace.getPlayer().getSize().getHeight();
        int fWidth = workspace.getPlayer().getSize().getWidth();
        workspace.changeAspectRatio(AspectRatio.WIDESCREEN);
        workspace.undoAction();
        int sHeight = workspace.getPlayer().getSize().getHeight();
        int sWidth = workspace.getPlayer().getSize().getWidth();
        boolean differ = ((fHeight == sHeight)&(fWidth == sWidth));
        workspace.closeProject();
        Assert.assertTrue(differ);
    }

}

