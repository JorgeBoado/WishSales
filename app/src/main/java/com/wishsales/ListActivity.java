package com.wishsales;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wishsales.fragments.StoreFragment;

public class ListActivity extends AppCompatActivity {
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MenuPagerActivity.newIntent(ListActivity.this, StoreFragment.FRAGMENT_ID);
                startActivity(intent);
            }
        });

    }
}
