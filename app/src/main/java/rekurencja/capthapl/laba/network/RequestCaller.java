package rekurencja.capthapl.laba.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.Callable;

public class RequestCaller extends AsyncTask<String,Void,String> {

    @Override
    protected String doInBackground(String... strings) {
        try {
            StringBuilder result = new StringBuilder();
            URL url =  new URL(strings[0]);
            URLConnection conn =  url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) result.append(line);
            rd.close();
            Log.d("XD",result.toString());
            return result.toString();
        }catch (Exception ex){Log.d("Exception",ex.getClass().getName());}
            return null;
    }

}



