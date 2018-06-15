package rekurencja.capthapl.laba.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class RequestCallerPOSTVote extends AsyncTask<String,Void,String> {
    private static HttpURLConnection con;
    @Override
    protected String doInBackground(String... strings) {
        String url = "https://httpbin.org/post";
        String urlParameters = "name=Jack&occupation=programmer";
        String postData = "{   \"id\":"+strings[0]+",   \"vote\":"+strings[1]+",   \"device_id\":\""+strings[2]+"\", }\n";

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("x-api-token", "7fbczyjw7jOSzAUw9vj04lgYVWyjpY5w");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            Log.d("Tag",content.toString());

        }catch (Exception ex){}finally {

            con.disconnect();
        }
        return "";
    }

}



