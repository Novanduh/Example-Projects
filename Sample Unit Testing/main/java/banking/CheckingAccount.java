package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.ArrayList;

public class CheckingAccount extends Account {
    public CheckingAccount(String accountID, double interestRate) {
        this.accountStatus = "open";
        this.accountID = accountID;
        this.accountBalance = 0;
        this.monthsSinceCreation = 0;
        this.interestRate = interestRate;
        this.accountType = "checking";
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public void withdrawMoneyFromAccountBalance(double withdrawalAmount) {
        if (withdrawalAmount <= 400) {
            super.withdrawMoneyFromAccountBalance(withdrawalAmount);
        }
    }
}
