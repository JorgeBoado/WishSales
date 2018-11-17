package com.wishsales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.AppComponentFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wishsales.fragments.GameFragment;
import com.wishsales.model.Game;
import com.wishsales.model.GameLab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StorePagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Game> mGames;
    private Button mFirstButton;
    private Button mLastButton;

    public static final String EXTRA_GAME_ID = "com.wishsales.pager_game_id";

    public static Intent newIntent(Context context, UUID gameID) {
        Intent intent = new Intent(context, StorePagerActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_pager);

        UUID gameID = (UUID) getIntent().getSerializableExtra(EXTRA_GAME_ID);

        mViewPager = (ViewPager) findViewById(R.id.store_view_pager);
        mFirstButton = (Button) findViewById(R.id.first_index_button);
        mLastButton = (Button) findViewById(R.id.last_index_button);
        mGames = GameLab.getInstance(this).getGames();

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Game game = mGames.get(i);
                return GameFragment.newInstance(game.getId());
            }

            @Override
            public int getCount() {
                return mGames.size();
            }
        });

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0, true);
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mGames.size()-1, true);
            }
        });

        for (int i = 0; i < mGames.size(); i++) {
            if(mGames.get(i).getId().equals(gameID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
