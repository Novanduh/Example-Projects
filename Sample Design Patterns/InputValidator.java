public class InputValidator {

    private String input;


    public static boolean validateThisMenuChoice(String choice, int maxRange) {

        try {
            Integer.parseInt(choice);
        }
        catch (Exception numberFormatException) {
            return false;
        }
        int intChoice = Integer.parseInt(choice);

        return ((intChoice > 0) && (intChoice <= maxRange));

    }

}
