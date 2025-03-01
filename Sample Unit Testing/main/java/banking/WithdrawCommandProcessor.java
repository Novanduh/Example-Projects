package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class WithdrawCommandProcessor extends CommandProcessor {
    private WithdrawCommandValidator withdrawCommandValidator;

    public WithdrawCommandProcessor(Bank bank) {
        this.bank = bank;
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
    }


    public void processCommandGiven(String command) {
        if (withdrawCommandValidator.validateWithdrawCommand(command)) {
            withdrawFromAccount(command);
        }
    }

    private void withdrawFromAccount(String command) {
        String[] commandParsed = commandParser(command);
        accountID = commandParsed[1];
        amount = Double.parseDouble(commandParsed[2]);
        bank.withdrawFromBankAccount(accountID, amount);
        bank.getAccountsInBank().get(accountID).storeTransactionHistory(command);
    }
}
