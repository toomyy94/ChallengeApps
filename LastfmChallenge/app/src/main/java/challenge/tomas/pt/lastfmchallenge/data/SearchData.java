package challenge.tomas.pt.lastfmchallenge.data;


import android.graphics.Bitmap;

/**
 * Created by Tomás Rodrigues on 04/19/2018.
 */

public class SearchData {

    private int id;
    private static int nextId = 0;
    private String name;
    private String listener_artist;
    private String url;
    private Bitmap image;

    public SearchData(String name, String listener_artist, String url, Bitmap image) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.listener_artist = listener_artist;
        this.url = url;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListener_artist() {
        return listener_artist;
    }

    public void setListener_artist(String listener_artist) {
        this.listener_artist = listener_artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
