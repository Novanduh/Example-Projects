package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {

    MasterControl masterControl;
    private List<String> input;

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @BeforeEach
    void setUp() {
        Bank bank = new Bank();
        masterControl = new MasterControl(bank, new CreateCommandProcessor(bank), new DepositCommandProcessor(bank), new
                WithdrawCommandProcessor(bank), new TransferCommandProcessor(bank), new PassTimeProcessor(bank), new CommandStorage(bank));
        input = new ArrayList<>();
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");
        List<String> actual = masterControl.start(input);
        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("depositt 12345678 100", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 1.0");
        input.add("create checking 12345678 1.0");
        List<String> actual = masterControl.start(input);
        assertSingleCommand("create checking 12345678 1.0", actual);
    }

    @Test
    void one_valid_create_command() {
        input.add("Create savings 12345678 0.6");
        List<String> actual = masterControl.start(input);
        assertEquals(0, actual.size());
    }

    @Test
    void one_valid_create_cd_command() {
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);
        assertEquals(1, actual.size());
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(0));
    }


    @Test
    void open_account_history_gets_printed_after_pass_time() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Deposit 12345678 5000", actual.get(3));
    }


    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }
}
