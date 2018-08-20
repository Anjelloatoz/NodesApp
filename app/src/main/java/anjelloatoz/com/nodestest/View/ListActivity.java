package anjelloatoz.com.nodestest.View;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import anjelloatoz.com.nodestest.Adapter.MovieAdapter;
import anjelloatoz.com.nodestest.Constants;
import anjelloatoz.com.nodestest.Model.SearchResult;
import anjelloatoz.com.nodestest.R;
import anjelloatoz.com.nodestest.ViewModel.MainViewModel;
import anjelloatoz.com.nodestest.ViewModel.MovieViewModel;
import anjelloatoz.com.nodestest.databinding.ActivityListBinding;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListActivity extends AppCompatActivity {
    private ActivityListBinding activityListBinding;
    private MainViewModel mainViewModel;
    private ArrayList<MovieViewModel> movieViewModelArrayList;
    private MovieAdapter movieAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        getSupportActionBar().setTitle("Search Results");

        activityListBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);

        mainViewModel = new MainViewModel();
        Observable<SearchResult> observable = mainViewModel.getSearchResults()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<SearchResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SearchResult value) {
                movieAdapter = new MovieAdapter(context, value.results);
                activityListBinding.list.setAdapter(movieAdapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        activityListBinding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, MovieListActivity.class);
                intent.putExtra(Constants.MOVIE_ID_KEY, Integer.toString((int)id));
                startActivity(intent);
            }
        });
    }
}
