package com.tp.instagram1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotoStreamActivity extends ActionBarActivity {
    public static final String CLIENT_ID="e1e2496faa6c40c78b27df531327c534";
    private ArrayList<InstaGramPhoto> photos;
    private InstaGramPhotoAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stream);
        fetchPopularPhotos();
        getSupportActionBar().hide();
    }

    private void fetchPopularPhotos() {
        photos = new ArrayList<InstaGramPhoto>();
        aPhotos = new InstaGramPhotoAdapter(this,photos);
        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotoStream);
        lvPhotos.setAdapter(aPhotos);
        String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(popularUrl,new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONArray photosJSONArray = null;
                try {
                    photos.clear();
                    photosJSONArray = response.getJSONArray("data");
                    for (int i = 0; i < photosJSONArray.length(); i++)
                    {
                        JSONObject photoJSON = photosJSONArray.getJSONObject(i);
                        InstaGramPhoto photo= new InstaGramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        if(photoJSON.getJSONObject("caption") != null)
                            photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.image_url = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
