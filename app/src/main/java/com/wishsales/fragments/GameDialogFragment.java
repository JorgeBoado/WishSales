package com.wishsales.fragments;

import android.app.Activity;
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
    private boolean mMoreOptions;
    private TextView mNameField;
    private TextView mDescriptionField;
    private TextView mActPriceField;
    private ImageView mCoverImage;

    private static final String ARG_GAME_ID = "game_id";
    private static final String ARG_HAS_OPTIONS = "has_more_options";

    public static final String EXTRA_GAME = "com.wishsales.fragments.extra_game";
    public static final int RESULT_BUY = 1;
    public static final int RESULT_REMOVE = 2;

    /**
     * Creates a new instance for the dialog with the id of the game to be shown in args
     * @param gameID Id of the game wanted to be shown
     * @return New GameDialogFragment for the especified game
     */
    public static GameDialogFragment newInstance(UUID gameID, boolean hasStoreOptions) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_ID, gameID);
        args.putBoolean(ARG_HAS_OPTIONS, hasStoreOptions);

        GameDialogFragment fragment = new GameDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_GAME, mGame.getId());
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    /**
     * Creates de fragment and assigns the game with the game id saved in args
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID gameID = (UUID) getArguments().getSerializable(ARG_GAME_ID);
        mGame = GameLab.getInstance(getActivity()).getGame(gameID);
        mMoreOptions = getArguments().getBoolean(ARG_HAS_OPTIONS);
    }

    /**
     * Creates a dialog with a view containing game info inside
     * @param savedInstanceSate
     * @return A new dialog with 3 buttons with diferent return values: -1 Ok | 1 Buy | 2 Remove from wishlist
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceSate) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_game_information, null);

        mNameField = (TextView) v.findViewById(R.id.view_game_name);
        mNameField.setText(mGame.getName());
        mDescriptionField = (TextView) v.findViewById(R.id.view_game_description);
        mDescriptionField.setText(mGame.getDescription());
        mActPriceField = (TextView) v.findViewById(R.id.view_game_price);
        mActPriceField.setText(String.valueOf(mGame.getFinalPrice()));
        mCoverImage = (ImageView) v.findViewById(R.id.view_game_cover);
        mCoverImage.setImageResource(mGame.getPortada());

        if (mMoreOptions) {
            return new AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setNegativeButton(R.string.dialog_remove, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendResult(RESULT_REMOVE);
                        }
                    })
                    .setPositiveButton(R.string.dialog_buy, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendResult(RESULT_BUY);
                        }
                    })
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendResult(Activity.RESULT_OK);
                        }
                    })
                    .create();
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v).setPositiveButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
}
