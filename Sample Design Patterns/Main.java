import java.io.*;
import java.util.ArrayList;



public class Main {
    public static void main(String[] args) {

        Menu mainMenu = new Menu();

        mainMenu.add("1) Survey");
        mainMenu.add("2) Test");
        mainMenu.add("3) Exit");

        while (true) {
            mainMenu.display();
            String menuSelection = UserInput.getUserInput("");

            if (InputValidator.validateThisMenuChoice(menuSelection, mainMenu.menuItems.size())) {

                int menuSelectionInt = mainMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        SurveyDriver.runSurvey();
                        break;
                    case 2:
                        TestDriver.runTest();
                        break;
                    case 3:
                        return;
                }
            }
        }


    }





}