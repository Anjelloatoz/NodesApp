package anjelloatoz.com.nodestest.Persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.Model.SearchResult;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Anjelloatoz on 8/11/18.
 */

public class PersistenceManager {

    private static PersistenceManager INSTANCE;
    private Context context;
    Gson gson = new GsonBuilder().serializeNulls().create();

    private static String SEARCH_RESULTS_KEY = "SEARCH_RESULTS";
    private static String FAVORITES_KEY = "FAVORITES";

    public static PersistenceManager getInstance(){
        return INSTANCE;
    }

    public static void initInstance(Context context){
        INSTANCE = new PersistenceManager();
        INSTANCE.context = context;
    }

    public void storeSearchResults(SearchResult searchResult){
        SharedPreferences sharedPref = this.context.getSharedPreferences(SEARCH_RESULTS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SEARCH_RESULTS_KEY, gson.toJson(searchResult));
        editor.commit();
    }

    public SearchResult getSearchResults(){
        SharedPreferences sharedPrefs = this.context.getSharedPreferences(SEARCH_RESULTS_KEY, MODE_PRIVATE);
        return gson.fromJson(sharedPrefs.getString(SEARCH_RESULTS_KEY, ""), new TypeToken<SearchResult>() {}.getType());
    }

    public ArrayList<Movie> getFavorites(){
        SharedPreferences sharedPrefs = this.context.getSharedPreferences(FAVORITES_KEY, MODE_PRIVATE);
        ArrayList<Movie> fav_list = gson.fromJson(sharedPrefs.getString(FAVORITES_KEY, ""), new TypeToken<ArrayList<Movie>>() {}.getType());
        if(fav_list == null){
            fav_list = new ArrayList<Movie>();
        }

        return fav_list;
    }

    public void addToFavorites(Movie movie){
        SharedPreferences sharedPrefs = this.context.getSharedPreferences(FAVORITES_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Movie> fav_movies = gson.fromJson(sharedPrefs.getString(FAVORITES_KEY, ""), new TypeToken<ArrayList<Movie>>() {}.getType());
        if(fav_movies == null){
            ArrayList<Movie> mov_list = new ArrayList<>();
            editor.putString(FAVORITES_KEY, gson.toJson(mov_list));
            editor.commit();
        } else{
            if(isFavorite(movie)){//Movie already in the favorites. Returning.
                return;
            }

            fav_movies.add(movie);
            editor.putString(FAVORITES_KEY, gson.toJson(fav_movies));
            editor.commit();
        }
    }

    public void removeFromFavorites(Movie movie){
        SharedPreferences sharedPrefs = this.context.getSharedPreferences(FAVORITES_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        ArrayList<Movie> fav_movies = gson.fromJson(sharedPrefs.getString(FAVORITES_KEY, ""), new TypeToken<ArrayList<Movie>>() {}.getType());
        if(fav_movies != null && !fav_movies.isEmpty()){
            for(int i = 0; i < fav_movies.size(); i++){
                if(fav_movies.get(i).id == movie.id){
                    fav_movies.remove(i);
                    editor.putString(FAVORITES_KEY, gson.toJson(fav_movies));
                    editor.commit();
                    return;
                }
            }
        }
    }

    public boolean isFavorite(Movie movie){
        SharedPreferences sharedPrefs = this.context.getSharedPreferences(FAVORITES_KEY, MODE_PRIVATE);
        ArrayList<Movie> fav_movies = gson.fromJson(sharedPrefs.getString(FAVORITES_KEY, ""), new TypeToken<ArrayList<Movie>>() {}.getType());
        if(fav_movies == null || fav_movies.isEmpty()){
            return false;
        } else{
            for(int i = 0; i < fav_movies.size(); i++){
                if(fav_movies.get(i).id == movie.id){
                    System.out.println("Already there");
                    return true;
                }
            }
            return false;
        }
    }
}


