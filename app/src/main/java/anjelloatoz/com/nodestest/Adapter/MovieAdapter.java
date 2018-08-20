package anjelloatoz.com.nodestest.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.R;
import anjelloatoz.com.nodestest.ViewModel.MovieViewModel;
import anjelloatoz.com.nodestest.databinding.MovieitemBinding;

/**
 * Created by Anjelloatoz on 8/17/18.
 */

public class MovieAdapter extends BaseAdapter {
    private List<Movie> movie_list;
    private Context context;
    private LayoutInflater layoutInflater;
    private MovieitemBinding movieitemBinding;

    public MovieAdapter(Context context, List<Movie> movie_list){
        this.context = context;
        this.movie_list = movie_list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movie_list.size();
    }

    @Override
    public Object getItem(int position) {
        return movie_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movie_list.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.movieitem, parent, false);
            movieitemBinding = DataBindingUtil.bind(convertView);
            convertView.setTag(movieitemBinding);
        } else{
            movieitemBinding = (MovieitemBinding)convertView.getTag();
        }

        movieitemBinding.setMovie(new MovieViewModel(movie_list.get(position)));
        return movieitemBinding.getRoot();
    }
}
