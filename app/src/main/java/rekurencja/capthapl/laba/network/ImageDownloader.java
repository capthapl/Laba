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
                val = val.toLowerCase();
                val = val.replace(" ", "%20");
                val = val.replace("ę", "e");
                val = val.replace("ó", "o");
                val = val.replace("ą", "a");
                val = val.replace("ś", "s");
                val = val.replace("ł", "l");
                val = val.replace("ż", "z");
                val = val.replace("ź", "z");
                val = val.replace("ć", "c");
                val = val.replace("ń", "n");
                if(val.contains("maps.googleapis")){
                    val+="&keyAIzaSyBLlGPnBG9IyoSc4La4RwOXU7gZIOPDp8E";
                }
         return val;
     }
}