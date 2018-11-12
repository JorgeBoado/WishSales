package com.wishsales;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class GameActivity extends SingleFragmentActivity {
    public static final String EXTRA_GAME_ID = "com.wishsales.game_id";

    public static Intent newIntent(Context context, UUID gameId) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID gameid = (UUID) getIntent().getSerializableExtra(EXTRA_GAME_ID);
        return GameFragment.newInstance(gameid);
    }
}
