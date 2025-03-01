import java.util.ArrayList;
import java.util.HashMap;

public class Matching extends Questions {

    ArrayList<String> leftMatches;
    ArrayList<String> rightMatches;

    public Matching(String questionPrompt) {
        super(questionPrompt);
        this.leftMatches = new ArrayList<>();
        this.rightMatches = new ArrayList<>();
    }

    public void take(boolean printPrompt) {

        int questionsRespondedTo = 0;

        ResponsesCorrectAnswer responses = new ResponsesCorrectAnswer();

        while (neededResponses > questionsRespondedTo) {
            int userLeftChoice = 1;
            String userRightChoice;
            char rightChoiceChar;
            String concatenatedResponse = "";

            display(printPrompt);
            for (String leftChoice : leftMatches) {
                userRightChoice = UserInput.getUserInput("For choice, " + leftChoice + " type the corresponding right choice: ");
                rightChoiceChar = userRightChoice.charAt(0);

                concatenatedResponse = concatenatedResponse.concat(String.format("%d %c \n", userLeftChoice, rightChoiceChar));


                userLeftChoice += 1;
            }
            responses.add(concatenatedResponse);
            questionsRespondedTo += 1;
        }

        getResponseList().add(responses);

        System.out.printf("This question has already been answered a maximum of %d times\n",neededResponses);

    }

    public void take() {
        take(true);
    }

    public void create() {
        setMatchingPrompt();
        int leftChoiceQuantity = 0;
        int rightChoiceQuantity = 0;
        boolean gotChoiceQuantity =false;
        while(!gotChoiceQuantity) {
            try {
                leftChoiceQuantity = Integer.parseInt(UserInput.getUserInput("Type the quantity of left multiple choice responses you would like to have: "));
                rightChoiceQuantity = Integer.parseInt(UserInput.getUserInput("Type the quantity of right multiple choice responses you would like to have: "));
                gotChoiceQuantity = (leftChoiceQuantity > 0) && (rightChoiceQuantity > 0);

            } catch (NumberFormatException e) {
                System.out.println("Not an integer");
            }
        }
        for (int i = 0; i < leftChoiceQuantity; i++) {
            addLeftChoice();
        }
        for (int i = 0; i < rightChoiceQuantity; i++) {
            addRightChoice();
        }
        setNeededResponses();
    }
    public void edit() {
        Menu questionMenu = new Menu();

        questionMenu.add("1) Modify this prompt");
        questionMenu.add("2) Modify needed responses");
        questionMenu.add("3) Add a Left choice");
        questionMenu.add("4) Add a Right choice");
        questionMenu.add("5) Edit a Left choice");
        questionMenu.add("6) Edit a Right choice");
        questionMenu.add("7) Display all choice options");
        questionMenu.add("8) Go back");

        boolean finished = false;
        while (!finished){
            questionMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, questionMenu.menuItems.size())) {
                int menuSelectionInt = questionMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        setMatchingPrompt();
                        break;
                    case 2:
                        setNeededResponses();
                        break;
                    case 3:
                        addLeftChoice();
                        break;
                    case 4:
                        addRightChoice();
                        break;
                    case 5:
                        editLeftChoice();
                        break;
                    case 6:
                        editRightChoice();
                        break;
                    case 7:
                        this.display();
                       break;
                    case 8:
                        finished = true;

                    }
            }
        }

    }

    public void display(boolean printPrompt) {
        if(printPrompt) {
            System.out.println(questionPrompt);
        }

        int maxChoiceLength = 0;

        for(int i = 0; i < leftMatches.size(); i++) {
            if(maxChoiceLength < leftMatches.get(i).length()) {
                maxChoiceLength = leftMatches.get(i).length();
            }
        }

        for(int i = 0; i < Math.max(leftMatches.size(), rightMatches.size()); i++) {
            String finalFormat = "%-" + maxChoiceLength + "s";
            if(i < leftMatches.size()) {
                finalFormat = String.format("%d) " + finalFormat, i, leftMatches.get(i));
            }else {
                finalFormat = String.format("   " + finalFormat, "");
            }

            if(i < rightMatches.size()) {
                finalFormat += String.format("      %c) %s", i + 97, rightMatches.get(i));
            }

            System.out.println(finalFormat);
        }

    }

    public void display() {
        display(true);
    }

    private void editRightChoice() {
        int choiceNumber = 1;
            for (String rightChoice: rightMatches) {
                System.out.printf("%c) %s \n", choiceNumber +96, rightChoice);
                choiceNumber += 1;
            }
            String userChoice = UserInput.getUserInput("Type the letter of the choice to edit: ");
            int rightChoiceChar = userChoice.charAt(0)-96;
            if(rightChoiceChar >= 0) {
                rightMatches.set(rightChoiceChar-1, UserInput.getUserInput("Add an option for the Right option " + (char)(rightChoiceChar+96)+": "));

        }
    }

    private void editLeftChoice() {
        int choiceNumber = 1;
        for (String leftChoice:leftMatches) {
            System.out.println(choiceNumber + ") " + leftChoice);
            choiceNumber += 1;
        }
        String userChoice = UserInput.getUserInput("Type the number of the choice to edit: ");
        if(InputValidator.validateThisMenuChoice(userChoice, leftMatches.size())) {
            int menuSelectionInt = Integer.parseInt(userChoice);
            leftMatches.set(menuSelectionInt-1, UserInput.getUserInput("Add an option for the Left option " + menuSelectionInt+": "));
        }

    }

    private void addLeftChoice() {
        leftMatches.add(UserInput.getUserInput("Add an option for the Left match: "));
    }
    private void addRightChoice() {
        rightMatches.add(UserInput.getUserInput("Add an option for the Right match: "));
    }

    private void setNeededResponses() {
        try {
            neededResponses = Integer.parseInt(UserInput.getUserInput("Type the number of needed responses for this question: "));
        } catch (NumberFormatException e) {
            System.out.println("Not an Integer");
        }
    }

    private void setMatchingPrompt() {
        questionPrompt = UserInput.getUserInput("Type a new Matching prompt: ");
    }

    public void displayTabulated(HashMap<String, Integer> tabulatedValues) {
        this.display();

        System.out.println();

        tabulatedValues.forEach((key, value) -> {
            System.out.println(value);
            System.out.println(key);
        });
    }

}
