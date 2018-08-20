package anjelloatoz.com.nodestest.Remote;

import anjelloatoz.com.nodestest.Model.Movie;
import anjelloatoz.com.nodestest.Model.SearchResult;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anjelloatoz on 8/17/18.
 */

public interface APIService {
    @GET("search/movie")
    Observable<SearchResult> getSearchResults(@Query("api_key") String api_key, @Query("query") String search_query);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetails(@Path("movie_id") String movie_id, @Query("api_key") String api_key);
}
