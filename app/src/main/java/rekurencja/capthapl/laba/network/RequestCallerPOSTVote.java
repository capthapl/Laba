package rekurencja.capthapl.laba.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RequestCallerPOSTVote extends AsyncTask<String,Void,Integer> {
    private static HttpURLConnection con;

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            String url_ = "https://tumskanova.pl/event/vote";
            URL url = new URL(url_);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("x-api-token", "7fbczyjw7jOSzAUw9vj04lgYVWyjpY5w");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", Integer.parseInt(strings[0]));
            jsonParam.put("vote", Integer.parseInt(strings[1]));
            jsonParam.put("device_id",strings[2]);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
           // os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());
            conn.disconnect();
            return conn.getResponseCode();


        } catch (Exception e) {
            e.printStackTrace();

        }
        return -1;
    }


}



