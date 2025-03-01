package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class CommandProcessor {
    protected Bank bank;
    protected double interestRate;
    protected String accountID, accountType;
    protected double initialBalance;
    protected double amount;
    protected String commandKeyword;
    protected String[] parsedCommand;

    protected String[] commandParser(String command) {
        parsedCommand = command.split(" ", 5);
        this.commandKeyword = parsedCommand[0];
        return command.split(" ", 5);
    }

}
