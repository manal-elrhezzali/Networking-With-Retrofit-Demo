package me.elrhezzalimanal.networkingwithretrofitdemo;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PostDataService {
    @GET("posts")
    Call<List<Post>> getPosts();

    //    You can pass multiple path params idem you can pass multiple Query params
    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int id);// the parameter will be recognized in the webservice by the string passed to the annotation Path

    //    You can pass multiple path params idem you can pass multiple Query params
    @GET("posts")
    Call<List<Post>> getPostsByUserId(@Query("userId") int id);//retrofit will automatically make the url like this: BASE_URL/posts?userId=id

    @GET("posts")
    Call<Post> getPostByUserIdAndId(@Query("userId") int userId, @Query("id") int id);//if you don't want to pass a parameter just pass null (Rmrq: use the wrapper class Integer instead of int inorder to be able to pass null) ,and then retrofit will ignore it

    //    let's say we want to get posts by multiple userIds
    @GET("posts")
    Call<List<Post>> getPostsByMultipleUserIds(@Query("userId") int[] id);
    //or pass a list of integers or you can use varargs
//    Call<List<Post>> getPostsByMultipleUserIds(@Query("userId") int... id);

    //let's say we don't want to define the Queries until the we call this method
    //this can take a key only one time so we can't pass for example multiple userId s
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);//the first String is the key the name of the parameter for exp userId the second is the value



}
