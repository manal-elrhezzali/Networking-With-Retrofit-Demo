package me.elrhezzalimanal.networkingwithretrofitdemo;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface CommentDataService {

    @GET("comments")
    Call<List<Comment>> getAllComments();

    @GET("posts/{postId}/comments")
    Call<List<Comment>> getCommentsOfPost(@Path("postId") int id);

//    You can pass multiple path params idem you can pass multiple Query params
//    @GET("posts/{postId}/{someOtherReplacementBloc}")
//    Call<List<Comment>> getCommentsOfPost(@Path("postId") int id, @Path("omeOtherReplacementBloc") int someParameter);

    //if you want to pass the URL dynamically
    @GET
    Call<List<Comment>> getComments(@Url String url);
}
