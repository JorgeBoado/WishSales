package com.wishsales.fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.List;

public class LibraryFragment extends Fragment{
    private RecyclerView mCrimeRecyclerView;
    private GameAdapter mAdapter;

    public static final String FRAGMENT_ID = "library_fragment";
    private static final String DIALOG_ANSWER = "game_dialog_answer";
    private static final int DIALOG_RESPONSE = 0;

    /**
     * Creates a new instance of LibraryFragment
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
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return A new view with all the games in library
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.library_fragment);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        GameLab gameLab = GameLab.getInstance(getActivity());
        List<Game> games = gameLab.getGames(Game.IN_LIBRARY);

        if (mAdapter == null) {
            mAdapter = new GameAdapter(games);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Game mGame;
        private TextView mTitleTextView;
        private Button mPlayButton;

        /**
         * Creates a new GameHolder with the especified view
         * @param inflater
         * @param parent View holding the GameHolder
         * @param viewType Time of view to display
         */
        public GameHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.library_game_title);
            mPlayButton = (Button) itemView.findViewById(R.id.library_game_button);
        }

        /**
         * Action to perform when item clicked.
         * Shows a toast with a message.
         * @param v Clicked view
         */
        @Override
        public void onClick(View v) {
            GameDialogFragment dialog = GameDialogFragment.newInstance(mGame.getId(), false);
            dialog.setTargetFragment(LibraryFragment.this, DIALOG_RESPONSE);
            dialog.show(getFragmentManager(), DIALOG_ANSWER);
        }

        /**
         * Binds a game data to the holder
         * @param game Game to be binded
         */
        public void bind(Game game) {
            mGame = game;

            mTitleTextView.setText(mGame.getName());
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Ejecutando "+mGame.getName()+"...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {
        private List<Game> mGames;

        /**
         * Assigns a game list to the adapter
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
