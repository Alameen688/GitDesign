package com.example.al_ameen.gitdesign;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String URL_DATA ="https://api.github.com/search/users?q=location:lagos+language:java";
    //https://api.github.com/search/users?q=location:lagos+language:java&per_page=100
    ArrayList<Profile> profiles;
    ProfileAdapter profileAdapter;
    TextView noInternet;
    Button mRetry;
    View loadingIndicator;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.list);

        noInternet = (TextView) findViewById(R.id.empty_view);
        mRetry = (Button) findViewById(R.id.retry);
        loadingIndicator = findViewById(R.id.loading_indicator);
        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doWork();

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile profile = profileAdapter.getItem(position);
                String avatar = profile.getMProfileResourceId();
                String fullname = profile.getMFullName();
                String username = profile.getMUserName();
                String bio = profile.getMBio();
                String totalRepo = profile.getMTotalRepo();
                String followers = profile.getMFollowers();
                String following = profile.getMFollowing();
                String totalGist = profile.getMTotalGist();
                String profileUrl = profile.getMProfileUrl();

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);

                intent.putExtra("iAvatar",avatar);
                intent.putExtra("iFullname",fullname);
                intent.putExtra("iUsername",username);
                intent.putExtra("iBio",bio);
                intent.putExtra("iTotalRepo",totalRepo);
                intent.putExtra("iFollowers",followers);
                intent.putExtra("iFollowing",following);
                intent.putExtra("iTotalGists",totalGist);
                intent.putExtra("iProfileUrl",profileUrl);

                startActivity(intent);
            }
        });

        profiles = new ArrayList<>();
        doWork();
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void doWork(){

        if (isNetworkAvailable()) {
            loadListViewData();
            profileAdapter = new ProfileAdapter(this, profiles);
            listview.setAdapter(profileAdapter);
        }else {
            noInternet.setText(R.string.no_internet_connection);
            noInternet.setVisibility(View.VISIBLE);
            mRetry.setVisibility(View.VISIBLE);
        }
    }

    public void loadListViewData(){
        //clear the
        profiles.clear();
        noInternet.setVisibility(View.GONE);
        mRetry.setVisibility(View.GONE);
        loadingIndicator.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String userUrlData;
                    JSONArray jsonArray = response.getJSONArray("items");
                    //default returned per page is actually 30 so no need of limit

                    for (int i = 0; i < jsonArray.length() ; i++) {
                        JSONObject user = jsonArray.getJSONObject(i);
                        userUrlData = user.getString("url");
                        Log.d("User data", userUrlData);

                        loadUserInfo(userUrlData);
                    }

                    loadingIndicator.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*
                loadingIndicator.setVisibility(View.GONE);
                noInternet.setText(R.string.no_profile);
                noInternet.setVisibility(View.VISIBLE);
                mRetry.setVisibility(View.VISIBLE);
                */
            }});

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void loadUserInfo(String userUrl)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, userUrl,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String userBio =response.getString("bio");
                    if(userBio=="null") {
                        userBio = "no bio";
                    }

                    profileAdapter.add(new Profile(
                            response.getString("avatar_url"),response.getString("name"),
                            "@"+response.getString("login"), userBio,
                            response.getString("public_repos")+" repo",response.getString("followers"),response.getString("following"),response.getString("public_gists")+" gist(s)",response.getString("html_url")));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loadingIndicator.setVisibility(View.GONE);
                noInternet.setText(R.string.no_profile);
                noInternet.setVisibility(View.VISIBLE);
                mRetry.setVisibility(View.VISIBLE);

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
