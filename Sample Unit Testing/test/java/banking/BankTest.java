package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
    public static final String ACCOUNT_TYPE = "checking";
    public static final String ACCOUNT_ID = "12345678";
    public static final double INTEREST_RATE = 0.06;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_initially_has_no_accounts() {
        assertTrue(bank.getAccountsInBank().isEmpty());
    }

    @Test
    void add_account_to_bank() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        assertEquals(ACCOUNT_TYPE, bank.getAccountsInBank().get(ACCOUNT_ID).getAccountType());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        bank.addCDAccount("cd", "87654321", INTEREST_RATE, 1000);
        assertEquals(ACCOUNT_TYPE, bank.getAccountsInBank().get(ACCOUNT_ID).getAccountType());
        assertEquals("cd", bank.getAccountsInBank().get("87654321").getAccountType());
    }

    @Test
    void deposit_into_bank_account() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        bank.depositIntoBankAccount(ACCOUNT_ID, 1000);
        Account actualAccount = bank.getAccountsInBank().get(ACCOUNT_ID);
        assertEquals(1000, actualAccount.getAccountBalance());
    }

    @Test
    void deposit_into_bank_account_twice() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        bank.depositIntoBankAccount(ACCOUNT_ID, 1000);
        bank.depositIntoBankAccount(ACCOUNT_ID, 1000);
        Account actualAccount = bank.getAccountsInBank().get(ACCOUNT_ID);
        assertEquals(2000, actualAccount.getAccountBalance());
    }

    @Test
    void withdraw_from_bank_account() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        bank.depositIntoBankAccount(ACCOUNT_ID, 1000);
        bank.withdrawFromBankAccount(ACCOUNT_ID, 400);
        Account actualAccount = bank.getAccountsInBank().get(ACCOUNT_ID);
        assertEquals(600, actualAccount.getAccountBalance());
    }

    @Test
    void if_amount_more_than_balance_withdraw_will_result_in_zero_balance() {
        bank.addCheckingAccount(ACCOUNT_TYPE, ACCOUNT_ID, INTEREST_RATE);
        bank.depositIntoBankAccount(ACCOUNT_ID, 300);
        bank.withdrawFromBankAccount(ACCOUNT_ID, 400);
        Account actualAccount = bank.getAccountsInBank().get(ACCOUNT_ID);
        assertEquals(0, actualAccount.getAccountBalance());
    }
}
