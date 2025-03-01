package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountTest {

    public static final String ACCOUNT_ID = "12345678";
    public static final double INTEREST_RATE = 0.06;
    CheckingAccount checkingAccount;

    @BeforeEach
    void setUp() {
        checkingAccount = new CheckingAccount(ACCOUNT_ID, INTEREST_RATE);
    }

    @Test
    void checking_account_gets_created_and_has_initial_balance_zero() {
        assertEquals(ACCOUNT_ID, checkingAccount.getAccountID());
        assertEquals(checkingAccount.getAccountBalance(), 0);
    }

    @Test
    void savings_account_gets_created_and_has_initial_balance_zero() {
        SavingsAccount savingsAccount = new SavingsAccount("87654321", 0.05);
        assertEquals("87654321", savingsAccount.getAccountID());
        assertEquals(savingsAccount.getAccountBalance(), 0);
    }

    @Test
    void cd_account_gets_created_and_initial_balance_equals_balance_supplied() {
        CertificateOfDepositAccount cd = new CertificateOfDepositAccount("21436587", 0.04, 1000);
        assertEquals("21436587", cd.getAccountID());
        assertEquals(cd.getAccountBalance(), 1000);
    }

    @Test
    void account_type_gets_assigned() {
        assertEquals("checking", checkingAccount.getAccountType());
    }

    @Test
    void account_has_interest_rate() {
        assertEquals(INTEREST_RATE, checkingAccount.getInterestRate());
    }

    @Test
    void account_interest_rate_cannot_be_negative() {
        CheckingAccount checkingAccount = new CheckingAccount(ACCOUNT_ID, -0.06);
        assertFalse(checkingAccount.isInterestRateValid());
    }

    @Test
    void depositing_balance_into_checking_account() {
        checkingAccount.addMoneyToAccountBalance(5);
        assertEquals(5, checkingAccount.getAccountBalance());
    }

    @Test
    void depositing_balance_zero_into_checking_account() {
        checkingAccount.addMoneyToAccountBalance(0);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void depositing_balance_twice_into_checking_account() {
        checkingAccount.addMoneyToAccountBalance(5);
        checkingAccount.addMoneyToAccountBalance(5);
        assertEquals(10, checkingAccount.getAccountBalance());
    }

    @Test
    void negative_values_cannot_be_deposited() {
        checkingAccount.addMoneyToAccountBalance(-5);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void withdrawing_zero_from_checking_account() {
        checkingAccount.addMoneyToAccountBalance(5);
        checkingAccount.withdrawMoneyFromAccountBalance(0);
        assertEquals(5, checkingAccount.getAccountBalance());
    }

    @Test
    void withdrawing_from_checking_account() {
        checkingAccount.addMoneyToAccountBalance(5);
        checkingAccount.withdrawMoneyFromAccountBalance(3);
        assertEquals(2, checkingAccount.getAccountBalance());
    }

    @Test
    void withdrawing_twice_from_checking_account() {
        checkingAccount.addMoneyToAccountBalance(10);
        checkingAccount.withdrawMoneyFromAccountBalance(3);
        checkingAccount.withdrawMoneyFromAccountBalance(3);
        assertEquals(4, checkingAccount.getAccountBalance());
    }

    @Test
    void savings_account_has_maximum_withdrawal_1000() {
        SavingsAccount savingsAccount = new SavingsAccount("87654321", 0.05);
        savingsAccount.addMoneyToAccountBalance(1001);
        savingsAccount.withdrawMoneyFromAccountBalance(1001);
        assertEquals(1001, savingsAccount.getAccountBalance());
    }

    @Test
    void savings_account_can_withdraw_1000() {
        SavingsAccount savingsAccount = new SavingsAccount("87654321", 0.05);
        savingsAccount.addMoneyToAccountBalance(1001);
        savingsAccount.withdrawMoneyFromAccountBalance(1000);
        assertEquals(1, savingsAccount.getAccountBalance());
    }


    @Test
    void checking_account_has_maximum_withdrawal_400() {
        checkingAccount.addMoneyToAccountBalance(1000);
        checkingAccount.withdrawMoneyFromAccountBalance(401);
        assertEquals(1000, checkingAccount.getAccountBalance());
    }

    @Test
    void checking_account_can_withdraw_400() {
        checkingAccount.addMoneyToAccountBalance(1000);
        checkingAccount.withdrawMoneyFromAccountBalance(400);
        assertEquals(600, checkingAccount.getAccountBalance());
    }

    @Test
    void withdrawing_from_cd() {
        CertificateOfDepositAccount cd = new CertificateOfDepositAccount("21436587", 0.04, 1000);
        cd.withdrawMoneyFromAccountBalance(1000);
        cd.passTimeInMonths(12);
        assertEquals(0, cd.getAccountBalance());
    }

    @Test
    void negative_values_cannot_be_withdrawn() {
        checkingAccount.addMoneyToAccountBalance(5);
        checkingAccount.withdrawMoneyFromAccountBalance(-5);
        assertEquals(5, checkingAccount.getAccountBalance());
    }

    @Test
    void withdrawing_more_than_balance_makes_balance_zero() {
        checkingAccount.addMoneyToAccountBalance(5);
        checkingAccount.withdrawMoneyFromAccountBalance(6);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void pass_0_does_nothing() {
        checkingAccount.addMoneyToAccountBalance(100);
        checkingAccount.passTimeInMonths(0);
        assertEquals(100, checkingAccount.getAccountBalance());
    }

    @Test
    void pass_1_passes_one_month() {
        checkingAccount.addMoneyToAccountBalance(100);
        checkingAccount.passTimeInMonths(1);
        assertEquals(1, checkingAccount.getMonthsSinceCreation());
    }

    @Test
    void pass_60_passes_60_months() {
        checkingAccount.addMoneyToAccountBalance(100);
        checkingAccount.passTimeInMonths(60);
        assertEquals(60, checkingAccount.getMonthsSinceCreation());
    }

    @Test
    void account_with_zero_balance_closes_after_a_month() {
        assertEquals(0, checkingAccount.getAccountBalance());
        checkingAccount.passTimeInMonths(1);
        assertEquals("closed", checkingAccount.getAccountStatus());
    }

    @Test
    void account_with_balance_more_than_zero_remains_open() {
        checkingAccount.addMoneyToAccountBalance(50);
        checkingAccount.passTimeInMonths(1);
        assertEquals("open", checkingAccount.getAccountStatus());
    }

    @Test
    void account_with_balance_less_than_100_is_charged_minimum_balance_fee() {
        checkingAccount.addMoneyToAccountBalance(99);
        checkingAccount.passTimeInMonths(1);
        assertEquals(74, checkingAccount.getAccountBalance());
    }

    @Test
    void account_with_balance_100_is_not_charged_minimum_balance_fee_and_accrues_interest() {
        checkingAccount.addMoneyToAccountBalance(100);
        checkingAccount.passTimeInMonths(1);
        assertEquals(100.01, checkingAccount.getAccountBalance());
    }

    @Test
    void account_with_balance_more_than_100_is_not_charged_minimum_balance_fee_and_accrues_interest() {
        checkingAccount.addMoneyToAccountBalance(101);
        checkingAccount.passTimeInMonths(1);
        assertEquals(101.01, checkingAccount.getAccountBalance());
    }

    @Test
    void account_with_balance_less_than_25_goes_to_zero_after_charging_minimum_balance_fee() {
        checkingAccount.addMoneyToAccountBalance(24);
        checkingAccount.passTimeInMonths(1);
        assertEquals(0, checkingAccount.getAccountBalance());
    }

    @Test
    void checking_account_calculates_apr_each_month() {
        checkingAccount.addMoneyToAccountBalance(5000);
        checkingAccount.passTimeInMonths(1);
        assertEquals(5000.25, checkingAccount.getAccountBalance());
    }

    @Test
    void checking_account_apr_calculated_for_2_months() {
        checkingAccount.addMoneyToAccountBalance(5000);
        checkingAccount.passTimeInMonths(2);
        assertEquals(5000.5, checkingAccount.getAccountBalance());
    }

    @Test
    void cd_apr_calculated_4_times_per_month() {
        CertificateOfDepositAccount cd = new CertificateOfDepositAccount("21436587", 0.04, 1000);
        cd.passTimeInMonths(1);
        assertEquals(1000.13, cd.getAccountBalance());
    }

    @Test
    void adding_command_to_transaction_history() {
        checkingAccount.storeTransactionHistory("Deposit 12345678 700");
        assertEquals("Deposit 12345678 700", checkingAccount.getTransactionHistory().get(0));

    }
}
