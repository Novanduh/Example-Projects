package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidationTest {
    DepositCommandValidator depositCommandValidator;
    Bank bank;


    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositCommandValidator = new DepositCommandValidator(bank);
    }

    @Test
    void valid_deposit_command() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        boolean actual = depositCommandValidator.validateDepositCommand("Deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void command_is_case_insensitive() {
        boolean actual = depositCommandValidator.validateNoTypoInCommand("dEposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void id_does_not_exist() {
        boolean actual = depositCommandValidator.validateAccountID("Deposit 12345678 500");
        assertFalse(actual);
    }

    @Test
    void command_missing_ID() {
        boolean actual = depositCommandValidator.validateAccountID("Deposit 500");
        assertFalse(actual);
    }

    @Test
    void command_missing_deposit() {
        boolean actual = depositCommandValidator.validateNoTypoInCommand("12345678 500");
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit() {
        boolean actual = depositCommandValidator.validateNoTypoInCommand("Depoit 12345678 500");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_is_negative() {
        boolean actual = depositCommandValidator.validateDepositAmount("Deposit 12345678 -150");
        assertFalse(actual);
    }

    @Test
    void missing_amount_for_deposit() {
        boolean actual = depositCommandValidator.validateDepositAmount("Deposit 12345678");
        assertFalse(actual);
    }

    @Test
    void amount_is_alphanumeric() {
        boolean actual = depositCommandValidator.validateDepositAmount("Deposit 12345678 5a0");
        assertFalse(actual);
    }

    @Test
    void cannot_deposit_into_cd() {
        bank.addCDAccount("cd", "87654321", 0.06, 1000);
        boolean actual = depositCommandValidator.validateDepositLimit("Deposit 87654321 500");
        assertFalse(actual);
    }

    @Test
    void cannot_deposit_more_than_1000_into_checking_account() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        boolean actual = depositCommandValidator.validateDepositLimit("Deposit 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void cannot_deposit_more_than_2500_into_savings_account() {
        bank.addSavingsAccount("savings", "12345678", 0.06);
        boolean actual = depositCommandValidator.validateDepositLimit("Deposit 12345678 2501");
        assertFalse(actual);
    }
}
