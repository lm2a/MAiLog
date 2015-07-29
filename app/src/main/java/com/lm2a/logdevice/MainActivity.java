package com.lm2a.logdevice;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lm2a.mailoglibrary.LogSingleton;
import com.lm2a.mailoglibrary.MAiLog;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity implements View.OnClickListener {


    private TextView textView;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //-------------------------------------------------------------------
        LogSingleton logSingleton = LogSingleton.getInstance();
        logSingleton.init(this);
        //-------------------------------------------------------------------

        findViewById(R.id.send_log_button).setOnClickListener(this);
        textView = (TextView) findViewById(R.id.TextView01);

    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //-------------------------------------------------------------------
                    //Log and send log by email
                    MAiLog.e(e.getMessage());
                    //-------------------------------------------------------------------
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }

    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www1.vogella.com"});

    }






}
