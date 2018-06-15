package rekurencja.capthapl.laba;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import rekurencja.capthapl.laba.Entities.Event;
import rekurencja.capthapl.laba.network.ImageDownloader;

public class MapDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_dialog);
        setupMap(EventActivity.CurrentEvent);
    }

    private void setupMap(Event event){
        ImageView map = findViewById(R.id.map_dialog_image);
        if(event.Location.length()>4){
            String rootUrl = "https://maps.googleapis.com/maps/api/staticmap?center="+ event.Location +
                    "&zoom=17&size=500x500&style=element:geometry|hue:0xa2c400&markers="+event.Location
                    ;
            ImageDownloader downloader = new ImageDownloader();
            try {

                Bitmap bt = downloader.execute(rootUrl).get();
                BitmapDrawable mapImage = new BitmapDrawable(getResources(),bt);
                map.setImageDrawable(mapImage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }else finish();
    }

}
