import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {

    ArrayList<String> menuItems;
    private static final long serialVersionUID = 987654324;

    public Menu() {
        this.menuItems = new ArrayList<>();
    }

    public void add(String item) {
        menuItems.add(item);
    }

    public void remove(String item) {
        menuItems.remove(item);
    }
    public void display() {
        System.out.println("Please type a number to select an option:");

        for(String item:menuItems) {
            System.out.println(item);
        }
    }

    public int convertStringToInt(String choice) {
        try {
            Integer.parseInt(choice);
        }
        catch (Exception numberFormatException) {
            throw numberFormatException;
        }
        return Integer.parseInt(choice);
    }

//    public void chooseItem(int choice) {
//
//
//    }
//
//    public void back() {
//
//    }
}
