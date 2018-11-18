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

import com.wishsales.fragments.LibraryFragment;
import com.wishsales.fragments.StoreFragment;
import com.wishsales.fragments.WishListFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<String> mFragments;

    public static final String EXTRA_MENU_ID = "com.wishsales.menu_fragment_id";

    /**
     * Creates a new intent for the pager with an especified menu
     * @param context
     * @param menuId The id of the first menu shown
     * @return A new intent with the menu id in extra
     */
    public static Intent newIntent(Context context, String menuId) {
        Intent intent = new Intent(context, MenuPagerActivity.class);
        intent.putExtra(EXTRA_MENU_ID, menuId);
        return intent;
    }

    /**
     * Creates the Activity of the Menu Pager.
     * There are 3 menus saved: Wishlist, Store and Library.
     * The first menu shown by default is Store
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pager);

        String menuId = (String) getIntent().getStringExtra(EXTRA_MENU_ID);
        if (menuId == null) {
            menuId = StoreFragment.FRAGMENT_ID;
        }

        mViewPager = (ViewPager) findViewById(R.id.menu_view_pager);
        mFragments = new ArrayList<>();
        mFragments.add(WishListFragment.FRAGMENT_ID);
        mFragments.add(StoreFragment.FRAGMENT_ID);
        mFragments.add(LibraryFragment.FRAGMENT_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                switch (mFragments.get(i)) {
                    case StoreFragment.FRAGMENT_ID:
                        return StoreFragment.newInstance();
                    case LibraryFragment.FRAGMENT_ID:
                        return LibraryFragment.newInstance();
                    case WishListFragment.FRAGMENT_ID:
                        return WishListFragment.newInstance();
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
