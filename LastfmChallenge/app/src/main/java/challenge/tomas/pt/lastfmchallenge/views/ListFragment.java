package challenge.tomas.pt.lastfmchallenge.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import challenge.tomas.pt.lastfmchallenge.R;
import challenge.tomas.pt.lastfmchallenge.utils.LastfmUtils;

import static challenge.tomas.pt.lastfmchallenge.utils.LastfmUtils.SEARCH_OPTION;

public class ListFragment extends Fragment {

    private int searchOption;
    private OnFragmentInteractionListener mListener;

    //Header
    private TextView header2;

    //Floating button
    FloatingActionButton fab;

    //ListView
    private TextView txtName;
    private TextView txtListeners_artists;
    private ImageView urlImg;
    private SearchListAdapter adapter;
    private ListView listView;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(int searchOption) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(SEARCH_OPTION, searchOption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchOption = getArguments().getInt(SEARCH_OPTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        initializeViews(view);
        initializeListeners();

        return view;
    }

    private void initializeViews(View view) {
        header2 = view.findViewById(R.id.header2);
        if(searchOption == LastfmUtils.SEARCH_OPTION_ARTIST)
            header2.setText("Listeners");

        fab = view.findViewById(R.id.fab);

        //ListView
        txtName = view.findViewById(R.id.item_name);
        txtListeners_artists = view.findViewById(R.id.item_listeners);
        urlImg = view.findViewById(R.id.item_image);
        listView = view.findViewById(R.id.list_view);

        //List
        adapter = new SearchListAdapter(((MainActivity) getActivity()).getSearchData(), ((MainActivity) getActivity()).getLastfmApplication().getApplicationContext());
        listView.setAdapter(adapter);
    }

    private void initializeListeners() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((MainActivity) getActivity()).getSearchData().get(position).getUrl()));
                getContext().startActivity(browserIntent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment mainFragment = new MainFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, mainFragment);
                fragmentTransaction.commit();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
