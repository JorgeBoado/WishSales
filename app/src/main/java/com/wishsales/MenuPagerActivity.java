package com.wishsales;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MenuPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<String> mFragments;

    public static final String EXTRA_MENU_ID = "com.wishsales.menu_fragment_id";

    public static Intent newIntent(Context context, String menuId) {
        Intent intent = new Intent(context, MenuPagerActivity.class);
        intent.putExtra(EXTRA_MENU_ID, menuId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pager);

        String menuId = (String) getIntent().getStringExtra(EXTRA_MENU_ID);

        mViewPager = (ViewPager) findViewById(R.id.menu_view_pager);
        mFragments.add(StoreFragment.FRAGMENT_ID); //TODO quitar comentarios
        //mFragments.add(LibraryFragment.FRAGMENT_ID);
        //mFragments.add(WishListFragment.FRAGMENT_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                switch (mFragments.get(i)) {
                    case StoreFragment.FRAGMENT_ID:
                        return StoreFragment.newInstance();/* TODO quitar comentario
                    case LibraryFragment.FRAGMENT_ID:
                        return LibraryFragment.newInstance();
                    case WishListFragment.FRAGMENT_ID:
                        return WishListFragment.newInstance();*/
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        for (int i = 0; i < mFragments.size(); i++) {
            if(mFragments.get(i).equals(menuId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
