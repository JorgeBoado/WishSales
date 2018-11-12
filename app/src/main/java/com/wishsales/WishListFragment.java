package com.wishsales;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class WishListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;


    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Game mGame;

        public GameHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            itemView.setOnClickListener(this);

            // TODO Asociar componentes con la id de R
        }

        @Override
        public void onClick(View v) {
            Intent intent = GameActivity.newIntent(getActivity(), mGame.getId());
            startActivity(intent);
        }

        public void bind(Game game){
            mGame = game;

            // TODO Poner datos en componentes
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
            //return R.layout.list_item_game;
            return 0;
        }

    }

}
