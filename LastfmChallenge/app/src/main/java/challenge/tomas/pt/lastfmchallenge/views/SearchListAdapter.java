package challenge.tomas.pt.lastfmchallenge.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import challenge.tomas.pt.lastfmchallenge.R;
import challenge.tomas.pt.lastfmchallenge.data.SearchData;

/**
 * Created by Tomás Rodrigues on 04/09/2018.
 */

public class SearchListAdapter extends ArrayAdapter<SearchData> implements View.OnClickListener{

    private ArrayList<SearchData> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtListeners_Artists;
        ImageView imgUrl;
    }

    public SearchListAdapter(ArrayList<SearchData> data, Context context) {
        super(context, R.layout.search_list, data);
        this.dataSet = data;
        this.mContext=context;
    }

    public void refreshEvents(ArrayList<SearchData> events) {
        this.dataSet.clear();
        this.dataSet.addAll(events);
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        final SearchData searchData = (SearchData) object;

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchData.getUrl()));
//        getContext().startActivity(browserIntent);
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchData SearchData = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.search_list, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.item_name);
            viewHolder.txtListeners_Artists = convertView.findViewById(R.id.item_listeners);
            viewHolder.imgUrl = convertView.findViewById(R.id.item_image);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        lastPosition = position;

        viewHolder.txtName.setText(SearchData.getName());
        viewHolder.txtListeners_Artists.setText(SearchData.getListener_artist());
        viewHolder.imgUrl.setImageBitmap(SearchData.getImage());

        // Return the completed view to render on screen
        return convertView;
    }

}
