package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {
    CommandStorage commandStorage;
    TransactionHistory transactionHistory;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandStorage = new CommandStorage(bank);
        transactionHistory = new TransactionHistory();
    }

    @Test
    void store_invalid_command() {
        commandStorage.storeInvalidCommand("Create checkings 12345678 0.01");
        List<String> actual = new ArrayList<>();
        actual.add("Create checkings 12345678 0.01");
        assertEquals(actual, commandStorage.getInvalidCommand());
    }

    @Test
    void store_two_invalid_commands() {
        commandStorage.storeInvalidCommand("Create checkings 12345678 0.01");
        commandStorage.storeInvalidCommand("Create cd 12345678 1.2 200");
        List<String> actual = new ArrayList<>();
        actual.add("Create checkings 12345678 0.01");
        actual.add("Create cd 12345678 1.2 200");
        assertEquals(actual, commandStorage.getInvalidCommand());
    }

    @Test
    void store_transactional_history() {
        transactionHistory.storeTransactionalCommand("Create savings 12345678 0.6");
        List<String> actual = new ArrayList<>();
        actual.add("Create savings 12345678 0.6");
        assertEquals(actual, transactionHistory.getTransactionHistory());
    }

    @Test
    void transaction_history_of_checking_account_is_stored() {
        Bank bank = new Bank();
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        List<String> actual = new ArrayList<>();
        actual.add("Deposit 12345678 500");
        assertEquals(actual, bank.getAccountsInBank().get("12345678").getTransactionHistory());
    }

    @Test
    void checking_account_current_state_is_stored() {
        Bank bank = new Bank();
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        assertEquals("Checking 12345678 500.00 0.01", bank.getAccountsInBank().get("12345678").getCurrentState());
    }

    @Test
    void cd_account_current_state_is_stored() {
        Bank bank = new Bank();
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        createCommandProcessor.processCommandGiven("Create cd 12345678 0.01 1000");
        assertEquals("Cd 12345678 1000.00 0.01", bank.getAccountsInBank().get("12345678").getCurrentState());
    }

    @Test
    void savings_account_current_state_and_transaction_history_stored() {
        Bank bank = new Bank();
        CreateCommandProcessor createCommandProcessor = new CreateCommandProcessor(bank);
        createCommandProcessor.processCommandGiven("Create savings 12345678 0.01");
        DepositCommandProcessor depositCommandProcessor = new DepositCommandProcessor(bank);
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        List<String> actual = new ArrayList<>();
        actual.add("Deposit 12345678 500");
        assertEquals("Savings 12345678 500.00 0.01", bank.getAccountsInBank().get("12345678").getCurrentState());

        assertEquals(actual, bank.getAccountsInBank().get("12345678").getTransactionHistory());
    }


}
