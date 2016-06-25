package adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import beans.NewsKind;
import ui.fragment.MyFragment;


public class ContentAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ContentAdapter";
    private NewsKind[] newsKinds = new NewsKind[5];
    public ContentAdapter(FragmentManager fm,NewsKind[] nk) {
        super(fm);
        this.newsKinds = nk;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i(TAG, "getPageTitle() returned: position=" + position);
        MyFragment mf = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",newsKinds[position].id);
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public int getCount() {
        return newsKinds.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i(TAG, "getPageTitle() returned: name" + newsKinds[position].name);
        return newsKinds[position].name;
    }
}
