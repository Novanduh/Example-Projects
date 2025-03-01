package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidationTest {
    WithdrawCommandValidator withdrawCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawCommandValidator = new WithdrawCommandValidator(bank);
    }

    @Test
    void valid_withdraw_command_for_checking() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        boolean actual = withdrawCommandValidator.validateWithdrawCommand("withdraw 12345678 200");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_command_for_savings() {
        bank.addSavingsAccount("savings", "12345678", 0.06);
        boolean actual = withdrawCommandValidator.validateWithdrawCommand("withdraw 12345678 900");
        assertTrue(actual);
    }

    @Test
    void valid_withdraw_command_for_cd() {
        bank.addCDAccount("cd", "12345678", 0.06, 1000);
        bank.passTime(12);
        assertTrue(withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 1500"));
    }

    @Test
    void typo_in_withdraw() {
        boolean actual = withdrawCommandValidator.validateNoTypoInCommand("with 12345678 200");
        assertFalse(actual);
    }

    @Test
    void command_is_case_insensitive() {
        boolean actual = withdrawCommandValidator.validateNoTypoInCommand("withDRAW 12345678 200");
        assertTrue(actual);
    }

    @Test
    void command_missing_withdraw() {
        boolean actual = withdrawCommandValidator.validateNoTypoInCommand("12345678 200");
        assertFalse(actual);
    }

    @Test
    void id_does_not_exist() {
        boolean actual = withdrawCommandValidator.validateAccountID("Withdraw 12345678 500");
        assertFalse(actual);
    }

    @Test
    void command_missing_ID() {
        boolean actual = withdrawCommandValidator.validateAccountID("Withdraw 500");
        assertFalse(actual);
    }

    @Test
    void withdraw_amount_is_negative() {
        boolean actual = withdrawCommandValidator.validateWithdrawAmount("withdraw 12345678 -150");
        assertFalse(actual);
    }

    @Test
    void missing_amount_for_withdrawal() {
        boolean actual = withdrawCommandValidator.validateWithdrawAmount("withdraw 12345678");
        assertFalse(actual);
    }

    @Test
    void amount_is_alphanumeric() {
        boolean actual = withdrawCommandValidator.validateWithdrawAmount("withdraw 12345678 5a0");
        assertFalse(actual);
    }

    @Test
    void cannot_withdraw_more_than_400_per_command_from_checking_account() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        boolean actual = withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 402");
        assertFalse(actual);
    }

    @Test
    void cannot_withdraw_more_than_1000_from_savings() {
        bank.addSavingsAccount("savings", "12345678", 0.06);
        boolean actual = withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 1002");
        assertFalse(actual);
    }

    @Test
    void cannot_withdraw_more_than_once_per_month_from_savings() {
        bank.addSavingsAccount("savings", "12345678", 0.06);
        boolean notActual = withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 100");
        boolean actual = withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 100");
        assertFalse(actual);
    }

    @Test
    void cannot_withdraw_less_than_balance_from_cd() {
        bank.addCDAccount("cd", "12345678", 0.06, 1000);
        bank.passTime(12);
        boolean actual = withdrawCommandValidator.validateWithdrawLimit("withdraw 12345678 900");
        assertFalse(actual);
    }
}

