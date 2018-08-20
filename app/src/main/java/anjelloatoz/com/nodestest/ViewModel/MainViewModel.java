package anjelloatoz.com.nodestest.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;

import anjelloatoz.com.nodestest.BR;
import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.Model.SearchResult;
import anjelloatoz.com.nodestest.Persistence.PersistenceManager;
import io.reactivex.Observable;

/**
 * Created by Anjelloatoz on 8/17/18.
 */

public class MainViewModel extends BaseObservable{
    public String empty = "Empty";
    public String search = "Search";
    public String searchQuery;
    public String caption;

    public MainViewModel() {
    }

    @Bindable
    public String getSearchQuery() {
        return searchQuery;
    }

    @Bindable
    public String getCaption() {
        return caption;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        if(this.searchQuery.length()<3){
            caption = empty;
        } else{
            caption = search;
        }
        notifyPropertyChanged(BR.searchQuery);
        notifyPropertyChanged(BR.caption);
    }

    public Observable<SearchResult> getSearchResults(){
        Observable<SearchResult> result = Observable.just(PersistenceManager.getInstance().getSearchResults());
        return result;
    }

    public Observable<ArrayList<Movie>> getFavorites(){
        Observable<ArrayList<Movie>> result = Observable.just(PersistenceManager.getInstance().getFavorites());
        return result;
    }
}
