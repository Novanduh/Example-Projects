package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidationTest {
    CreateCommandValidator createCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
    }

    @Test
    void valid_create_cd_command() {
        boolean actual = createCommandValidator.validateCreateCommand("Create cd 12345678 1.2 2000");
        assertTrue(actual);
    }

    @Test
    void valid_create_saving_command() {
        boolean actual = createCommandValidator.validateCreateCommand("Create savings 98765432 0.6");
        assertTrue(actual);
    }

    @Test
    void command_is_case_insensitive() {
        boolean actual = createCommandValidator.validateNoTypoInCommand("creAte cHecKing 98765432 0.01");
        assertTrue(actual);
    }

    @Test
    void command_missing_Create() {
        boolean actual = createCommandValidator.validateNoTypoInCommand("checking 12345678 0.06");
        assertFalse(actual);
    }

    @Test
    void typo_in_create() {
        boolean actual = createCommandValidator.validateNoTypoInCommand("Creat checking 12345678 0.06");
        assertFalse(actual);
    }

    @Test
    void command_missing_Account_Type() {
        boolean actual = createCommandValidator.validateNoTypoInCommand("Create 12345678 0.06");
        assertFalse(actual);
    }

    @Test
    void typo_in_account_Type() {
        boolean actual = createCommandValidator.validateNoTypoInCommand("Create saving 12345678 0.06");
        assertFalse(actual);
    }

    @Test
    void command_missing_ID() {
        boolean actual = createCommandValidator.validateAccountID("Create checking 0.06");
        assertFalse(actual);
    }

    @Test
    void account_ID_already_exists() {
        bank.addCheckingAccount("checking", "12345678", 0.06);
        boolean actual = createCommandValidator.validateAccountID("Create checking 12345678 0.01");
        assertFalse(actual);
    }

    @Test
    void account_ID_is_alphanumeric() {
        boolean actual = createCommandValidator.validateAccountID("Create checking 1b3d5f7h 0.01");
        assertFalse(actual);
    }

    @Test
    void account_ID_is_more_than_eight_digits() {
        boolean actual = createCommandValidator.validateAccountID("Create checking 123456789 0.01");
        assertFalse(actual);
    }

    @Test
    void initial_balance_for_cd_missing() {
        boolean actual = createCommandValidator.validateCDCommand("Create cd 12345678 0.06");
        assertFalse(actual);
    }

    @Test
    void initial_balance_for_cd_is_negative() {
        boolean actual = createCommandValidator.validateCDCommand("Create cd 12345678 0.06 -1000");
        assertFalse(actual);
    }

    @Test
    void initial_balance_for_cd_is_less_than_1000() {
        boolean actual = createCommandValidator.validateCDCommand("Create cd 12345678 0.06 500");
        assertFalse(actual);
    }

    @Test
    void apr_is_negative() {
        boolean actual = createCommandValidator.validateAPR("Create checking 12345678 -0.05");
        assertFalse(actual);
    }

    @Test
    void apr_contains_alphabets() {
        boolean actual = createCommandValidator.validateAPR("Create checking 12345678 a5");
        assertFalse(actual);
    }

    @Test
    void command_missing_apr() {
        boolean actual = createCommandValidator.validateAPR("Create checking 12345678");
        assertFalse(actual);
    }

    @Test
    void apr_not_in_percentage_form() {
        boolean actual = createCommandValidator.validateAPR("Create checking 12345678 50");
        assertFalse(actual);
    }

    @Test
    void maximum_apr_allowed_is_10() {
        boolean actual = createCommandValidator.validateAPR("Create cd 12345678 10.1 2000");
        assertFalse(actual);
    }

    @Test
    void zero_apr_is_valid() {
        boolean actual = createCommandValidator.validateAPR("Create cd 12345678 0 2000");
        assertFalse(actual);
    }
}
