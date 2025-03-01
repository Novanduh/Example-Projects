package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.List;

public class MasterControl {
    Bank bank;
    CreateCommandValidator createCommandValidator;
    DepositCommandValidator depositCommandValidator;
    WithdrawCommandValidator withdrawCommandValidator;
    TransferCommandValidator transferCommandValidator;
    PassTimeValidator passTimeValidator;
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    WithdrawCommandProcessor withdrawCommandProcessor;
    TransferCommandProcessor transferCommandProcessor;
    PassTimeProcessor passTimeProcessor;
    CommandStorage commandStorage;
    String openAccountID;

    public MasterControl(Bank bank, CreateCommandProcessor createCommandProcessor, DepositCommandProcessor depositCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor, TransferCommandProcessor transferCommandProcessor, PassTimeProcessor passTimeProcessor, CommandStorage commandStorage) {
        this.bank = bank;

        createCommandValidator = new CreateCommandValidator(bank);
        depositCommandValidator = new DepositCommandValidator(bank);
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
        transferCommandValidator = new TransferCommandValidator(bank);
        passTimeValidator = new PassTimeValidator();

        this.createCommandProcessor = createCommandProcessor;
        this.depositCommandProcessor = depositCommandProcessor;
        this.withdrawCommandProcessor = withdrawCommandProcessor;
        this.transferCommandProcessor = transferCommandProcessor;
        this.passTimeProcessor = passTimeProcessor;
        this.commandStorage = commandStorage;
    }

    private String[] commandParser(String command) {
        return command.split(" ", 5);
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            String[] commandParsed = commandParser(command);

            if (createCommandValidator.validateCreateCommand(command) && commandParsed[0].toLowerCase().equals("create")) {
                createCommandProcessor.processCommandGiven(command);
                openAccountID = createCommandValidator.getOpenAccountID(command);
                if (commandParsed[1].toLowerCase().equals("cd")) {
                    commandStorage.storeOutputOfValidCommands(openAccountID);
                }
            } else if (depositCommandValidator.validateDepositCommand(command) && commandParsed[0].toLowerCase().equals("deposit")) {
                depositCommandProcessor.processCommandGiven(command);
                openAccountID = depositCommandValidator.getOpenAccountID(command);
            } else if (withdrawCommandValidator.validateWithdrawCommand(command) && commandParsed[0].toLowerCase().equals("withdraw")) {
                withdrawCommandProcessor.processCommandGiven(command);
                openAccountID = withdrawCommandValidator.getOpenAccountID(command);
            } else if (transferCommandValidator.validateTransferCommand(command) && commandParsed[0].toLowerCase().equals("transfer")) {
                transferCommandProcessor.processCommandGiven(command);
                openAccountID = transferCommandValidator.getOpenAccountID(command);
            } else if (passTimeValidator.validatePassTimeCommand(command) && commandParsed[0].toLowerCase().equals("pass")) {
                passTimeProcessor.processCommandGiven(command);
                commandStorage.storeOutputOfValidCommands(openAccountID);
            } else {
                commandStorage.storeInvalidCommand(command);
            }
        }
        return commandStorage.getOutputOfCommands();
    }

}
