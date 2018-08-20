package anjelloatoz.com.nodestest.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import anjelloatoz.com.nodestest.BR;
import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.Persistence.PersistenceManager;

/**
 * Created by Anjelloatoz on 8/17/18.
 */

public class MovieViewModel extends BaseObservable {
    int id;
    public String title, overview, release_date, posterpath;
    private Movie movie;
    public boolean isFavorit = false;
    private String fav_no_string = "Add to my favorites";
    private String fav_yes_string = "Remove from my favorites";
    public String fav_button_caption;


    public MovieViewModel(int id, String title, String overview, String release_date, String posterpath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.posterpath = posterpath;
    }

    public MovieViewModel(Movie movie){
        this.id = movie.id;
        this.title = movie.title;
        this.overview = movie.overview;
        this.release_date = movie.release_date;
        this.posterpath = movie.poster_path;
        this.movie = movie;
        this.isFavorit = PersistenceManager.getInstance().isFavorite(movie);
        if(isFavorit){
            fav_button_caption = fav_yes_string;
        } else{
            fav_button_caption = fav_no_string;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getOverview() {
        return overview;
    }

    @Bindable
    public String getFav_button_caption(){
        return fav_button_caption;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    @BindingAdapter(("bind:imageUrl"))
    public static void loadImage(ImageView imageView, String imageUrl){
        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

    public String getImageUrl(){
        return "http://image.tmdb.org/t/p/w185/"+this.posterpath;
    }

    private void updateFavState(){
        this.isFavorit = PersistenceManager.getInstance().isFavorite(movie);
        if(isFavorit){
            fav_button_caption = fav_yes_string;
        } else{
            fav_button_caption = fav_no_string;
        }
        notifyPropertyChanged(BR.fav_button_caption);
    }

    public void addToFavorites(){ //This should be called addRemove favorites.
        if(isFavorit)
            PersistenceManager.getInstance().removeFromFavorites(movie);
        else
            PersistenceManager.getInstance().addToFavorites(movie);
        updateFavState();
    }
}
