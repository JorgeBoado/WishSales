package com.wishsales.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.UUID;

public class GameDialogFragment extends DialogFragment {
    private Game mGame;
    private TextView mNameField;
    private TextView mDescriptionField;
    private TextView mActPriceField;
    private Button mBuyButton;
    private Switch mWishSwitch;
    private ImageView mCoverImage;

    private static final String ARG_GAME_ID = "game_id";
    public static final String EXTRA_GAME = "com.wishsales.fragments.extra_game";

    public static GameDialogFragment newInstance(UUID gameID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_ID, gameID);

        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Game game) {
        GameLab.getInstance(getActivity())
                .getGame(game.getId())
                .setDisposition(game.getDisposition());

        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_GAME, game.getId());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
    // TODO añadir un fragmento nuevo con informacion del juego pero sin botones
    // TODO añadir como extra boton del dialog para comprar o eliminar de la lista (fuera del fragment)


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID gameID = (UUID) getArguments().getSerializable(ARG_GAME_ID);
        mGame = GameLab.getInstance(getActivity()).getGame(gameID);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceSate) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_game, null);

        mNameField = (TextView) v.findViewById(R.id.view_game_name);
        mNameField.setText(mGame.getName());
        mDescriptionField = (TextView) v.findViewById(R.id.view_game_description);
        mDescriptionField.setText(mGame.getDescription());
        mActPriceField = (TextView) v.findViewById(R.id.view_game_price);
        mActPriceField.setText(String.valueOf(mGame.getFinalPrice()));
        mCoverImage = (ImageView) v.findViewById(R.id.view_game_cover);
        mCoverImage.setImageResource(mGame.getPortada());

        mBuyButton = (Button) v.findViewById(R.id.view_game_buy);
        if (mGame.getDisposition() == Game.IN_LIBRARY) {
            mBuyButton.setEnabled(false);
            mBuyButton.setText("En posesion");
        }
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // TODO actualizar lista o quitar boton
                mGame.buy();
                mWishSwitch.setChecked(false);
                mWishSwitch.setEnabled(false);
                mBuyButton.setEnabled(false);
                mBuyButton.setText("En posesion");
            }
        });

        mWishSwitch = (Switch) v.findViewById(R.id.view_game_wish);
        mWishSwitch.setChecked(mGame.getDisposition() == Game.IN_WISHLIST);
        if (mGame.getDisposition() == Game.IN_LIBRARY) {
            mWishSwitch.setEnabled(false);
        }
        mWishSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    mGame.addWish();
                else
                    mGame.removeWish();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sendResult(Activity.RESULT_OK, game);
                    }
                })
                .create();
    }
}
