import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Survey implements Serializable {


    protected ArrayList<Questions> questionList;

    private static final long serialVersionUID = 987654321;

    protected String name;
    Menu surveyMenu;

    public Survey(String name) {
        this.surveyMenu = new Menu();
        this.name = name;
        this.questionList = new ArrayList<>();
        surveyMenu.add("1) Add a new T/F question");
        surveyMenu.add("2) Add a new multiple-choice question");
        surveyMenu.add("3) Add a new short answer question");
        surveyMenu.add("4) Add a new essay question");
        surveyMenu.add("5) Add a new date question");
        surveyMenu.add("6) Add a new matching question");
        surveyMenu.add("7) Modify an existing question");
        surveyMenu.add("8) Return to previous menu");

    }


    public void edit() {

        while (true) {
            surveyMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, surveyMenu.menuItems.size())) {
                int menuSelectionInt = surveyMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        Questions newTF = new TrueFalse("Sample Prompt");
                        questionList.add(newTF);
                        newTF.create();
                        break;
                    case 2:
                        Questions newMC = new MultipleChoice("Sample Prompt");
                        questionList.add(newMC);
                        newMC.create();
                        break;
                    case 3:
                        Questions newShortAnswer = new ShortAnswer("Sample Prompt");
                        questionList.add(newShortAnswer);
                        newShortAnswer.create();
                        break;
                    case 4:
                        Questions newEssay = new Essay("Sample prompt");
                        questionList.add(newEssay);
                        newEssay.create();
                        break;
                    case 5:
                        Questions newDate = new Date("Sample prompt");
                        questionList.add(newDate);
                        newDate.create();
                        break;
                    case 6:
                        Questions newMatching = new Matching("Sample prompt");
                        questionList.add(newMatching);
                        newMatching.create();
                        break;
                    case 7:
                        modifyQuestion();
                        break;
                    case 8:
                        return;


                }
            }
        }

    }

    public void modifyQuestion() {
        if (questionList.isEmpty()) {
            System.out.println("There are no questions to modify.\n");
            return;
        }

        int questionNumber=1;
        for (Questions question: questionList) {
            System.out.println(questionNumber + ") " + question.questionPrompt);
            questionNumber += 1;
        }
        String userChoice = UserInput.getUserInput("Type the number of the choice to edit: ");
        if(InputValidator.validateThisMenuChoice(userChoice, questionList.size())) {
            int menuSelectionInt = Integer.parseInt(userChoice);
            questionList.get(menuSelectionInt-1).edit();
        }
    }

    public String getName(){
        return name;
    }

    public void setSurveyName(String name) {
        this.name = name;
    }

    public void take() {
        // if no questions, say no questions
        if(questionList.isEmpty()) {
            System.out.println("There are no questions in the Survey");
        }
        // if some questions, loop through each of them and wait for a user response.
        else {
            for (Questions question:questionList) {
                question.take();
            }

        }

    }


    public void display() {
        System.out.println("Survey Name: " + this.name + "\nhas the following questions with responses: \n");
        int counter=0;
        for (Questions questions:questionList) {
            System.out.printf("Question: %d  ", counter+1);
            questions.display();
            System.out.printf("\n");
            counter += 1;
        }

    }

    public void tabulate(String surveyPath) {
        ArrayList<HashMap<String, Integer>> tabulatedValues = new ArrayList<>();

        for(Questions ignored : questionList) {
            tabulatedValues.add(new HashMap<>());
        }

        // open file directory
        // for each file
        File directory = new File(surveyPath);
        File[] content = directory.listFiles();

        for (File file : content) {
            Survey survey;

            if (file.getName().startsWith(name) && file.getName().endsWith(".resp")) {
                try {
                    survey = Serial.deserialize(surveyPath + File.separator + file.getName());
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("No Surveys found");
                    return;
                }
            }else {
                continue;
            }

            for(int i = 0; i < questionList.size(); i++) {
                for(ResponsesCorrectAnswer response : survey.questionList.get(i).getResponseList()) {
                    HashMap<String, Integer> questionMap = tabulatedValues.get(i);

                    for(String responseString : response.getResponses()) {
                        questionMap.putIfAbsent(responseString, 0);
                        questionMap.put(responseString, questionMap.get(responseString) + 1);
                    }
                }
            }
        }


        for(int i = 0; i < questionList.size(); i++) {
            Questions question = questionList.get(i);
            HashMap<String, Integer> tabulatedResponses = tabulatedValues.get(i);

            question.displayTabulated(tabulatedResponses);
            System.out.println("");
        }
    }


}
