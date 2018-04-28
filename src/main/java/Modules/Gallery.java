package Modules;

import Modules.Enums.AspectRatio;
import Tools.Distribute;
import Tools.ScreenShoter;
import Tools.ScreenerList;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class Gallery implements IGallery {
    private By gallery_title=By.name("Add videos");
    private By gallery_add_videos=By.name("Start editing");
    private String videoitem_start="//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[";
    private String videoitem_end="]/XCUIElementTypeOther/XCUIElementTypeImage";

    private String videoitem_delete_start = "(//XCUIElementTypeButton[@name=\"ic gallery basket\"])[";
    private String videoitem_delete_end = "]";

    private String deleteApply_ru = "Удалить";
    private String deleteApply_en = "Delete";


    private String add_videos_to_project = "//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeButton";

    private String add_photos_to_project = "//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/" +
            "XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeButton";

    private String videoitem_duration_start = "//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/XCUIElementTypeWindow[1]/" +
            "XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[";
    private String videoitem_duration_end = "]/XCUIElementTypeOther/XCUIElementTypeStaticText";

    private By gallery_video_capture=By.name("videocam");
    private By move_to_top=By.xpath("//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/" +
            "XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther/XCUIElementTypeImage");

    public static VideoCapture capture;

    private String add_photo_camera = "icon photocamera";
    private String add_video_camera = "icon videocamera";
    private String permission_notification_ru = "Разрешить";
    private String permission_notification_en = "Allow";

    private String permission_photo_video_en = "OK";
    private String permission_photo_video_ru = "Разрешить";

    private String add_photo_video = "icon add video";
    private String add_photo = "icon photocamera";
    private String add_video = "icon videocamera";

    private String applyHelp_ru = "Понятно";
    private String applyHelp_en = "Got it";


    private String aspectRatio_cell_selected = "/XCUIElementTypeImage[@name=\"ic_check\"]";

    private String photoitem_start = "(//XCUIElementTypeCell[@name=\"MOVGalleryPhotoCell\"])[";
    private String photoitem_end = "]/XCUIElementTypeOther/XCUIElementTypeImage[2]";

    private String full_photoitem_start = "(//XCUIElementTypeCell[@name=\"MOVGalleryPhotoCell\"])[";
    private String full_photoitem_end = "]/XCUIElementTypeOther/XCUIElementTypeImage[1]";


    private String livePhoto_start = "(//XCUIElementTypeCell[@name=\"MOVGalleryPhotoCell\"])[";
    private String livePhoto_end = "]/XCUIElementTypeOther/XCUIElementTypeImage[3]";


    private ScreenerList screenerList;



    private final WebDriver.Timeouts timeout;
    private final IOSDriver driver;
    private int countVideoItem;
    private int avaliableVideos;
    private int avaliablePhotos;
    private int countPhotoItem;
    private int countLivePhotoItem;
    private int videoDuration;
    private int photoDuration;
    private int summaryDuration;

    public Gallery(IOSDriver driver) {
        this.driver = driver;
        this.screenerList = new ScreenerList();
        this.countVideoItem = 0;
        this.countPhotoItem = 0;
        this.videoDuration = 0;
        this.countLivePhotoItem = 0;
        this.timeout = driver.manage().timeouts();

        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        if (driver.findElementsByName(permission_notification_en).size()>0)
            driver.findElementByName(permission_notification_en).click();
        else if (driver.findElementsByName(permission_notification_ru).size()>0)
            driver.findElementByName(permission_notification_ru).click();
        avaliableVideos = 0;
        avaliablePhotos = 0;
        allowPermission();
        allowPermission();

    }

    @Override
    public void openVideoGallery() {
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        driver.findElementByName(add_photo_video).click();
        driver.findElementByName(add_video).click();
    }

    @Override
    public void openPhotoGallery() {
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        driver.findElementByName(add_photo_video).click();
        driver.findElementByName(add_photo).click();
    }

    @Override
    public void checkVideoItem(int id) {
        if (avaliableVideos == 0)
            avaliableVideos = countVideoItems();
        countVideoItem++;
        videoDuration+=ParseTime(findDuration(avaliableVideos-(id-1)));
        Distribute.message("GALLERY: Summary duration: " + videoDuration);
        driver.findElementByXPath(videoitem_duration_start+(avaliableVideos-(id-1))+videoitem_duration_end).click();
    }

    @Override
    public void checkAllVideos() {
        if (avaliableVideos == 0)
            avaliableVideos = countVideoItems();
        for (int i=avaliableVideos;i>0;i--){
            countVideoItem++;
            videoDuration+=ParseTime(findDuration(i));
            driver.findElementByXPath(videoitem_duration_start+i+videoitem_duration_end).click();
        }
        Distribute.message("GALLERY: Summary duration: " + videoDuration);
    }

    @Override
    public void checkPhotoItem(int id) {
        if (avaliablePhotos ==0) {
            avaliablePhotos = countPhotoItems();
        }
        if (isPhotoLive(id))
            Distribute.message("ITS LIVE PHOTO");
        else
            photoDuration+= 5;
        driver.findElementByXPath(photoitem_start + (avaliablePhotos-(id-1))  + photoitem_end).click();
        Distribute.message("GALLERY: Checked " + (countPhotoItem+1) + " photos");
        countPhotoItem++;
    }

    @Override
    public void checkAllPhotos() {
        if (avaliablePhotos ==0) {
            avaliablePhotos = countPhotoItems();
        }
        for (int i=avaliablePhotos;i>0;i--){
            if (!isPhotoLive(i)){
                photoDuration+= 5;
                driver.findElementByXPath(photoitem_start + i + photoitem_end).click();
            }
        }
    }

    @Override
    public void deleteVideoItem(int id) {
        if (avaliableVideos == 0)
            avaliableVideos = countVideoItems();
        driver.findElementByXPath(videoitem_delete_start+(avaliableVideos-(id-1))+videoitem_delete_end).click();
    }

    @Override
    public void deletePhotoItem(int id) {

    }

    @Override
    public void checkVideoCapture() {

    }

    @Override
    public void checkPhotoCapture() {

    }

    @Override
    public void openPreviewVideo() {

    }

    @Override
    public void closePreviewVideo() {

    }

    @Override
    public void openPreviewPhoto() {

    }

    @Override
    public void closePreviewPhoto() {

    }

    @Override
    public int getAllDuration() {
        return videoDuration;
    }

    @Override
    public int getVideoDuration() {
        return videoDuration;
    }

    @Override
    public int getCountVideoItems() {
        return countVideoItem;
    }

    @Override
    public int getPhotoDuration() {
        return photoDuration;
    }

    @Override
    public int getCountPhotoItems() {
        return countPhotoItem;
    }

    @Override
    public void addVideosToProject() {
        driver.findElementByXPath(add_videos_to_project).click();
    }

    @Override
    public void addPhotosToProject() {
        driver.findElementByXPath(add_photos_to_project).click();
    }

    @Override
    public void applyHelp() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driver.findElementsByName(applyHelp_en).size()>0)
            driver.findElementByName(applyHelp_en).click();
        else if (driver.findElementsByAccessibilityId(applyHelp_ru).size()>0)
            driver.findElementByAccessibilityId(applyHelp_ru).click();
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
    }

    @Override
    public void selectAspectRatio(AspectRatio aspectRatio) {
//        timeout.implicitlyWait(100, TimeUnit.MILLISECONDS);
//        for (int i=1;i<=AspectRatio.values().length;i++){
//                if (driver.findElementsByXPath(aspectRatio_cell_start + (i+1) + aspectRatio_cell_end + aspectRatio_cell_selected).size()>0){
//                    break;
//                }
//        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElementByAccessibilityId(aspectRatio.getLocator());
//        Distribute.error("CLICKEED");
//        timeout.implicitlyWait(100, TimeUnit.MILLISECONDS);
        driver.findElementByAccessibilityId("acceptAspectRatio").click();
//        timeout.implicitlyWait(100, TimeUnit.MILLISECONDS);
        allowPermission();
        allowPermission();
    }

    @Override
    public int getCountAvaliableVideos() {
        return countVideoItems();
    }

    @Override
    public int getCountAvaliablePhotos() {
        return countPhotoItems();
    }

    @Override
    public void closeGallery() {
        driver.findElementByName("Отмена").click();

    }

    //TODO: добавить
    @Override
    public WebElement getAddButton() {
        return null;
    }

    @Override
    public WebElement getAddVideoButton() {
        return null;
    }

    @Override
    public WebElement getAddPhotoButton() {
        return null;
    }

    @Override
    public boolean isAddVideoDisplayed() {
        return false;
    }

    @Override
    public boolean isAddPhotoDisplayed() {
        return false;
    }


    //TODO: добавить
    @Override
    public void addButtonClick() {

    }

    public void allowPermission(){
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        if (driver.findElementsByName(permission_photo_video_en).size()>0)
            driver.findElementByName(permission_photo_video_en).click();
        else if (driver.findElementsByName(permission_photo_video_ru).size()>0)
            driver.findElementByName(permission_photo_video_ru).click();
    }

    private String findDuration(int id){
        String s = driver.findElementByXPath(videoitem_duration_start+id+videoitem_duration_end).getText();
        System.out.println(s);
        return s;
    }

    private int countVideoItems(){
        timeout.implicitlyWait(1,TimeUnit.SECONDS);
        int count = 0;
        while (driver.findElementsByXPath(videoitem_start + (count+1) + videoitem_end).size()>0)
            count++;
        Distribute.message("VIDEOS AVALIABLE: "+(count-1));
        return count-1;

    }private int countPhotoItems(){
        int count = 0;
        while (driver.findElementsByXPath(photoitem_start + (count+1)  + photoitem_end).size()>0)
            count++;
        Distribute.message("PHOTOS AVALIABLE: "+(count));
        return count;
    }

    private void isDisplayed(int id){
        boolean a = driver.findElementByXPath(videoitem_start + id + videoitem_end).isEnabled();
        Distribute.message("ENABLED: " +a);
//        boolean b = driver.findElementByXPath(videoitem_start + id + videoitem_end).isSelected();
//        Distribute.message("SELECTED: " +b);
        boolean c = driver.findElementByXPath(videoitem_start + id + videoitem_end).isDisplayed();
        Distribute.message("DISPLAYED: " +c);
    }

    private boolean isPhotoLive(int id){
        return driver.findElementsByXPath(livePhoto_start+(avaliablePhotos-(id-1))+livePhoto_end).size()>0;
    }

    /**
     * Конвертация времени типа mm:ss в секунды
     */
    private int ParseTime(String timestring){
        String[] time= timestring.split(":");
        return Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
    }

    private void acceptDeleteVideoItem(){
        if (driver.findElementsByName(deleteApply_en).size()>0)
            driver.findElementByName(deleteApply_en).click();
        else if (driver.findElementsByName(deleteApply_ru).size()>0)
            driver.findElementByName(deleteApply_ru).click();
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

    public WebElement getVideoItem(int id){
        if (avaliableVideos == 0)
            avaliableVideos = countVideoItems();
        return driver.findElementByXPath(videoitem_start + (avaliableVideos-(id-1)) + videoitem_end);
    }

    public WebElement getPhotoItem(int id) {
        if (avaliablePhotos == 0)
            avaliablePhotos = countPhotoItems();
        return driver.findElementByXPath(full_photoitem_start + (avaliablePhotos-(id-1)) + full_photoitem_end);
    }
}
