package com.wishsales.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.UUID;

public class GameFragment extends Fragment {
    private Game mGame;
    private TextView mNameField;
    private TextView mDescriptionField;
    private TextView mOldPriceField;
    private TextView mActPriceField;
    private Button mBuyButton;
    private Switch mWishSwitch;
    private ImageView mCoverImage;


    private static final String ARG_GAME_ID = "game_id";

    /**
     * Creates a new instance of GameFragment for the wanted game
     * @param gameId Id of the wanted game info to be shown
     * @return A new GameFragment with the especified game id saved in args
     */
    public static GameFragment newInstance(UUID gameId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME_ID, gameId);

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates the fragment and assigns the game with the game id in the args
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID gameId = (UUID) getArguments().getSerializable(ARG_GAME_ID);
        mGame = GameLab.getInstance(getActivity()).getGame(gameId);
    }

    /**
     * Creates the view for the especified game
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return The view with all the data binded
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(mGame.isInSale() ? R.layout.fragment_game_sale : R.layout.fragment_game, container, false);

        mNameField = (TextView) v.findViewById(R.id.view_game_name);
        mNameField.setText(mGame.getName());

        mDescriptionField = (TextView) v.findViewById(R.id.view_game_description);
        mDescriptionField.setText(mGame.getDescription());

        mActPriceField = (TextView) v.findViewById(R.id.view_game_price);
        mActPriceField.setText(String.valueOf(mGame.getFinalPrice()) + "€");

        if (mGame.isInSale()) {
            mOldPriceField = (TextView) v.findViewById(R.id.view_game_price_old);
            mOldPriceField.setText(String.valueOf(mGame.getPrice()) + "€");
        }

        mCoverImage = (ImageView) v.findViewById(R.id.view_game_cover);
        mCoverImage.setImageResource(mGame.getPortada());

        mWishSwitch = (Switch) v.findViewById(R.id.view_game_wish);
        mWishSwitch.setChecked(mGame.getDisposition() == Game.IN_WISHLIST);
        mWishSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGame.addWish();
                    Toast.makeText(getContext(), R.string.toast_add_wish, Toast.LENGTH_SHORT).show();
                } else {
                    mGame.removeWish();
                    Toast.makeText(getContext(), R.string.toast_remove_wish, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mBuyButton = (Button) v.findViewById(R.id.view_game_buy);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGame.buy()) {
                    buyGame();
                    Toast.makeText(getContext(), R.string.toast_thank_buy, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.toast_not_funds, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (mGame.getDisposition() == Game.IN_LIBRARY) {
            buyGame();
        }

        return v;
    }

    private void buyGame() {
        mWishSwitch.setChecked(false);
        mWishSwitch.setEnabled(false);
        mBuyButton.setEnabled(false);
        mBuyButton.setText(R.string.toast_owned);
    }

    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
