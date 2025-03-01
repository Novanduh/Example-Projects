import java.util.ArrayList;
import java.util.List;

public class Test extends Survey{
    private static final long serialVersionUID = 987654321;

    private ArrayList<ResponsesCorrectAnswer> correctAnswers = new ArrayList<>();

    public Test(String name) {
        super(name);
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

    public List<ResponsesCorrectAnswer> getCorrectAnswers() {
        return this.correctAnswers;
    }

    @Override
    public void edit() {

        while (true) {
            surveyMenu.display();
            String menuSelection = UserInput.getUserInput("");
            if (InputValidator.validateThisMenuChoice(menuSelection, surveyMenu.menuItems.size())) {
                int menuSelectionInt = surveyMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        TrueFalse newTF = new TrueFalse("Sample Prompt");
                        questionList.add(newTF);
                        newTF.create();
                        System.out.println("Write the correct answer to this question: ");
                        newTF.take(false);
                        this.correctAnswers.add(newTF.getResponseList().get(0));
                        newTF.getResponseList().clear();
                        break;
                    case 2:
                        MultipleChoice newMC = new MultipleChoice("Sample Prompt");
                        questionList.add(newMC);
                        newMC.create();
                        System.out.println("Write the correct answer to this question: ");
                        newMC.take(false);
                        this.correctAnswers.add(newMC.getResponseList().get(0));
                        newMC.getResponseList().clear();
                        break;
                    case 3:
                        ShortAnswer newShortAnswer = new ShortAnswer("Sample Prompt");
                        questionList.add(newShortAnswer);
                        newShortAnswer.create();
                        System.out.println("Write the correct answer to this question: ");
                        newShortAnswer.take(false);
                        this.correctAnswers.add(newShortAnswer.getResponseList().get(0));
                        newShortAnswer.getResponseList().clear();
                        break;
                    case 4:
                        Essay newEssay = new Essay("Sample prompt");
                        questionList.add(newEssay);
                        newEssay.create();
                        this.correctAnswers.add(null);
                        break;
                    case 5:
                        Date newDate = new Date("Sample prompt");
                        questionList.add(newDate);
                        newDate.create();
                        System.out.println("Write the correct answer to this question: ");
                        newDate.take(false);
                        this.correctAnswers.add(newDate.getResponseList().get(0));
                        newDate.getResponseList().clear();
                        break;
                    case 6:
                        Matching newMatching = new Matching("Sample prompt");
                        questionList.add(newMatching);
                        newMatching.create();
                        System.out.println("Write the correct answer to this question: ");
                        newMatching.take(false);
                        this.correctAnswers.add(newMatching.getResponseList().get(0));
                        newMatching.getResponseList().clear();
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

    public void display(boolean displayCorrectAnswers) {
        System.out.println("Test Name: " + this.name + "\nhas the following questions with responses: \n");
        int counter=0;
        for (Questions questions:questionList) {
            System.out.printf("Question: %d  ", counter+1);
            questions.display();
            System.out.print("\n");
            counter += 1;

            if (displayCorrectAnswers) {
                System.out.println("Correct Answer: ");

                ResponsesCorrectAnswer correctAnswer = correctAnswers.get(counter - 1);
                if(correctAnswer != null) {
                    correctAnswers.get(counter - 1).display();
                }
                System.out.println();
            }
        }
    }


    public int grade() {
        // list of questions

        // list of the user's responses to those questions
        // list of correct answers

        // each question will be worth (100 / numQuestions) points
        int points = 100/ questionList.size();
        int totalPoints = 0;

        // loop through the questions
        for (int i = 0; i < questionList.size(); i++) {
            Questions question = questionList.get(i);
            ResponsesCorrectAnswer userResponse = question.getResponseList().get(0);
            ResponsesCorrectAnswer correctAnswer = correctAnswers.get(i);
            if ((correctAnswer != null) && (userResponse.isEqual(correctAnswer))) {
                totalPoints += points;
            }

        }


        // for each question, compare the user's ResponseCorrectAnswer to the correct answer ResponseCorrectAnswer
        // you can do this taking the correct ResponseCorrectAnswer.isEqual(user's ResponseCorrectAnswer)
        // if they are equal, add (100 / numQuestions) to the total
        // if they aren't, don't
        return totalPoints;
    }

}
