public class ShortAnswer extends Essay{

    private int wordLimit = 1;

    public ShortAnswer(String prompt) {
        super(prompt);
    }

    public void take(boolean printPrompt){

        int questionsRespondedTo = 0;
        ResponsesCorrectAnswer shortAnswerResponse = new ResponsesCorrectAnswer();

        while (neededResponses > questionsRespondedTo) {
            String userInput;

            do {
                System.out.println("The word limit is: "+ wordLimit);
                userInput = UserInput.getUserInput(printPrompt ? questionPrompt : "");
            }while (countWords(userInput) > wordLimit);


            shortAnswerResponse.add(userInput);
            questionsRespondedTo +=1;
        }

        getResponseList().add(shortAnswerResponse);

        System.out.printf("This question has already been answered a maximum of %d times\n", neededResponses);

    }

    public void take() {
        take(true);
    }

    public int countWords(String userInput) {
        String[] words = userInput.split(" ");
        return words.length;
    }

    public void create() {
        setShortAnswerPrompt();
        setWordLimit();
        setNeededResponses();
    }

    public void edit() {

        Menu questionMenu = new Menu();
        questionMenu.add("1) Modify this prompt");
        questionMenu.add("2) Modify needed responses");
        questionMenu.add("3) Modify word limit");
        questionMenu.add("4) Go back");

        boolean finished = false;

        while (!finished) {
            questionMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, questionMenu.menuItems.size())) {
                int menuSelectionInt = questionMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        setShortAnswerPrompt();
                        break;
                    case 2:
                        setNeededResponses();
                        break;
                    case 3:
                        setWordLimit();
                        break;
                    case 4:
                        finished = true;

                }

            }
        }

    }

    public void setWordLimit()
    {
        boolean gotWordLimit = false;
        while (!gotWordLimit) {
            try {
                this.wordLimit = Integer.parseInt(UserInput.getUserInput("Type the desired word limit above 0: "));
                gotWordLimit = wordLimit > 0;
            } catch (NumberFormatException e) {
                System.out.println("Not an Integer");
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

    public void setShortAnswerPrompt() {
        questionPrompt = UserInput.getUserInput("Type a new Short Answer prompt: ");

    }
}
