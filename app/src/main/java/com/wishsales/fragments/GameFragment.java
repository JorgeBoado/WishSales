package com.wishsales.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.UUID;

public class GameFragment extends Fragment{
    private Game mGame;
    private TextView mNameField;

    private static final String ARG_GAME_ID = "game_id";

    public static GameFragment newInstance(UUID gameId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_ID, gameId);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID gameId = (UUID) getArguments().getSerializable(ARG_GAME_ID);
        mGame = GameLab.getInstance(getActivity()).getGame(gameId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        mNameField = (TextView) v.findViewById(R.id.game_name);
        mNameField.setText(mGame.getName());

        return v;
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
