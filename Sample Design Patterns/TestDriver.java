import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDriver {

    public static void runTest() {
        Test test = null;

        Menu mainMenu = new Menu();

        mainMenu.add("1) Create a new Test");
        mainMenu.add("2) Display an existing Test without correct answers");
        mainMenu.add("3) Display an existing Test with correct answers");
        mainMenu.add("4) Load an existing Test");
        mainMenu.add("5) Save the current Test");
        mainMenu.add("6) Take the current Test");
        mainMenu.add("7) Modify the current Test");
        mainMenu.add("8) Tabulate a Test");
        mainMenu.add("9) Grade a Test");
        mainMenu.add("10) Quit");

        while (true) {
            mainMenu.display();
            String menuSelection = UserInput.getUserInput("");

            if (InputValidator.validateThisMenuChoice(menuSelection, mainMenu.menuItems.size())) {

                int menuSelectionInt = mainMenu.convertStringToInt(menuSelection);

                switch (menuSelectionInt) {
                    case 1:
                        String testName = UserInput.getUserInput("Name your test: ");
                        test = new Test(testName);
                        test.edit();
                        break;
                    case 2:
                        if (test== null) {
                            System.out.println("(No test is loaded)\n");
                            break;
                        }
                        test.display(false);
                        break;
                    case 3: //display with correct answers
                        if (test== null) {
                            System.out.println("(No test is loaded)\n");
                            break;
                        }
                        test.display(true);
                        break;
                    case 4:
                        test = (Test) Serial.load("saved_tests");
                        break;
                    case 5:
                        if (test == null) {
                            System.out.println("No test is loaded at the moment. Cannot save a null test.\n4");
                            break;
                        }
                        Serial.serialize(test, "saved_tests");
                        System.out.println("Saved: " + test.getName()+ "\n");

                        break;
                    case 6:
                        if (test == null) {
                            System.out.println("No test is loaded at the moment. Cannot take a null test.\n");
                            break;
                        }
                        test.take();
                        Serial.serialize(test, "saved_tests", "_" + (int)Math.floor(Math.random() * 100000), ".resp");
                        test.questionList.forEach((questions -> questions.getResponseList().clear()));
                        break;
                    case 7://tabulate
                        if(test==null) {
                            System.out.println("No test is loaded at the moment. Cannot modify a null test.\n");
                            break;
                        }
                        test.edit();
                        break;
                    case 8:
                        if(test==null) {
                            System.out.println("No test is loaded at the moment. Cannot tabulate a null test.\n");
                            break;
                        }
                        test.tabulate("saved_tests");
                        break;
                    case 9://grade
                        if(test==null) {
                            System.out.println("No test is loaded at the moment. Cannot grade a null test.\n");
                            break;
                        }

                        List<File> files = Serial.getFilesWithExtension(".resp", "saved_tests", test.getName() + "_");
                        for(int i = 0; i < files.size(); i++) {
                            System.out.println(String.format("%d) %s", i + 1, files.get(i).getName()));
                        }

                        while(true) {
                            String userInput = UserInput.getUserInput("Type the number of the test: ");

                            try {
                                int option = Integer.parseInt(userInput.trim());
                                if(option < 1 || option > files.size()) {
                                    throw new NumberFormatException();
                                }

                                Test gradeTest = (Test) Serial.deserialize(files.get(option - 1).getAbsolutePath());

                                int grade = gradeTest.grade();

                                int numEssayQuestions = 0;

                                for(ResponsesCorrectAnswer correctAnswer : gradeTest.getCorrectAnswers()) {
                                    if(correctAnswer == null) {
                                        numEssayQuestions += 1;
                                    }
                                }

                                System.out.println(String.format("You got %d points out of 100 on the test. %d questions (%d points) could not be graded because they are essay questions.\n",
                                        grade, numEssayQuestions, numEssayQuestions * (100 / gradeTest.questionList.size())));

                                break;
                            }catch(NumberFormatException ignored) {
                                System.out.println("Invalid choice");
                            } catch (IOException e) {
                                System.out.println("File not found");
                            } catch (ClassNotFoundException e) {
                                System.out.println("Invalid file");
                            }
                        }

                        break;
                    case 10:
                        return; //quit


                }

            }
        }
    }
}