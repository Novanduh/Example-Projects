import java.util.ArrayList;
import java.util.HashMap;

public class MultipleChoice extends Questions{
    public ArrayList<String> choices;
    


    public MultipleChoice(String prompt) {
        super(prompt);
        this.choices = new ArrayList<>();
    }

    public void take(boolean printPrompt) {

        int questionsRespondedTo = 0;
        ResponsesCorrectAnswer responses = new ResponsesCorrectAnswer();

        while (neededResponses > questionsRespondedTo) {
            display(printPrompt);

            while(true) {
                String concatenatedResponse = UserInput.getUserInput("Type the number of the choice: ");

                try {
                    int option = Integer.parseInt(concatenatedResponse.trim());
                    if(option < 1 || option > choices.size()) {
                        throw new NumberFormatException();
                    }
                    responses.add(choices.get(option - 1));
                    questionsRespondedTo += 1;
                    break;
                }catch(NumberFormatException ignored) {
                    System.out.println("Invalid choice");
                }
            }
        }

        getResponseList().add(responses);

        System.out.printf("This question has already been answered a maximum of %d times\n", neededResponses);

    }

    public void take() {
        take(true);
    }

    public void create() {
        setMultipleChoicePrompt();
        int choiceQuanity = 0;
        boolean gotChoiceQuantity =false;
        while(!gotChoiceQuantity) {
            try {
                choiceQuanity = Integer.parseInt(UserInput.getUserInput("Type the quantity of multiple choice responses you would like to have: "));
                gotChoiceQuantity = choiceQuanity > 0;
            } catch (NumberFormatException e) {
                System.out.println("Not an Integer");
            }
        }
        for (int i = 0; i < choiceQuanity; i++) {
            addLeftChoice();
        }
        setNeededResponses();
    }

    public void edit() {
        Menu questionMenu = new Menu();

        questionMenu.add("1) Modify this prompt");
        questionMenu.add("2) Modify needed responses");
        questionMenu.add("3) Add a choice");

        questionMenu.add("4) Edit a choice");

        questionMenu.add("5) Display all choice options");
        questionMenu.add("6) Go back");

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
                        addLeftChoice();
                        break;
                    case 4:
                        editLeftChoice();
                        break;
                    case 5:
                        this.display();
                        break;
                    case 6:
                        finished = true;

                }
            }
        }

    }
    public void display(boolean printPrompt) {
        if(printPrompt) {
            System.out.println(questionPrompt);
        }

        int choiceNumber = 1;
        for (String leftChoice:choices) {
            System.out.println(choiceNumber + ") " + leftChoice);
            choiceNumber += 1;
        }

        for (ResponsesCorrectAnswer response: getResponseList()) {
            response.display();
        }

    }

    public void display() {
        display(true);
    }

    public void editLeftChoice() {
        int choiceNumber = 1;
        for (String leftChoice:choices) {
            System.out.println(choiceNumber + ") " + leftChoice);
            choiceNumber += 1;
        }
        String userChoice = UserInput.getUserInput("Type the number of the choice to edit: ");
        if(InputValidator.validateThisMenuChoice(userChoice, choices.size())) {
            int menuSelectionInt = Integer.parseInt(userChoice);
            choices.set(menuSelectionInt-1, UserInput.getUserInput("Add an option to choose from " + menuSelectionInt+": "));
        }

    }

    public void addLeftChoice() {
        choices.add(UserInput.getUserInput("Add an option for matching: "));
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
    public void setMultipleChoicePrompt() {
        questionPrompt = UserInput.getUserInput("Type a new prompt: ");
    }

    public void displayTabulated(HashMap<String, Integer> tabulatedValues) {
        System.out.println(this.questionPrompt);
        System.out.println();

        for(String choice : this.choices) {
            System.out.print(choice + ": ");
            if (tabulatedValues.containsKey(choice)) {
                System.out.println(tabulatedValues.get(choice));
            }else {
                System.out.println(0);
            }
        }
    }
}

