package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class CommandValidator {

    protected String[] commandParser(String command) {
        return command.split(" ", 5);
    }

    public boolean checkIfNumeric(String command) {
        boolean isNumeric = true;
        for (int i = 0; i < command.length(); i++) {
            if (!Character.isDigit(command.charAt(i))) {
                isNumeric = false;
            }
        }
        return isNumeric;
    }

    public boolean checkIfDoesNotContainsAlphabet(String command) {
        boolean isNumber = true;
        for (int i = 0; i < command.length(); i++) {
            if (Character.isLetter(command.charAt(i))) {
                isNumber = false;
            }
        }
        return isNumber;
    }
}
