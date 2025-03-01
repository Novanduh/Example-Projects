package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class DepositCommandProcessor extends CommandProcessor {
    private final DepositCommandValidator depositCommandValidator;

    public DepositCommandProcessor(Bank bank) {
        this.bank = bank;
        depositCommandValidator = new DepositCommandValidator(bank);
    }

    public void processCommandGiven(String command) {
        if (depositCommandValidator.validateDepositCommand(command)) {
            depositIntoAccount(command);
        }
    }

    private void depositIntoAccount(String command) {
        String[] commandParsed = commandParser(command);
        accountID = commandParsed[1];
        amount = Double.parseDouble(commandParsed[2]);
        bank.depositIntoBankAccount(accountID, amount);
        bank.getAccountsInBank().get(accountID).storeTransactionHistory(command);
    }
}
