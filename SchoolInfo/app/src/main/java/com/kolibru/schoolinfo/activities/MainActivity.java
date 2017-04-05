package com.kolibru.schoolinfo.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.kolibru.schoolinfo.ConstClass;
import com.kolibru.schoolinfo.R;
import com.kolibru.schoolinfo.adapters.ViewTabPagerAdapter;
import com.kolibru.schoolinfo.databinding.ActivityMainBinding;
import com.kolibru.schoolinfo.fragments.MenuProgressFragment;
import com.kolibru.schoolinfo.fragments.MenuScheduleFragment;
import com.kolibru.schoolinfo.fragments.MenuVisitFragment;
import com.kolibru.schoolinfo.fragments.SettingsFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    ActivityMainBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        initUI();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    private void initUI() {
        setupViewTabPager(mBinding.vpHorizontalNtb);
        mBinding.vpHorizontalNtb.setOffscreenPageLimit(4);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        NavigationTabBar.Model modelProfile=new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.book),
                ConstClass.getColor(this,R.color.colorPrimary))
                .title(getString(R.string.tab_progress))
                .build();
        models.add(modelProfile);
        NavigationTabBar.Model modelRoute=new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.university),
                ConstClass.getColor(this,R.color.colorPrimary))
                .title(getString(R.string.tab_visit))
                .build();
        models.add(modelRoute);
        NavigationTabBar.Model modelQuests=new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.bell),
                ConstClass.getColor(this,R.color.colorPrimary))
                .title(getString(R.string.tab_schedule))
                .build();
        models.add(modelQuests);
        NavigationTabBar.Model modeSetting=new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.settings),
                ConstClass.getColor(this,R.color.colorPrimary))
                .title(getString(R.string.tab_settings))
                .build();
        models.add(modeSetting);
        mBinding.ntbHorizontal.setModels(models);
        mBinding.ntbHorizontal.setViewPager(mBinding.vpHorizontalNtb, 0);
    }


    private void setupViewTabPager(final ViewPager viewPager) {
        ViewTabPagerAdapter adapter = new ViewTabPagerAdapter(getSupportFragmentManager());

        Bundle b = new Bundle();
        MenuProgressFragment oExamFragment = new MenuProgressFragment();
        oExamFragment.setArguments(b);

        MenuScheduleFragment oScheduleFragment = new MenuScheduleFragment();
        oScheduleFragment.setArguments(b);

        SettingsFragment oSettingsFragment = new SettingsFragment();
        oSettingsFragment.setArguments(b);

        MenuVisitFragment oVisitFragment = new MenuVisitFragment();
        oVisitFragment.setArguments(b);
        adapter.addFrag(oExamFragment, getResources().getString(R.string.tab_progress));
        adapter.addFrag(oVisitFragment, getResources().getString(R.string.tab_visit));
        adapter.addFrag(oScheduleFragment, getResources().getString(R.string.tab_schedule));
        adapter.addFrag(oSettingsFragment, getResources().getString(R.string.tab_settings));

        viewPager.setAdapter(adapter);
        if(webFunctionService.getTOKEN()!=null) {
            viewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(0);
                    }
                }, 100);
            }
        else{
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setCurrentItem(3);
                }
            }, 100);
        }
    }
}
