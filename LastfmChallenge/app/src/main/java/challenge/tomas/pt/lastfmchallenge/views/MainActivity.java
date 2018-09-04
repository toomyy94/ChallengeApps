package challenge.tomas.pt.lastfmchallenge.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import challenge.tomas.pt.lastfmchallenge.LastfmApplication;
import challenge.tomas.pt.lastfmchallenge.R;
import challenge.tomas.pt.lastfmchallenge.data.SearchData;

/**
 * Main activity.
 * <p>
 * Created by Tomas on 03/09/2018.
 */
public class MainActivity extends FragmentActivity {

    public ArrayList<SearchData> searchData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fragmentMain = new MainFragment();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragmentMain);
        fragmentTransaction.commit();
    }

    public LastfmApplication getLastfmApplication(){
        return (LastfmApplication) getApplication();
    }

    public ArrayList<SearchData> getSearchData() {
        return searchData;
    }

    public void setSearchData(ArrayList<SearchData> searchData) {
        this.searchData = searchData;
    }

    public void clearSearchData() {
        searchData.clear();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
