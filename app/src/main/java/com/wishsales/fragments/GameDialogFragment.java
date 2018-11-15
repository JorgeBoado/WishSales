package com.wishsales.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.UUID;

public class GameDialogFragment extends DialogFragment {
    private TextView mGameName;

    private static final String ARG_GAME_ID = "game_id";
    public static final String EXTRA_GAME = "com.wishsales.fragments.extra_game";

    public static GameDialogFragment newInstance(UUID gameID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_ID, gameID);

        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, UUID gameID) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_GAME, gameID);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
    //TODO añadir como extra boton de comprar o eliminar de la lista

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceSate) {
        UUID gameID = (UUID) getArguments().getSerializable(ARG_GAME_ID);
        Game game = GameLab.getInstance(getActivity()).getGame(gameID);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_game, null);

        mGameName = (TextView) v.findViewById(R.id.game_name);
        mGameName.setText(game.getName());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(game.getName())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
