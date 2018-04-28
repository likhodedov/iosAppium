package Modules;


import Modules.Enums.AspectRatio;
import Tools.LocalScreenshots;
import org.openqa.selenium.WebElement;

public interface IGallery extends LocalScreenshots {

    void openVideoGallery();

    void openPhotoGallery();

    void checkVideoItem(int id);
    void checkAllVideos();

    void checkPhotoItem(int id);
    void checkAllPhotos();

    void deleteVideoItem(int id);

    void deletePhotoItem(int id);

    void checkVideoCapture();

    void checkPhotoCapture();

    void openPreviewVideo();
    void closePreviewVideo();

    void openPreviewPhoto();
    void closePreviewPhoto();

    int getAllDuration();

    int getVideoDuration();
    int getCountVideoItems();

    int getPhotoDuration();
    int getCountPhotoItems();

    void addVideosToProject();
    void addPhotosToProject();

    void applyHelp();

    void selectAspectRatio(AspectRatio aspectRatio);

    int getCountAvaliableVideos();
    int getCountAvaliablePhotos();

    void closeGallery();

    WebElement getAddButton();
    WebElement getAddVideoButton();
    WebElement getAddPhotoButton();

    boolean isAddVideoDisplayed();
    boolean isAddPhotoDisplayed();

    void addButtonClick();



}

