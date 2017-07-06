package Modules;

import org.openqa.selenium.WebDriver;

/**
 * Created by test_user_1 on 05.07.17.
 */
public class VideoCapture {

    private final WebDriver driver;

    public VideoCapture(WebDriver driver) {
        this.driver = driver;
    }

    public VideoCapture CaptureVideo (int seconds) throws InterruptedException {
        Thread.sleep(seconds*1000);
        return this;
    }

    public VideoCapture StopCaptureVideo (){
        return this;
    }

    public VideoCapture ChangeCamera() {
        return this;
    }
    public VideoCapture CancelVideoCapture(){
        return this;
    }





}
