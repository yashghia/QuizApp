/*Homework 4
GetImageTask
Yash Ghia & Prabhakar teja Seeda*/

package com.example.teja.homework4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by teja on 9/27/17.
 */

public class GetImageTask extends AsyncTask<String,Integer,Bitmap> {
    TriviaActivity activity;
    InputStream inputStream = null;
    ProgressBar pb;
    ImageView img;

    public GetImageTask(TriviaActivity activity){
        this.activity = activity;
        pb = (ProgressBar) activity.findViewById(R.id.imageProgressBar);
        img = (ImageView) activity.findViewById(R.id.imageViewQuestion);
        img.setVisibility(View.INVISIBLE);
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream!=null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pb.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(Bitmap b) {
        super.onPostExecute(b);
        pb.setVisibility(ProgressBar.INVISIBLE);
        img.setVisibility(ImageView.VISIBLE);
        Log.d("bitmap"," "+b.toString());
        if(b!=null){
            img.setImageBitmap(b);
        }
        else
        {
            img.setImageResource(R.drawable.no_image);
            Log.d("bitmap","no image");
        }
    }
}
