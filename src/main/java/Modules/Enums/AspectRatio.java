package Modules.Enums;

public enum  AspectRatio {

    SQUARE_INSTAGRAM(1,"squareRatio"),
    VERTICAL_INSTAGRAM(2,"verticalRatioInstagram"),
    VERTICAL_PHONE(3,"verticalRatio"),
    WIDESCREEN(4,"horizontalRatio");

    private int id;
    private String locator;

    AspectRatio(int id, String locator){
        this.id = id;
        this.locator = locator;
    }

    public int getId(){
        return id;
    }

    public String getLocator() {
        return locator;
    }
}
