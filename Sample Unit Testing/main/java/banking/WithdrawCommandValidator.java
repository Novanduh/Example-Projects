package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class WithdrawCommandValidator extends CommandValidator {
    private final Bank bank;

    public WithdrawCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateWithdrawCommand(String command) {

        return validateAccountID(command) && validateNoTypoInCommand(command) && validateWithdrawAmount(command) && validateWithdrawLimit(command);
    }

    public boolean validateNoTypoInCommand(String command) {

        String[] commandParsed = commandParser(command);
        String withdrawKeyword = commandParsed[0].toLowerCase();
        return withdrawKeyword.equals("withdraw");
    }

    public boolean validateAccountID(String command) {

        String[] commandParsed = commandParser(command);
        boolean idLengthIsValid = false;
        boolean idFormatIsValid = false;
        boolean idExist = false;
        String accountID = commandParsed[1];

        if (commandParsed.length == 3) {
            idLengthIsValid = ((accountID.length() <= 8) && (accountID.length() > 0));
            idFormatIsValid = checkIfDoesNotContainsAlphabet(accountID);
            idExist = bank.accountWithIDExists(accountID);
        }
        return idLengthIsValid && idFormatIsValid && idExist;
    }

    public boolean validateWithdrawAmount(String command) {

        String[] commandParsed = commandParser(command);
        double withdrawAmount;
        boolean validWithdrawAmount = false;

        if (commandParsed.length == 3) {
            String amountToWithdraw = commandParsed[2];
            if (checkIfDoesNotContainsAlphabet(amountToWithdraw)) {
                withdrawAmount = Double.parseDouble(amountToWithdraw);
                validWithdrawAmount = (withdrawAmount > 0);
            }
        }
        return validWithdrawAmount;
    }

    public boolean validateWithdrawLimit(String command) {

        String[] commandParsed = commandParser(command);
        boolean withdrawValid = false;

        if (commandParsed.length == 3) {
            String amountToWithdraw = commandParsed[2];
            String accountID = commandParsed[1];
            withdrawValid = bank.withdrawAllowed(accountID, amountToWithdraw);
        }
        return withdrawValid;
    }

    public String getOpenAccountID(String command) {
        String[] commandParsed = commandParser(command);
        String accountID = commandParsed[1];
        return accountID;
    }
}
