package Tools;

import org.testng.Assert;

import java.util.ArrayList;

public class ScreenerList {
    private ArrayList<Screener> list;

    //Конструктор нашего листа.
    public ScreenerList(){
        this.list = new ArrayList<Screener>();
    }

    /*
    1)Если список пуст то создаем новый объект Screener и помещаем туда путь к сриншоту Before
    2) Если список не пуст, то ищем первую пустую ячейку Before в каждом объекте Screener и добавляем
    3) Если список не пуст и все существующие ячейки заполнены, то создаем новый объект Screener, добавляем туда путь до файла и добавляем в наш список
     */
    public void AddBefore(String path,boolean inverted_logic) throws InterruptedException {
        if (list.size() == 0){
            list.add(new Screener(inverted_logic));
            list.get(list.size() - 1).SetBeforeScreenPath(path);
        }
        else {
            if (list.get(list.size() - 1).isBeforeEmpty()) {
                for (int i = 0; i < list.size(); i++)  {
                    if (list.get(i).isBeforeEmpty()) {
                        list.get(i).SetBeforeScreenPath(path);
                        break;
                    }
                }
            }
            else if (list.get(list.size() - 1).isFull()) {
                list.add(new Screener(inverted_logic));
                list.get(list.size() - 1).SetBeforeScreenPath(path);
            }
        }
        CompareList();
    }

    /*
    1)Если список пуст то создаем новый объект Screener и помещаем туда путь к сриншоту After
    2) Если список не пуст, то ищем первую пустую ячейку After в каждом объекте Screener и добавляем
    3) Если список не пуст и все существующие ячейки заполнены, то создаем новый объект Screener, добавляем туда путь до файла и добавляем в наш список
     */
    public void AddAfter(String path,boolean inverted_logic) throws InterruptedException {
        if (list.size() == 0){
            list.add(new Screener(inverted_logic));
            list.get(list.size() - 1).SetAfterScreenPath(path);
        }
        else {
            if (list.get(list.size() - 1).isAfterEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isAfterEmpty()) {
                        list.get(i).SetAfterScreenPath(path);
                        break;
                    }
                }
            }
            else if (list.get(list.size() - 1).isFull()){
                list.add(new Screener(inverted_logic));
                list.get(list.size() - 1).SetAfterScreenPath(path);
            }
        }
        CompareList();
    }
    /*
    Ищем все записи где есть скриншоты Before и After вместе и сравниваем их.
     */
    private void CompareList(){
        for (int i = list.size() - 1;i >= 0;i--)
            if ((!list.get(i).isBeforeEmpty()) & (!list.get(i).isAfterEmpty())) list.get(i).CompareImages();
    }
    //Удаление ячейки
    private void DeleteSubmittedLine(int index){
        list.remove(index);
    }

    // Очистка списка, если в объекте Screener скриншоты были сравнены, то удаляем этот объект из списка
    public void processList(){
        for (int i = list.size() - 1;i >= 0;i--)
            if (list.get(i).GetCompare())
                Assert.assertFalse(!list.get(i).GetStatus());
    }
    public void clearList(){
        list.clear();
    }
}
