import java.util.HashMap;

public class Date extends Questions{

    public Date(String questionPrompt) {
        super(questionPrompt);
    }

    public void take(boolean printPrompt){

        int questionsRespondedTo = 0;

        ResponsesCorrectAnswer dateResponse = new ResponsesCorrectAnswer();

        while (neededResponses > questionsRespondedTo) {
            if(printPrompt) {
                System.out.println(questionPrompt);
            }

            String userDay;
            String userMonth;
            String userYear;

            do {
                userDay = UserInput.getUserInput("Day: ");
                userMonth = UserInput.getUserInput("Month: ");
                userYear = UserInput.getUserInput("Year: ");
            }while (!(InputValidator.validateThisMenuChoice(userDay, 31) && InputValidator.validateThisMenuChoice(userMonth, 12) && InputValidator.validateThisMenuChoice(userYear, Integer.MAX_VALUE)));

            dateResponse.add(userMonth + "/" + userDay + "/" + userYear);
            questionsRespondedTo +=1;

        }

        getResponseList().add(dateResponse);

        System.out.printf("This question has already been answered a maximum of %d times\n", neededResponses);

    }

    public void take() {
        take(true);
    }

    public void create() {
        setDatePrompt();
        setNeededResponses();
    }
    public void edit() {

        Menu questionMenu = new Menu();
        questionMenu.add("1) Modify this prompt");
        questionMenu.add("2) Modify needed responses");
        questionMenu.add("3) Go back");

        boolean finished = false;

        while (!finished) {
            questionMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, questionMenu.menuItems.size())) {
                int menuSelectionInt = questionMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        setDatePrompt();
                        break;
                    case 2:
                        setNeededResponses();
                        break;
                    case 3:
                        finished = true;

                }

            }
        }

    }
    public void setNeededResponses() {
        boolean gotNeededResponses = false;
        while (!gotNeededResponses) {
            try {
                neededResponses = Integer.parseInt(UserInput.getUserInput("Type the number of needed responses for this question: "));
                gotNeededResponses = neededResponses > 0;

            } catch (NumberFormatException e) {
                System.out.println("Not an Integer");
            }

        }
    }

    public void setDatePrompt() {
        questionPrompt = UserInput.getUserInput("Type a new Date prompt: ");

    }

    public void displayTabulated(HashMap<String, Integer> tabulatedValues) {
        System.out.println(this.questionPrompt);
        System.out.println();

        tabulatedValues.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            System.out.println("");
        });
    }

}
