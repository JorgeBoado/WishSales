package com.wishsales.model;

import java.util.UUID;

public class Game {
    private UUID mId;
    private String mName;
    private String mDescription;
    private double mPrice;
    private double mFinalPrice;
    private boolean mInSale;
    private int mPortada;
    private int mDiscount;
    private byte mDisposition;

    public static final byte IN_STORE = 0;
    public static final byte IN_WISHLIST = 1;
    public static final byte IN_LIBRARY = 2;

    public Game(int portada, String name, String description, double price, int discount) {
        this();
        mPortada = portada;
        mName = name;
        mDescription = description;
        mPrice = price;
        mInSale = discount > 0;
        mDiscount = discount;
        mFinalPrice = mInSale ? mPrice * (mDiscount / 100) : mPrice;
        mDisposition = 0;
    }

    public Game() {
        mId = UUID.randomUUID();
    }

    public byte getDisposition() {
        return mDisposition;
    }

    public void setDisposition(byte disposition) {
        mDisposition = disposition;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public int getPortada() {
        return mPortada;
    }

    public void setPortada(int portada) {
        mPortada = portada;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getFinalPrice() {
        return mFinalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        mFinalPrice = finalPrice;
    }

    public int getDiscount() {
        return mDiscount;
    }

    public void setDiscount(int discount) {
        mDiscount = discount;
    }

    public boolean isInSale() {
        return mInSale;
    }

    public void setInSale(boolean inSale) {
        mInSale = inSale;
    }

    public void buy() {
        mDisposition = IN_LIBRARY;
    }

    public void addWish() {
        mDisposition = IN_WISHLIST;
    }

    public void removeWish() {
        mDisposition = IN_STORE;
    }

}
