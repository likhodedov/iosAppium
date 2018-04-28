package Tools;

import org.testng.Assert;

import java.io.File;


public class Screener {
    private String beforescreenpath;
    private String afterscreenpath;
    private boolean iscompared;
    private boolean issame;
    private double diffpercent;
    private boolean inverted_logic;

    public Screener(boolean inverted_logic) {
        this.beforescreenpath = "";
        this.afterscreenpath = "";
        this.iscompared = false;
        this.issame=false;
        this.diffpercent=0.0;
        this.inverted_logic=inverted_logic;
    }
    public void SetCompared(){
        iscompared=true;
    }
    public void SetStatus(){
        issame=true;
    }
    public boolean GetCompare(){
       return iscompared;
   }
    public boolean GetStatus(){
       return issame;
   }
    public void SetBeforeScreenPath(String path){
        beforescreenpath=path;
    }
    public void SetAfterScreenPath(String path){
        afterscreenpath=path;
    }
    //TODO либо добавить варианты подтверждения идентичности (разные проценты) либо как-то по другому разрулить
    /*
    Сравниваем наши два изображения
    В зависимости от исхода перемещаем файлы в папки Success и Fail.
     */
    public void CompareImages(){
        if (isEmpty())
            Assert.fail("Невозможно проверить, один из path не заполнен");
        else {
            diffpercent = CompareImage.compare(beforescreenpath, afterscreenpath);
            iscompared = true;
            //Процент нужно подкорректировать
                if (diffpercent < 0.1) {
                    SetCompared();
                    if (!inverted_logic){
                        SetStatus();
                        MoveFiles("Success");
                    }
                    else{
                        MoveFiles("Fail");
                        Distribute.error("Скриншоты сошлись, когда не должны были");
                    }
                }
                else {
                    SetCompared();
                    if (!inverted_logic){
                        MoveFiles("Fail");
                        Distribute.error("Скриншоты не сошлись");
                    }
                    else{
                        MoveFiles("Success");
                        SetStatus();
                    }
                }
            }
    }
    public boolean isEmpty(){
        return (beforescreenpath.equals("")) | (afterscreenpath.equals(""));
    }
    public boolean isBeforeEmpty(){
        return beforescreenpath.equals("");
    }
    public boolean isAfterEmpty(){
        return afterscreenpath.equals("");
    }
    public boolean isFull(){
        return (!beforescreenpath.equals("")) | (!afterscreenpath.equals(""));
    }

    private void MoveFiles(String foldername){
        File x = new File(beforescreenpath);
        File y = new File(afterscreenpath);
        new File(System.getProperty("user.dir") + File.separator + "screenshots"+File.separator+foldername).mkdirs();
        x.renameTo(new File(System.getProperty("user.dir") + File.separator+"screenshots"+File.separator+foldername+File.separator+ x.getName()));
        y.renameTo(new File(System.getProperty("user.dir") + File.separator+"screenshots"+File.separator+foldername+File.separator + y.getName()));
        x.delete();
        y.delete();
    }
}
