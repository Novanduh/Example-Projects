package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class TransferCommandValidator extends CommandValidator {
    private final Bank bank;

    public TransferCommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateTransferCommand(String command) {
        return validateAccountID(command) && validateNoTypoInCommand(command) && validateTransferAmount(command) && validateTransferLimit(command);
    }

    public boolean validateNoTypoInCommand(String command) {
        String[] commandParsed = commandParser(command);
        String withdrawKeyword = commandParsed[0].toLowerCase();
        return withdrawKeyword.equals("transfer");

    }

    public boolean validateAccountID(String command) {

        String[] commandParsed = commandParser(command);
        boolean idLengthIsValid = false;
        boolean idFormatIsValid = false;
        boolean idExist = false;

        if (commandParsed.length == 4) {
            String fromID = commandParsed[1];
            String toID = commandParsed[2];
            idLengthIsValid = ((fromID.length() <= 8) && (fromID.length() > 0)) && ((toID.length() <= 8) && (toID.length() > 0));
            idFormatIsValid = checkIfDoesNotContainsAlphabet(fromID) && checkIfDoesNotContainsAlphabet(toID);
            idExist = bank.accountWithIDExists(fromID) && bank.accountWithIDExists(toID);
        }
        return idLengthIsValid && idFormatIsValid && idExist;
    }

    public boolean validateTransferAmount(String command) {

        String[] commandParsed = commandParser(command);
        double transferAmount;
        boolean validTransferAmount = false;

        if (commandParsed.length == 4) {
            String amountToTransfer = commandParsed[3];
            if (checkIfDoesNotContainsAlphabet(amountToTransfer)) {
                transferAmount = Double.parseDouble(amountToTransfer);
                validTransferAmount = (transferAmount > 0);
            }
        }
        return validTransferAmount;
    }

    public boolean validateTransferLimit(String command) {

        String[] commandParsed = commandParser(command);
        boolean transferValid = false;
        String fromID = commandParsed[1];
        String toID = commandParsed[2];
        String amountToTransfer = commandParsed[3];

        if (commandParsed.length == 4) {
            transferValid = bank.transferAllowed(fromID, toID, amountToTransfer);
        }
        return transferValid;
    }

    public String getOpenAccountID(String command) {
        String[] commandParsed = commandParser(command);
        String openAccountID = commandParsed[2];
        return openAccountID;
    }
}
