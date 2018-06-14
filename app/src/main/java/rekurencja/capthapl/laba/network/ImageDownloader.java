package rekurencja.capthapl.laba.network;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


 public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            params[0] = SpaceEncone(params[0]);
            URL url = new URL(params[0]);
            Log.d("hehurl",params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }catch (Exception e){
            Log.d("TAG",e.getMessage());
        }
        return null;
    }

     private String SpaceEncone(String val){
             val = val.replace(" ", "%20");
         return val;
     }
}