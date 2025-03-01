import java.util.ArrayList;
import java.util.HashMap;

public class TrueFalse extends MultipleChoice{



    public TrueFalse(String prompt) {
        super(prompt);
        choices.add("True");
        choices.add("False");
    }

    public void create() {
        setMultipleChoicePrompt();
    }

    public void edit() {
        Menu questionMenu = new Menu();

        questionMenu.add("1) Modify this prompt");
        questionMenu.add("2) Modify needed responses");

        questionMenu.add("3) Display all choice options");
        questionMenu.add("4) Go back");

        boolean finished = false;
        while (!finished){
            questionMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, questionMenu.menuItems.size())) {
                int menuSelectionInt = questionMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        setMultipleChoicePrompt();
                        break;
                    case 2:
                        setNeededResponses();
                        break;
                    case 3:
                        this.display();
                        break;
                    case 4:
                        finished = true;

                }
            }
        }

    }
}
