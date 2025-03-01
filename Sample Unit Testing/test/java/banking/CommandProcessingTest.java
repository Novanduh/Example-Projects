package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandProcessingTest {
    CreateCommandProcessor createCommandProcessor;
    DepositCommandProcessor depositCommandProcessor;
    WithdrawCommandProcessor withdrawCommandProcessor;
    TransferCommandProcessor transferCommandProcessor;
    PassTimeProcessor passTimeProcessor;
    Bank bank;

    private void assertAccountsGetCorrectValues(String accountType, String accountID, double interestRate, int initialBalance) {
        assertEquals(bank.getAccountsInBank().get(accountID).getAccountType(), accountType);
        assertEquals(bank.getAccountsInBank().get(accountID).getAccountID(), accountID);
        assertEquals(bank.getAccountsInBank().get(accountID).getInterestRate(), interestRate);
        assertEquals(bank.getAccountsInBank().get(accountID).getAccountBalance(), initialBalance);
    }

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
        depositCommandProcessor = new DepositCommandProcessor(bank);
        withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
        transferCommandProcessor = new TransferCommandProcessor(bank);
        passTimeProcessor = new PassTimeProcessor(bank);
    }

    @Test
    void create_checking_account() {
        createCommandProcessor.processCommandGiven("creAte cHecKing 98765432 0.01");
        assertAccountsGetCorrectValues("checking", "98765432", 0.01, 0);
    }

    @Test
    void create_savings_account() {
        createCommandProcessor.processCommandGiven("Create savings 98765432 0.6");
        assertAccountsGetCorrectValues("savings", "98765432", 0.6, 0);
    }

    @Test
    void create_cd_account() {
        createCommandProcessor.processCommandGiven("Create cd 12345678 1.2 2000");
        assertAccountsGetCorrectValues("cd", "12345678", 1.2, 2000);
    }

    @Test
    void create_two_account() {
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountType(), "checking");

        createCommandProcessor.processCommandGiven("Create savings 22345678 0.01");
        assertEquals(bank.getAccountsInBank().get("22345678").getAccountType(), "savings");
    }

    @Test
    void deposit_into_account_once() {
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountBalance(), 500);
    }

    @Test
    void deposit_into_account_twice() {
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountBalance(), 1000);
    }

    @Test
    void withdraw_from_account_once() {
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        withdrawCommandProcessor.processCommandGiven("Withdraw 12345678 200");
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountBalance(), 300);
    }

    @Test
    void withdraw_from_cd_account() {
        createCommandProcessor.processCommandGiven("Create cd 12345678 0.01 1000");
        bank.passTime(12);
        withdrawCommandProcessor.processCommandGiven("Withdraw 12345678 1005");
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountBalance(), 0);
    }

    @Test
    void transfer_from_one_account_to_another() {
        createCommandProcessor.processCommandGiven("Create checking 12345678 0.01");
        depositCommandProcessor.processCommandGiven("Deposit 12345678 500");
        createCommandProcessor.processCommandGiven("Create checking 23456789 0.01");
        transferCommandProcessor.processCommandGiven("Transfer 12345678 23456789 200");
        assertEquals(bank.getAccountsInBank().get("23456789").getAccountBalance(), 200);
        assertEquals(bank.getAccountsInBank().get("12345678").getAccountBalance(), 300);
    }

    @Test
    void pass_time_accrues_interest() {
        createCommandProcessor.processCommandGiven("Create savings 98765432 0.6");
        depositCommandProcessor.processCommandGiven("Deposit 98765432 500");
        passTimeProcessor.processCommandGiven("Pass 1");
        assertEquals(500.25, bank.getAccountsInBank().get("98765432").getAccountBalance());
    }
}
