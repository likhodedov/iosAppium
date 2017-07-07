package Modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by test_user_1 on 04.07.17.
 */

public class Gallery {
    private By gallery_title=By.name("Add videos");
    private By gallery_add_videos=By.name("Start editing");
    private String videoitem_start="//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[";
    private String videoitem_end="]";
    private By gallery_video_capture=By.name("videocam");
    private By move_to_top=By.xpath("//XCUIElementTypeApplication[@name=\"Movavi Clips\"]/" +
            "XCUIElementTypeWindow[1]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/" +
            "XCUIElementTypeOther/XCUIElementTypeCollectionView/XCUIElementTypeCell[2]/XCUIElementTypeOther/XCUIElementTypeImage");
    private String videoitem_duration="/XCUIElementTypeOther/XCUIElementTypeStaticText[2]";

    private final WebDriver driver;
    private int countVideoItem;
    private int videoduration;
    private int countLivePhotoItem;

    public Gallery(WebDriver driver) {
        this.driver = driver;
        this.countVideoItem = 0;
        this.videoduration = 0;
        this.countLivePhotoItem = 0;
        String s="Add videos";
        if (!s.equals(this.driver.findElement(gallery_title).getText())){
            throw new IllegalStateException("This is not the gallery page");
        }
    }

    public Gallery CheckVideoItem (int id){
        driver.findElement(By.xpath(videoitem_start+id+videoitem_end)).click();
        videoduration+=GetDurationVideoItem(videoitem_start+id+videoitem_end);
        countVideoItem++;
        System.out.print("\n"+"Video checked with id "+ id+"\n");
        return this;
    }

    //TODO: необходимо определять лайв фото
    public Gallery CheckLivePhotoItem (){
    countLivePhotoItem++;
        return this;
    }

    public Gallery CheckVideoCapture(){
        driver.findElement(gallery_video_capture).click();
        return this;
    }

    //TODO: Получаем длительность выбранного видео и возвращаем количество секунд
    private int GetDurationVideoItem(String source) {

        String duration=driver.findElement(By.xpath(source+videoitem_duration)).getText();
        System.out.print(duration);

        System.out.print("\n"+ParseTime(duration));
        return ParseTime(duration);
    }

    public int getVideoduration(){
        return videoduration;
    }

    public int getCountVideoItem(){
        return countVideoItem;
    }

    public Gallery ScrolltoTop(){
        driver.findElement(move_to_top).click();
        return this;
    }

    //TODO: Скролл к концу списка видео
    public Gallery ScrolltoBot(){
        return this;
    }

    public Gallery AddVideosToProject() {
        driver.findElement(gallery_add_videos).click();
        return this;
    }

    private int ParseTime (String timestring){
        String[] time= timestring.split(":");
        return Integer.parseInt(time[0])*60+Integer.parseInt(time[1]);
    }
}
