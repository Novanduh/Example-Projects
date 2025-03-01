package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {
    private List<String> commandList;

    public TransactionHistory() {
        commandList = new ArrayList<>();
    }

    public void storeTransactionalCommand(String command) {
        this.commandList.add(command);
    }

    public List<String> getTransactionHistory() {
        return this.commandList;
    }
}
