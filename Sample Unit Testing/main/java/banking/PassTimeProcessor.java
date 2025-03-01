package banking;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class PassTimeProcessor extends CommandProcessor {
    private PassTimeValidator passTimeValidator;

    public PassTimeProcessor(Bank bank) {
        this.bank = bank;
        passTimeValidator = new PassTimeValidator();
    }

    public void processCommandGiven(String command) {
        String[] commandParsed = commandParser(command);
        if (passTimeValidator.validatePassTimeCommand(command)) {
            int months = Integer.parseInt(commandParsed[1]);
            bank.passTime(months);
        }
    }

}
