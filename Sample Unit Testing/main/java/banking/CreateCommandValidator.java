package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class CreateCommandValidator extends CommandValidator {

    private final Bank bank;

    public CreateCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateCreateCommand(String command) {
        String[] commandParsed = commandParser(command);
        String accountType = commandParsed[1];
        if (accountType.toLowerCase().equals("cd")) {
            return validateNoTypoInCommand(command) && validateCDCommand(command) && validateAccountID(command) && validateAPR(command);
        } else {
            return validateNoTypoInCommand(command) && validateAccountID(command) && validateAPR(command);
        }
    }


    public boolean validateAccountID(String command) {
        String[] commandParsed = commandParser(command);
        String accountID = commandParsed[2];

        boolean idLengthIsValid = ((accountID.length() <= 8) && (accountID.length() > 0));
        boolean idFormatIsValid = checkIfNumeric(accountID);
        boolean idIsUnique = !bank.accountWithIDExists(accountID);
        return idLengthIsValid && idFormatIsValid && idIsUnique;
    }

    public boolean validateCDCommand(String command) {

        String[] commandParsed = commandParser(command);
        boolean cdHasInitialBalance = false;
        boolean cdHasValidInitialBalance = false;

        if (commandParsed.length == 5) {
            String initialBalance = commandParsed[4];
            cdHasInitialBalance = checkIfDoesNotContainsAlphabet(initialBalance);
            int cdInitialBalance = Integer.parseInt(initialBalance);
            cdHasValidInitialBalance = (cdInitialBalance >= 1000);
        }
        return cdHasInitialBalance && cdHasValidInitialBalance;
    }

    public boolean validateNoTypoInCommand(String command) {

        String[] commandParsed = commandParser(command);
        boolean createKeywordUsedAndHasNoTypos = (commandParsed[0].toLowerCase().equals("create"));
        String accountType = commandParsed[1].toLowerCase();
        boolean accountTypeGivenAndHasNoTypos = false;

        if ((accountType.toLowerCase().equals("checking")) || (accountType.toLowerCase().equals("savings")) || (accountType.toLowerCase().equals("cd"))) {
            accountTypeGivenAndHasNoTypos = true;
        }
        return createKeywordUsedAndHasNoTypos && accountTypeGivenAndHasNoTypos;
    }

    public boolean validateAPR(String command) {

        String[] commandParsed = commandParser(command);
        boolean validAPR = false;
        boolean isInPercentageForm = false;
        double interestRate;


        if (commandParsed.length > 3) {
            String interestRateToAssign = commandParsed[3];
            if (!checkIfDoesNotContainsAlphabet(interestRateToAssign)) {
                return false;
            }
            isInPercentageForm = commandParsed[3].contains(".");
            interestRate = Double.parseDouble(commandParsed[3]);
            validAPR = (interestRate > 0.0) && (interestRate < 10.0);
        }
        return validAPR && isInPercentageForm;
    }

    public String getOpenAccountID(String command) {
        String[] commandParsed = commandParser(command);
        String accountID = commandParsed[2];
        return accountID;
    }

}
