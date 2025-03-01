package banking;
/*Samprati Sinha
 * sss396
 * SE181 Course Project*/

import java.util.List;

abstract class Account {
    public List<String> transactionHistory;
    protected String accountType;
    protected String accountID;
    protected double accountBalance;
    protected double interestRate;
    protected int monthsSinceCreation;
    protected String accountStatus;
    private int numberOfWithdrawal;

    public String getAccountID() {
        return accountID;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void addMoneyToAccountBalance(double depositAmount) {
        if (depositAmount > 0) {
            this.accountBalance += depositAmount;
        }
    }

    public void withdrawMoneyFromAccountBalance(double withdrawalAmount) {
        if ((accountBalance >= withdrawalAmount) && (withdrawalAmount > 0)) {
            this.accountBalance -= withdrawalAmount;
        } else if (withdrawalAmount > accountBalance) {
            this.accountBalance = 0;
        }
    }

    public boolean isInterestRateValid() {
        return !(interestRate < 0.00);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public boolean depositWithinLimits(String accountType, String depositAmount) {
        double amount = Double.parseDouble(depositAmount);
        boolean depositAllowed = false;
        if (accountType.equals("checking")) {
            depositAllowed = (amount <= 1000);
        }
        if (accountType.equals("savings")) {
            depositAllowed = (amount <= 2500);
        }
        return depositAllowed;
    }

    public boolean withdrawWithinLimits(String accountType, String withdrawAmount) {
        double amount = Double.parseDouble(withdrawAmount);
        boolean withdrawAllowed = false;
        if (accountType.equals("checking")) {
            withdrawAllowed = (amount <= 400);
        }
        if (accountType.equals("savings")) {
            withdrawAllowed = (amount <= 1000) && (this.numberOfWithdrawal == 0);
            this.numberOfWithdrawal = 1;
        }
        if (accountType.equals("cd")) {
            withdrawAllowed = (this.accountBalance < amount);
        }
        return withdrawAllowed;
    }

    public void passTimeInMonths(int months) {
        if (this.accountBalance == 0) {
            this.accountStatus = "closed";
        } else {
            this.monthsSinceCreation += months;
            if (this.accountBalance < 100) {
                this.accountBalance -= 25;
                if (this.accountBalance < 0) {
                    this.accountBalance = 0;
                }
            }
            for (int i = 0; i < months; i++) {
                calculateAPR();
            }
            this.numberOfWithdrawal = 0;
        }
    }

    private void calculateAPR() {
        if (accountType.equals("cd")) {
            for (int i = 0; i < 4; i++) {
                this.accountBalance = this.accountBalance + ((this.interestRate / 100) / 12) * this.accountBalance;
            }
        } else {
            this.accountBalance = this.accountBalance + ((this.interestRate / 100) / 12) * this.accountBalance;
        }
        this.accountBalance = Math.round(this.accountBalance * 100.0) / 100.0;
    }

    public int getMonthsSinceCreation() {
        return monthsSinceCreation;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public void storeTransactionHistory(String command) {
        try {
            this.transactionHistory.add(command);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }
    }

    public String getCurrentState() {
        String titleCaseAccountType = "";
        switch (accountType) {
            case "savings":
                titleCaseAccountType = "Savings";
                break;
            case "checking":
                titleCaseAccountType = "Checking";
                break;
            case "cd":
                titleCaseAccountType = "Cd";
                break;
        }
        return String.format("%s %s %.2f %.2f", titleCaseAccountType, accountID, accountBalance, interestRate);
    }
}
