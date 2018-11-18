package com.wishsales.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wishsales.GameActivity;
import com.wishsales.R;
import com.wishsales.StorePagerActivity;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.List;

public class StoreFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private GameAdapter mAdapter;
    private Button mAddGameButton;

    public static final String FRAGMENT_ID = "store_fragment";

    public static StoreFragment newInstance() {
        Bundle args = new Bundle();

        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store, container, false);

        /*
        mAddGameButton = (Button) v.findViewById(R.id.); // TODO añadir id
        mAddGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Crear dialog para creacion de juego
            }
        });*/

        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.store_fragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mCrimeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mCrimeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        }
        updateUI();
        return v;
    }

    private void updateUI() {
        GameLab gameLab = GameLab.getInstance(getActivity());
        List<Game> games = gameLab.getGames();

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
        private ImageView mCoverImage;

        public GameHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.grid_buy_button);
            mCoverImage = (ImageView) itemView.findViewById(R.id.grid_game_cover);
        }

        @Override
        public void onClick(View v) {
            Intent intent = StorePagerActivity.newIntent(getActivity(), mGame.getId());
            startActivity(intent);
        }

        public void bind(Game game) {
            mGame = game;
            mTitleTextView.setText(mGame.getName());
            mCoverImage.setImageResource(mGame.getPortada());
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
                return R.layout.grid_item_onsale;
            }
            return R.layout.grid_item_nosale;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
}
