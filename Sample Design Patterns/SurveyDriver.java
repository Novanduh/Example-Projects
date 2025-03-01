public class SurveyDriver {
    public static void runSurvey() {

        Survey survey = null;
        Menu mainMenu = new Menu();

        mainMenu.add("1) Create a new Survey");
        mainMenu.add("2) Display an existing Survey");
        mainMenu.add("3) Load an existing Survey");
        mainMenu.add("4) Save the current Survey");
        mainMenu.add("5) Take the current Survey");
        mainMenu.add("6) Modify the current Survey");
        mainMenu.add("7) Tabulate a Survey");
        mainMenu.add("8) Quit");

        while (true) {
            mainMenu.display();
            String menuSelection = UserInput.getUserInput("");

            if (InputValidator.validateThisMenuChoice(menuSelection, mainMenu.menuItems.size())) {

                int menuSelectionInt = mainMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        String surveyName = UserInput.getUserInput("Name your Survey: ");
                        survey = new Survey(surveyName);
                        survey.edit();
                        break;
                    case 2:
                        if (survey== null) {
                            System.out.println("(No survey is loaded)\n");
                            break;
                        }
                        survey.display();
                        break;
                    case 3:
                        survey = Serial.load("saved_surveys");
                        break;
                    case 4:
                        if (survey == null) {
                            System.out.println("No survey is loaded at the moment. Cannot save a null survey.\n4");
                            break;
                        }
                        Serial.serialize(survey, "saved_surveys");
                        System.out.println("Saved: " + survey.getName()+ "\n");

                        break;
                    case 5:
                        if (survey == null) {
                            System.out.println("No survey is loaded at the moment. Cannot take a null survey.\n");
                            break;
                        }
                        survey.take();

                        Serial.serialize(survey, "saved_surveys", "_" + (int)Math.floor(Math.random() * 100000), ".resp");
                        survey.questionList.forEach((questions -> questions.getResponseList().clear()));
                        break;

                    case 6:
                        if(survey==null) {
                            System.out.println("No survey is loaded at the moment. Cannot modify a null survey.\n");
                            break;
                        }
                        survey.edit();
                        break;
                    case 7:
                        if(survey==null) {
                            System.out.println("No survey is loaded at the moment. Cannot tabulate a null survey.\n");
                            break;
                        }
                        survey.tabulate("saved_surveys");
                        break; //tabulate
                    case 8:
                        return; //quit
                }
            }
        }
    }


}
