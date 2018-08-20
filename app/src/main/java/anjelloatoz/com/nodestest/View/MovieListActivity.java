package anjelloatoz.com.nodestest.View;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import anjelloatoz.com.nodestest.Constants;
import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.R;
import anjelloatoz.com.nodestest.Remote.APIService;
import anjelloatoz.com.nodestest.Remote.RetroClass;
import anjelloatoz.com.nodestest.ViewModel.MovieViewModel;
import anjelloatoz.com.nodestest.databinding.ActivityMovieListBinding;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMovieListBinding activityMovieListBinding;
    private String movie_id;
    private MovieViewModel movieViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_movie_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movie_id = extras.getString(Constants.MOVIE_ID_KEY);
        }
        activityMovieListBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);


        APIService apiService = RetroClass.getAPIService();
        Observable<Movie> observable = apiService.getMovieDetails(movie_id, Constants.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<Movie>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Movie value) {
                Log.e(getClass().getName(), value.title);
                movieViewModel = new MovieViewModel(value);
                activityMovieListBinding.setMovie(movieViewModel);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(getClass().getName(), "ERROR: "+e.getMessage()+e.toString());
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
