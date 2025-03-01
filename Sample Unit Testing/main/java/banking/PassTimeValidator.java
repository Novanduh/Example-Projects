package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class PassTimeValidator extends CommandValidator {
    public PassTimeValidator() {
    }

    public boolean validatePassTimeCommand(String command) {
        return validateNoTypoInCommand(command) && validateNumberOfMonths(command);
    }

    public boolean validateNoTypoInCommand(String command) {
        String[] commandParsed = commandParser(command);
        String withdrawKeyword = commandParsed[0].toLowerCase();
        return withdrawKeyword.equals("pass");
    }

    public boolean validateNumberOfMonths(String command) {

        String[] commandParsed = commandParser(command);
        boolean validNumberOfMonths = false;

        if (commandParsed.length == 2) {
            int months = Integer.parseInt(commandParsed[1]);
            validNumberOfMonths = (months > 0) && (months < 60);
        }
        return validNumberOfMonths;
    }
}
