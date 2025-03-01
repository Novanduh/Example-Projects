package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.ArrayList;

public class CertificateOfDepositAccount extends Account {
    public CertificateOfDepositAccount(String accountID, double interestRate, double accountBalance) {
        this.accountStatus = "open";
        this.accountID = accountID;
        this.accountBalance = accountBalance;
        this.monthsSinceCreation = 0;
        this.interestRate = interestRate;
        this.accountType = "cd";
        this.transactionHistory = new ArrayList<>();
    }
}
