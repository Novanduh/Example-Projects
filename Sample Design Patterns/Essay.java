import java.util.HashMap;

public class Essay extends Questions {


    public Essay(String prompt) {
        super(prompt);
    }

    public void take(boolean printPrompt){

        int questionsRespondedTo = 0;
        ResponsesCorrectAnswer essayResponse = new ResponsesCorrectAnswer();

        while (neededResponses > questionsRespondedTo) {
            String userInput = UserInput.getUserInput(printPrompt ? questionPrompt : "");

            essayResponse.add(userInput);

            questionsRespondedTo +=1;

        }

        getResponseList().add(essayResponse);

        System.out.printf("This question has already been answered a maximum of %d times\n", neededResponses);

    }

    public void take() {
        take(true);
    }

    public void create() {
        setEssayPrompt();
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
                        setEssayPrompt();
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


    private void setNeededResponses() {
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

    public void setEssayPrompt() {
        questionPrompt = UserInput.getUserInput("Type a new Essay prompt: ");

    }

    public void displayTabulated(HashMap<String, Integer> tabulatedValues) {
        System.out.println(this.questionPrompt);
        System.out.println();

        tabulatedValues.forEach((key, value) -> {
            System.out.println(key + "\n");
        });
    }
}
