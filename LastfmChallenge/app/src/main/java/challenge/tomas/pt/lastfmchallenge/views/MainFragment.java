package challenge.tomas.pt.lastfmchallenge.views;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.common.base.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import challenge.tomas.pt.lastfmchallenge.R;
import challenge.tomas.pt.lastfmchallenge.data.SearchData;
import challenge.tomas.pt.lastfmchallenge.http.client.HttpClient;
import challenge.tomas.pt.lastfmchallenge.network.MobileNetworkManager;
import challenge.tomas.pt.lastfmchallenge.network.WifiNetworkManager;
import challenge.tomas.pt.lastfmchallenge.utils.LastfmUtils;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainFragment extends Fragment {

    private MobileNetworkManager mobileNetworkManager;
    private WifiNetworkManager wifiNetworkManager;

    private TextView checkInternet;
    private TextInputEditText artistSearch;
    private TextInputEditText albumSearch;
    private TextInputEditText songSearch;
    private ImageButton ImageButtonArtist;
    private ImageButton ImageButtonAlbum;
    private ImageButton ImageButtonSong;

    private String searchResult;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mobileNetworkManager = MobileNetworkManager.getInstance(((MainActivity) getActivity()).getLastfmApplication());
        wifiNetworkManager = WifiNetworkManager.getInstance(((MainActivity) getActivity()).getLastfmApplication());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initializeViews(view);
        onListeners();

        return view;
    }

    private void onListeners() {
        ImageButtonArtist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInternet.setText(R.string.loading);
                if(checkInternetConnectivity()){
                    Single.just("artist").subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() { //Assyctask has too many code :)
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            HttpClient client = new HttpClient();
                            searchResult = client.getRequest("http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=" + artistSearch.getText() + "&api_key=" + LastfmUtils.API_KEY + "&format=json");
                            Log.d("Artist request", searchResult);
                            viewListResults(LastfmUtils.SEARCH_OPTION_ARTIST);
                        }
                    });
                }
                else
                    checkInternet.setText(R.string.check_internet_connection);
            }
        });

        ImageButtonAlbum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInternet.setText(R.string.loading);
                if(checkInternetConnectivity()){
                    Single.just("album").subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() { //Assyctask has too many code :)
                            @Override
                            public void accept(@NonNull String s) throws Exception {
                                HttpClient client = new HttpClient();
                                searchResult = client.getRequest("http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + albumSearch.getText() + "&api_key=" + LastfmUtils.API_KEY + "&format=json");
                                Log.d("Album request", searchResult);
                                viewListResults(LastfmUtils.SEARCH_OPTION_ALBUM);
                            }
                        });
                }
                else
                    checkInternet.setText(R.string.check_internet_connection);
            }
        });

        ImageButtonSong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkInternet.setText(R.string.loading);
                if(checkInternetConnectivity()){
                    Single.just("song").subscribeOn(Schedulers.newThread()).subscribe(new Consumer<String>() { //Assyctask has too many code :)
                        @Override
                        public void accept(@NonNull String s) throws Exception {
                            HttpClient client = new HttpClient();
                            searchResult = client.getRequest("http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + songSearch.getText() + "&api_key=" + LastfmUtils.API_KEY + "&format=json");
                            Log.d("Song request", searchResult);
                            viewListResults(LastfmUtils.SEARCH_OPTION_SONG);
                        }
                    });
                }
                else
                    checkInternet.setText(R.string.check_internet_connection);
            }
        });
    }

    private void fillSearchData(int searchOption){
        ArrayList<SearchData> tmpSeachData = new ArrayList<>();
        try {

            JSONObject jsonSearchResult = new JSONObject(searchResult);

            switch (searchOption) {
                case LastfmUtils.SEARCH_OPTION_ARTIST:
                    JSONArray artists = jsonSearchResult.getJSONObject("results").getJSONObject("artistmatches").getJSONArray("artist");

                    for(int i = 0; i < artists.length(); i++){
                        JSONObject artist = artists.getJSONObject(i);
                        SearchData searchData = new SearchData(artist.getString("name"), artist.getString("listeners"),
                                artist.getString("url"), urlToBitmap(artist.getJSONArray("image").getJSONObject(2).getString("#text")));
                        tmpSeachData.add(searchData);
                    }

                    break;
                case LastfmUtils.SEARCH_OPTION_ALBUM:
                    JSONArray albuns = jsonSearchResult.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album");

                    for(int i = 0; i < albuns.length(); i++){
                        JSONObject artist = albuns.getJSONObject(i);
                        SearchData searchData = new SearchData(artist.getString("name"), artist.getString("artist"),
                                artist.getString("url"), urlToBitmap(artist.getJSONArray("image").getJSONObject(2).getString("#text")));
                        tmpSeachData.add(searchData);
                    }

                    break;
                case LastfmUtils.SEARCH_OPTION_SONG:
                    JSONArray tracks = jsonSearchResult.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");

                    for(int i = 0; i < tracks.length(); i++){
                        JSONObject artist = tracks.getJSONObject(i);
                        SearchData searchData = new SearchData(artist.getString("name"), artist.getString("artist"),
                                artist.getString("url"), urlToBitmap(artist.getJSONArray("image").getJSONObject(2).getString("#text")));
                        tmpSeachData.add(searchData);
                    }

                    break;
            }
        }catch (JSONException je){
            Log.e("Error", "Error on parsing json");
            je.printStackTrace();
        }

        ((MainActivity) getActivity()).setSearchData(tmpSeachData);
    }

    private static Bitmap urlToBitmap(String urlPath) {

        if(Strings.isNullOrEmpty(urlPath)){
            return BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.no_image_found);
        }

        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (MalformedURLException e) {
            return BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.no_image_found);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void viewListResults(final int searchOption) {
        fillSearchData(searchOption);

        ListFragment fragmentList = ListFragment.newInstance(searchOption);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragmentList).addToBackStack(LastfmUtils.SEARCH_OPTION);
        fragmentTransaction.commit();
    }

    private void initializeViews(View view) {
        checkInternet = view.findViewById(R.id.internetCheck);
        artistSearch  = view.findViewById(R.id.textInputLayoutArtist);
        albumSearch   = view.findViewById(R.id.textInputLayoutAlbum);
        songSearch    = view.findViewById(R.id.textInputLayoutSong);
        ImageButtonArtist  = view.findViewById(R.id.searchArtist);
        ImageButtonAlbum   = view.findViewById(R.id.searchAlbum);
        ImageButtonSong    = view.findViewById(R.id.searchSong);
    }

    private boolean checkInternetConnectivity( ) {
        if(mobileNetworkManager.isMobileDataAvailable() || wifiNetworkManager.hasConnectivity())
            return true;
        else
            return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onImageButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
