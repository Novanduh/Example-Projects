package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class DepositCommandValidator extends CommandValidator {

    private final Bank bank;

    public DepositCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateDepositCommand(String command) {
        return validateAccountID(command) && validateNoTypoInCommand(command) && validateDepositAmount(command) && validateDepositLimit(command);
    }

    public boolean validateAccountID(String command) {

        String[] commandParsed = commandParser(command);
        boolean idLengthIsValid = false;
        boolean idFormatIsValid = false;
        boolean idExist = false;
        String accountID = commandParsed[1];

        if (commandParsed.length == 3) {
            idLengthIsValid = ((accountID.length() <= 8) && (accountID.length() > 0));
            idFormatIsValid = checkIfNumeric(accountID);
            idExist = bank.accountWithIDExists(accountID);
        }
        return idLengthIsValid && idFormatIsValid && idExist;
    }

    public boolean validateNoTypoInCommand(String command) {
        String[] commandParsed = commandParser(command);
        String depositKeyword = commandParsed[0].toLowerCase();
        return depositKeyword.equals("deposit");
    }

    public boolean validateDepositAmount(String command) {

        String[] commandParsed = commandParser(command);
        int depositAmount;
        boolean validDepositAmount = false;

        if (commandParsed.length == 3) {
            String amountToDeposit = commandParsed[2];
            if (checkIfNumeric(amountToDeposit)) {
                depositAmount = Integer.parseInt(amountToDeposit);
                validDepositAmount = (depositAmount > 0);
            }
        }
        return validDepositAmount;
    }

    public boolean validateDepositLimit(String command) {

        String[] commandParsed = commandParser(command);
        boolean depositValid = false;

        if (commandParsed.length == 3) {
            String accountID = commandParsed[1];
            String amountToDeposit = commandParsed[2];
            depositValid = bank.depositAllowed(accountID, amountToDeposit);
        }
        return depositValid;
    }

    public String getOpenAccountID(String command) {
        String[] commandParsed = commandParser(command);
        String accountID = commandParsed[1];
        return accountID;
    }
}
