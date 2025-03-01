package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class TransferCommandProcessor extends CommandProcessor {
    private final TransferCommandValidator transferCommandValidator;

    public TransferCommandProcessor(Bank bank) {
        this.bank = bank;
        transferCommandValidator = new TransferCommandValidator(bank);
    }

    public void processCommandGiven(String command) {
        if (transferCommandValidator.validateTransferCommand(command)) {
            transferToAndFromAccount(command);
        }
    }

    private void transferToAndFromAccount(String command) {
        String[] commandParsed = commandParser(command);
        String fromID = commandParsed[1];
        String toID = commandParsed[2];
        amount = Double.parseDouble(commandParsed[3]);
        bank.transferBetweenAccounts(fromID, toID, amount);
        bank.getAccountsInBank().get(fromID).storeTransactionHistory(command);
        bank.getAccountsInBank().get(toID).storeTransactionHistory(command);
    }


}
