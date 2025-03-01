package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidationTest {
    PassTimeValidator passTimeValidator;

    @BeforeEach
    void setUp() {
        passTimeValidator = new PassTimeValidator();
    }

    @Test
    void valid_pass_time_command() {
        boolean actual = passTimeValidator.validatePassTimeCommand("pass 2");
        assertTrue(actual);
    }

    @Test
    void typo_in_pass() {
        boolean actual = passTimeValidator.validateNoTypoInCommand("pas 1");
        assertFalse(actual);
    }

    @Test
    void command_is_case_insensitive() {
        boolean actual = passTimeValidator.validateNoTypoInCommand("paSS 1");
        assertTrue(actual);
    }

    @Test
    void command_missing_pass() {
        boolean actual = passTimeValidator.validateNoTypoInCommand("2");
        assertFalse(actual);
    }

    @Test
    void number_of_months_cannot_be_zero() {
        boolean actual = passTimeValidator.validateNumberOfMonths("pass 0");
        assertFalse(actual);
    }

    @Test
    void number_of_months_cannot_be_more_than_60() {
        boolean actual = passTimeValidator.validateNumberOfMonths("pass 61");
        assertFalse(actual);
    }

    @Test
    void command_missing_months() {
        boolean actual = passTimeValidator.validateNumberOfMonths("pass");
        assertFalse(actual);
    }
}
