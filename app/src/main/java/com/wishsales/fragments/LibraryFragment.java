package com.wishsales.fragments;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;
import com.wishsales.model.Wallet;

import java.util.List;

public class LibraryFragment extends Fragment {
    private Wallet mWallet;
    private RecyclerView mCrimeRecyclerView;
    private GameAdapter mAdapter;
    private TextView mWalletFunds;

    public static final String FRAGMENT_ID = "library_fragment";
    private static final String DIALOG_GAME_ANSWER = "game_dialog_answer";
    private static final String DIALOG_FUND_ANSWER = "fund_dialog_answer";
    private static final int DIALOG_RESPONSE = 0;

    /**
     * Creates a new instance of LibraryFragment
     *
     * @return A new LibraryFragment
     */
    public static LibraryFragment newInstance() {
        Bundle args = new Bundle();

        LibraryFragment fragment = new LibraryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new view with a list of games in library
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return A new view with all the games in library
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library, container, false);

        mWallet = Wallet.getInstance();
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.library_fragment);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWalletFunds = (TextView) v.findViewById(R.id.library_funds);
        mWalletFunds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FundsDialogFragment dialog = FundsDialogFragment.newInstance(Wallet.getInstance().getmCurrentFunds());
                dialog.setTargetFragment(LibraryFragment.this, DIALOG_RESPONSE);
                dialog.show(getFragmentManager(), DIALOG_FUND_ANSWER);
            }
        });

        updateUI();
        return v;
    }

    private void updateUI() {
        GameLab gameLab = GameLab.getInstance(getActivity());
        List<Game> games = gameLab.getGames(Game.IN_LIBRARY);
        double funds = ((int)(Wallet.getInstance().getmCurrentFunds() * 100.0)) / 100.0;
        mWalletFunds.setText(String.valueOf(funds) + getString(R.string.badge));

        mAdapter = new GameAdapter(games);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Game mGame;
        private ImageView mCoverImageView;
        private TextView mTitleTextView;
        private Button mPlayButton;

        /**
         * Creates a new GameHolder with the especified view
         *
         * @param inflater
         * @param parent   View holding the GameHolder
         * @param viewType Time of view to display
         */
        public GameHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            mCoverImageView = (ImageView) itemView.findViewById(R.id.library_game_image);
            mTitleTextView = (TextView) itemView.findViewById(R.id.library_game_title);
            mPlayButton = (Button) itemView.findViewById(R.id.library_game_button);
        }

        /**
         * Action to perform when item clicked.
         * Shows a toast with a message.
         *
         * @param v Clicked view
         */
        @Override
        public void onClick(View v) {
            GameDialogFragment dialog = GameDialogFragment.newInstance(mGame.getId(), false);
            dialog.setTargetFragment(LibraryFragment.this, DIALOG_RESPONSE);
            dialog.show(getFragmentManager(), DIALOG_GAME_ANSWER);
        }

        /**
         * Binds a game data to the holder
         *
         * @param game Game to be binded
         */
        public void bind(Game game) {
            mGame = game;
            mCoverImageView.setImageResource(mGame.getPortada());
            mTitleTextView.setText(mGame.getName());
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), getString(R.string.toast_launch) + mGame.getName() + getString(R.string.toast_dotted_line), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {
        private List<Game> mGames;

        /**
         * Assigns a game list to the adapter
         *
         * @param games
         */
        public GameAdapter(List<Game> games) {
            mGames = games;
        }

        @NonNull
        @Override
        public GameHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GameHolder(layoutInflater, viewGroup, i);
        }

        @Override
        public void onBindViewHolder(@NonNull GameHolder holder, int position) {
            holder.bind(mGames.get(position));
        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.library_item_game;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED)
            return;

        mWallet.modifyFunds(data.getDoubleExtra(FundsDialogFragment.EXTRA_AMOUNT, 0));
        updateUI();
    }

    /**
     * Action to perform on resume.
     * Updates the list shown.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
