package com.wishsales.model;

public class Wallet {
    private static final Wallet ourInstance = new Wallet();
    private double mCurrentFunds;
    private double mSpentMoney;

    public static Wallet getInstance() {
        return ourInstance;
    }

    private Wallet() {
        mCurrentFunds = 0;
        mSpentMoney = 0;
    }

    public double getmCurrentFunds() {
        return mCurrentFunds;
    }

    public double getmSpentMoney() {
        return mSpentMoney;
    }

   public void modifyFunds(double funds) {
        mCurrentFunds += funds;
   }

   public void spend(double price) {
        if (price < 0 )
            return;

        modifyFunds(-price);
        mSpentMoney += price;
   }

}
