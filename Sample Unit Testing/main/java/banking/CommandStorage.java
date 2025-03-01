package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
    private final Bank bank;
    protected List<String> commandList, invalidCommandList;

    public CommandStorage(Bank bank) {
        this.bank = bank;
        commandList = new ArrayList<>();
        invalidCommandList = new ArrayList<>();
    }

    public void storeInvalidCommand(String command) {
        this.invalidCommandList.add(command);
    }

    public List<String> getInvalidCommand() {
        return this.invalidCommandList;
    }

    public void storeOutputOfValidCommands(String accountID) {
        this.commandList.add(bank.getAccountsInBank().get(accountID).getCurrentState());
        List<String> transactionHistory = bank.getAccountsInBank().get(accountID).getTransactionHistory();
        try {
            for (String transaction : transactionHistory) {
                this.commandList.add(transaction);
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }
    }

    public List<String> getOutputOfCommands() {
        for (String invalidCommand : this.invalidCommandList) {
            this.commandList.add(invalidCommand);
        }
        return this.commandList;
    }

}
