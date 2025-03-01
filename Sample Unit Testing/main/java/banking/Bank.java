package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts;
    private final List<String> accountIDs;

    Bank() {
        accounts = new HashMap<>();
        accountIDs = new ArrayList<>();
    }

    public Map<String, Account> getAccountsInBank() {
        return accounts;
    }

    public void addCheckingAccount(String accountType, String accountId, double interestRate) {
        if (accountType.equals("checking")) {
            CheckingAccount account = new CheckingAccount(accountId, interestRate);
            accounts.put(accountId, account);
            accountIDs.add(accountId);
        }
    }

    public void addCDAccount(String accountType, String accountId, double interestRate, double initialBalance) {
        if (accountType.equals("cd")) {
            CertificateOfDepositAccount account = new CertificateOfDepositAccount(accountId, interestRate, initialBalance);
            accounts.put(accountId, account);
            accountIDs.add(accountId);
        }
    }

    public void addSavingsAccount(String accountType, String accountId, double interestRate) {
        if (accountType.equals("savings")) {
            SavingsAccount account = new SavingsAccount(accountId, interestRate);
            accounts.put(accountId, account);
            accountIDs.add(accountId);
        }
    }

    public void depositIntoBankAccount(String accountId, double amountToDeposit) {
        accounts.get(accountId).addMoneyToAccountBalance(amountToDeposit);
    }

    public void withdrawFromBankAccount(String accountId, double amountToWithdraw) {
        accounts.get(accountId).withdrawMoneyFromAccountBalance(amountToWithdraw);
    }

    public boolean accountWithIDExists(String accountID) {
        return accounts.get(accountID) != null;
    }

    public boolean depositAllowed(String accountID, String depositAmount) {
        String accountType = getAccountsInBank().get(accountID).getAccountType();
        return getAccountsInBank().get(accountID).depositWithinLimits(accountType, depositAmount);
    }

    public boolean withdrawAllowed(String accountID, String withdrawAmount) {
        String accountType = getAccountsInBank().get(accountID).getAccountType();
        return getAccountsInBank().get(accountID).withdrawWithinLimits(accountType, withdrawAmount);
    }

    public void passTime(int months) {
        for (String accountID : this.accountIDs) {
            getAccountsInBank().get(accountID).passTimeInMonths(months);
        }
    }

    public boolean transferAllowed(String fromID, String toID, String amount) {
        boolean transferFromAllowed = withdrawAllowed(fromID, amount);
        boolean transferToAllowed = depositAllowed(toID, amount);
        return transferFromAllowed && transferToAllowed;
    }

    public void transferBetweenAccounts(String fromID, String toID, double transferAmount) {
        getAccountsInBank().get(fromID).withdrawMoneyFromAccountBalance(transferAmount);
        getAccountsInBank().get(toID).addMoneyToAccountBalance(transferAmount);
    }
}
