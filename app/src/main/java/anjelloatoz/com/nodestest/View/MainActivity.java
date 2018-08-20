package anjelloatoz.com.nodestest.View;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import anjelloatoz.com.nodestest.Adapter.MovieAdapter;
import anjelloatoz.com.nodestest.Constants;
import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.Model.SearchResult;
import anjelloatoz.com.nodestest.Persistence.PersistenceManager;
import anjelloatoz.com.nodestest.R;
import anjelloatoz.com.nodestest.Remote.APIService;
import anjelloatoz.com.nodestest.Remote.RetroClass;
import anjelloatoz.com.nodestest.ViewModel.MainViewModel;
import anjelloatoz.com.nodestest.databinding.ActivityMainBinding;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private List<Movie> fav_list;
    MainViewModel model;
    private APIService apiService;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = new MainViewModel();
//        model.setSearchQuery("Terminator");
        activityMainBinding.setViewModel(model);
        apiService = RetroClass.getAPIService();

        activityMainBinding.searcher.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Observable<SearchResult> observable = apiService.getSearchResults(Constants.API_KEY, model.searchQuery)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
                observable.subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResult value) {
                        PersistenceManager.getInstance().storeSearchResults(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(getClass().getName(), "ERROR: "+e.getMessage()+e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fav_list = PersistenceManager.getInstance().getFavorites();
        movieAdapter = new MovieAdapter(MainActivity.this, fav_list);
        activityMainBinding.list.setAdapter(movieAdapter);

        activityMainBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                intent.putExtra(Constants.MOVIE_ID_KEY, Integer.toString((int)id));
                startActivity(intent);
            }
        });
    }
}
