package me.elrhezzalimanal.networkingwithretrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static PostDataService postDataService;
    private static CommentDataService commentDataService;

    private TextView textView;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        postDataService = RetrofitInstance.getRetrofitInstance().create(PostDataService.class);

        commentDataService = RetrofitInstance.getRetrofitInstance().create(CommentDataService.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getAllPosts();
//                getPostById();
//                getPostsByUserId();
//                getAllComments();
//                getCommentsOfPost();
                getPosts();

//                getComments();
//                createPost();

//                createPostSendItAsFormUrlEncoded();
//                createPostSendItAsFormUrlEncodedWithFieldMap();
//                deletePostById();


            }
        });
    }

    private void deletePostById() {
        int id = Integer.valueOf(String.valueOf(editText.getText()));
        Call<Void> call = postDataService.deletePostById(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code :" + response.code());
                    return;
                }
                textView.setText("post deleted successfully, the response code is  " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void createPostSendItAsFormUrlEncodedWithFieldMap() {
        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "123");
        fields.put("title", "I am the title");
        fields.put("body","AND AAAAAAAA IAAAAAAAAAY WILL ALWAAAAYS  LOOOOVE UUUUU AAAAAAAAY");//if we don't send a title for exp its value will be set to null
        Call<Post> call = postDataService.createPostSendItAsFormUrlEncodedWithFieldMap(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code :" + response.code());
                    return;
                }
                textView.setText(response.body().getTitle());

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void createPostSendItAsFormUrlEncoded() {
        Call<Post> call = postDataService.createPostSendItAsFormUrlEncoded(89,"MANAL CREATED ME", "TEXT WRITTEN BY MANAL");//this will be passed as  userId=89&title=MANAL%20CREATED%20ME&body=TEXT%20WRITTEN%20BY%20MANAL
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    editText.setText(" Response Code " + response.code());
                    return;
                }
                textView.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                editText.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(102,
                "A NEW POST BY Manal",
                "This is the body of the new Post "
        );
        Map <String,String> headersMap = new HashMap<>();
        headersMap.put("Map-Header1", "I am a header");
        headersMap.put("Map-Header2", "It's hard to come up with names");

        Call<Post> call = postDataService.createPost(headersMap, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    editText.setText(" Response Code " + response.code());
                    return;
                }
                textView.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                editText.setText(t.getMessage());
            }
        });
    }

    private void getComments() {
        Call<List<Comment>> call = commentDataService.getComments("posts/3/comments");
        // you can pass the whole url and it will override the one you passed to retrofit the "BASE_URL"
//        Call<List<Comment>> call = commentDataService.getComments("https://jsonplaceholder.typicode.com/posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                ArrayList<Comment> comments = (ArrayList<Comment>) response.body();
                for (Comment comment : comments) {
                    textView.setText(textView.getText() + "\n\t" + comment.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
        String userId = String.valueOf(editText.getText());
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("id", "3");
        Call<List<Post>> call = postDataService.getPosts(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code :" + response.code());
                    return;
                }
                ArrayList<Post> posts = (ArrayList<Post>) response.body();
                for (Post p : posts) {
                    textView.setText(textView.getText() + "\n\n" + p.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });

    }

    private void getCommentsOfPost() {
        int id = Integer.valueOf(String.valueOf(editText.getText()));
        Call<List<Comment>> call = commentDataService.getCommentsOfPost(id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code :" + response.code());
                    return;
                }
                ArrayList<Comment> comments = (ArrayList<Comment>) response.body();
                for (Comment comment : comments) {
                    textView.setText(textView.getText() + "\n\n" + comment.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getAllComments() {
        Call<List<Comment>> call = commentDataService.getAllComments();
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                ArrayList<Comment> comments = (ArrayList<Comment>) response.body();
                for (Comment comment : comments) {
                    textView.setText(textView.getText() + "\n\t" + comment.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getPostsByUserId() {
        int id = Integer.valueOf(String.valueOf(editText.getText()));
//        PostDataService postDataService = RetrofitInstance.getRetrofitInstance().create(PostDataService.class);
        Call<List<Post>> call = postDataService.getPostsByUserId(id);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                ArrayList<Post> posts = (ArrayList<Post>) response.body();
                for (Post p : posts) {
                    textView.setText(textView.getText() + "\n\t" + p.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getAllPosts() {
//        PostDataService postDataService = RetrofitInstance.getRetrofitInstance().create(PostDataService.class);
        Call<List<Post>> call = postDataService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                ArrayList<Post> posts = (ArrayList<Post>) response.body();
                for (Post p : posts) {
                    textView.setText(textView.getText() + "\n\t" + p.getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    private void getPostById() {
        int id = Integer.valueOf(String.valueOf(editText.getText()));
//        PostDataService postDataService = RetrofitInstance.getRetrofitInstance().create(PostDataService.class);
        Call<Post> call = postDataService.getPostById(id);
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code: " + response.code());
                    return;
                }
                textView.setText(response.body().getTitle());

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}