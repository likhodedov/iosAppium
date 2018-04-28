package Tools;

import org.openqa.selenium.WebElement;


public interface LocalScreenshots {
    public void BeforeScreenLocal(WebElement element, boolean inverted_logic) throws InterruptedException;
    public void AfterScreenLocal(WebElement element, boolean inverted_logic) throws InterruptedException;
    public void SubmitScreenshots();
}
