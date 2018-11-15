package com.wishsales.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishsales.R;
import com.wishsales.model.Game;
import com.wishsales.GameActivity;
import com.wishsales.model.GameLab;

import java.util.List;

public class WishListFragment extends Fragment {
    public static final String FRAGMENT_ID = "wishlist_fragment";
    private static final String DIALOG_ANSWER = "game_dialog_answer";
    private static final int DIALOG_RESPONSE = 0;

    private RecyclerView mCrimeRecyclerView;
    private GameAdapter mAdapter;

    public static WishListFragment newInstance() {
        Bundle args = new Bundle();

        WishListFragment fragment = new WishListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wishlist, container, false);
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.wishlist_fragment);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        GameLab gameLab = GameLab.getInstance(getActivity());
        List<Game> games = gameLab.getGames(Game.IN_WISHLIST);

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

        public GameHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_game_name);
        }

        @Override
        public void onClick(View v) {
            GameDialogFragment dialog = GameDialogFragment.newInstance(mGame.getId());
            dialog.setTargetFragment(WishListFragment.this, DIALOG_RESPONSE);
            dialog.show(getFragmentManager(), DIALOG_ANSWER);
        }

        public void bind(Game game) {
            mGame = game;

            mTitleTextView.setText(mGame.getName());
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {
        private List<Game> mGames;

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
            if (mGames.get(position).isInSale()) {
                return R.layout.list_item_game_sale;
            }
            return R.layout.list_item_game;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
