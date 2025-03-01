package banking;

import java.util.ArrayList;

/*Samprati Sinha
 * sss396
 * SE181 Course Project*/
public class SavingsAccount extends Account {
    public SavingsAccount(String accountID, double interestRate) {
        this.accountStatus = "open";
        this.accountID = accountID;
        this.accountBalance = 0;
        this.monthsSinceCreation = 0;
        this.interestRate = interestRate;
        this.accountType = "savings";
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public void withdrawMoneyFromAccountBalance(double withdrawalAmount) {
        if (withdrawalAmount <= 1000) {
            super.withdrawMoneyFromAccountBalance(withdrawalAmount);
        }
    }
}
