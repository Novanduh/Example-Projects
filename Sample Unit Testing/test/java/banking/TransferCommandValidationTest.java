package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandValidationTest {
    TransferCommandValidator transferCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferCommandValidator = new TransferCommandValidator(bank);
    }

    @Test
    void valid_transfer_test() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        bank.addCheckingAccount("checking", "23456789", 0.06);
        boolean actual = transferCommandValidator.validateAccountID("transfer 12345678 23456789 200");
        assertTrue(actual);
    }

    @Test
    void typo_in_transfer() {
        boolean actual = transferCommandValidator.validateNoTypoInCommand("transf 12345678 23456789 200");
        assertFalse(actual);
    }

    @Test
    void command_is_case_insensitive() {
        boolean actual = transferCommandValidator.validateNoTypoInCommand("transfER 12345678 23456789 200");
        assertTrue(actual);
    }

    @Test
    void command_missing_transfer() {
        boolean actual = transferCommandValidator.validateNoTypoInCommand("12345678 200");
        assertFalse(actual);
    }

    @Test
    void first_id_does_not_exist() {
        bank.addCheckingAccount("checking", "23456789", 0.05);
        boolean actual = transferCommandValidator.validateAccountID("transfer 12345678 23456789 500");
        assertFalse(actual);
    }

    @Test
    void second_id_does_not_exist() {
        bank.addCheckingAccount("checking", "12345678", 0.05);
        boolean actual = transferCommandValidator.validateAccountID("transfer 12345678 23456789 500");
        assertFalse(actual);
    }

    @Test
    void command_missing_ID() {
        boolean actual = transferCommandValidator.validateAccountID("transfer 12345678 500");
        assertFalse(actual);
    }

    @Test
    void transfer_amount_is_negative() {
        boolean actual = transferCommandValidator.validateTransferAmount("transfer 12345678 23456789 -150");
        assertFalse(actual);
    }

    @Test
    void missing_amount_for_transfer() {
        boolean actual = transferCommandValidator.validateTransferAmount("transfer 12345678 23456789");
        assertFalse(actual);
    }

    @Test
    void amount_is_alphanumeric() {
        boolean actual = transferCommandValidator.validateTransferAmount("transfer 12345678 23456789 5a0");
        assertFalse(actual);
    }

    @Test
    void checking_withdraw_rules_apply_to_transfer() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        bank.addCheckingAccount("checking", "23456789", 0.06);
        boolean actual = transferCommandValidator.validateTransferLimit("transfer 12345678 23456789 402");
        assertFalse(actual);
    }

    @Test
    void savings_withdraw_rules_apply_to_transfer() {
        bank.addSavingsAccount("savings", "12345678", 0.06);
        bank.addCheckingAccount("checking", "23456789", 0.06);
        boolean actual = transferCommandValidator.validateTransferLimit("transfer 12345678 23456789 1005");
        assertFalse(actual);
    }

    @Test
    void cannot_transfer_to_cd() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        bank.addCDAccount("cd", "23456789", 0.06, 1000);
        boolean actual = transferCommandValidator.validateTransferLimit("transfer 12345678 23456789 402");
        assertFalse(actual);
    }
}
