package com.wishsales;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.wishsales.fragments.GameFragment;
import com.wishsales.fragments.SingleFragmentActivity;

import java.util.UUID;

public class GameActivity extends SingleFragmentActivity {
    public static final String EXTRA_GAME_ID = "com.wishsales.game_id";

    /**
     * Creates an intent with especified Game Id
     * @param context
     * @param gameId The id of the wanted game
     * @return New intent with the game id in extra
     */
    public static Intent newIntent(Context context, UUID gameId) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_GAME_ID, gameId);
        return intent;
    }

    /**
     * Creates a fragment for the especified game in the extras
     * @return New GameFragment for especified game
     */
    @Override
    protected Fragment createFragment() {
        UUID gameid = (UUID) getIntent().getSerializableExtra(EXTRA_GAME_ID);
        return GameFragment.newInstance(gameid);
    }
}
