package Modules;


import Modules.Enums.AspectRatio;
import Tools.LocalScreenshots;
import org.openqa.selenium.WebElement;

public interface IWorkpace extends LocalScreenshots {

    void openSettings();
    void closeProject();
    void saveVideo();

    void changeAspectRatio(AspectRatio ratio);
    void changeSeekBarPosition();

    void openAudio();
    void rotateVideo();
    void openStickers();
    void openText();
    void openSpeed();
    void openColor();
    void openTransitions();
    void duplicateVideo();
    void deleteVideo();
    void cutVideo();
    void addVideo();
    void undoAction();
    void playVideo();

    int getDuration();

    WebElement getPlayer();


    boolean isUndoAvaliable();
    void changeTimlinePosition(double n);
    void closeTipAboutSwipe();

}
