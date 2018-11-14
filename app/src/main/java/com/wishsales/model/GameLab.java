package com.wishsales.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameLab {
    private static GameLab ourInstance;
    private List<Game> mGames;


    public static GameLab getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new GameLab(context);
        }
        return ourInstance;
    }

    private GameLab(Context context) {
        mGames = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            int discount = (int) (Math.random() * 100);
            Game game = new Game();

            game.setName("Game #" + i);
            game.setDescription("Game #" + i + " description");
            game.setPrice(3 + ((i + 1) / 2));
            game.setInSale(i % 2 == 0);
            game.setDiscount(game.isInSale() ? discount : 0);
            game.setFinalPrice(discount == 0 ? game.getPrice() : game.getPrice() * (discount / 100));
            game.setDisposition(Game.IN_STORE);
            game.setPortada(i % 2 == 0 ? "" : ""); // TODO cambiar el texto por R.findid

            if (i % 5 == 0) {
                if (i % 10 == 0) {
                    game.setDisposition(Game.IN_WISHLIST);
                } else {
                    game.setDisposition(Game.IN_LIBRARY);
                }
            }

            mGames.add(game);
        }

    }

    public List<Game> getGames() {
        return mGames;
    }

    public List<Game> getGames(byte disposition) {
        List<Game> games = new ArrayList<>();
        for (Game game : mGames) {
            if (game.getDisposition() == disposition)
                games.add(game);
        }
        return games;
    }

    public Game getGame(UUID id) {
        for (Game game : mGames) {
            if (game.getId().equals(id))
                return game;
        }
        return null;
    }
}
