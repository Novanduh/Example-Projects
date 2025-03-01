package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class CreateCommandProcessor extends CommandProcessor {

    private final CreateCommandValidator createCommandValidator;


    public CreateCommandProcessor(Bank bank) {
        this.bank = bank;
        createCommandValidator = new CreateCommandValidator(bank);
    }

    public void processCommandGiven(String command) {
        if (createCommandValidator.validateCreateCommand(command)) {
            createAccount(command);
        }
    }

    private void createAccount(String command) {
        String[] commandParsed = commandParser(command);
        accountType = commandParsed[1].toLowerCase();
        accountID = commandParsed[2];
        interestRate = Double.parseDouble(commandParsed[3]);
        switch (accountType) {
            case "checking":
                bank.addCheckingAccount(accountType, accountID, interestRate);
                break;
            case "savings":
                bank.addSavingsAccount(accountType, accountID, interestRate);
                break;
            case "cd":
                initialBalance = Double.parseDouble(commandParsed[4]);
                bank.addCDAccount(accountType, accountID, interestRate, initialBalance);
                break;
        }
    }
}
