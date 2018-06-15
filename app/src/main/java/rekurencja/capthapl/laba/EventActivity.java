package rekurencja.capthapl.laba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import rekurencja.capthapl.laba.Entities.Event;
import rekurencja.capthapl.laba.LoadedEvents.LoadedEvents;
import rekurencja.capthapl.laba.network.ImageDownloader;
import rekurencja.capthapl.laba.network.RequestCaller;
import rekurencja.capthapl.laba.network.RequestCallerPOSTVote;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EventActivity extends Activity {

    public static int EventId;
    TextView Title;
    TextView Description;
    ImageButton LinkButton;
    ImageButton NotificaitonButton;
    ImageButton SmsButton;
    ImageButton Map;
    ImageButton LikeButton;
    ImageButton DislikeButton;
    CircleImageView Logo;
    TextView Rating;
    static Event CurrentEvent;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/monte.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_event);
        SetupTextViews();
        BindText();
    }

    private void SetupTextViews(){
        Title = findViewById(R.id.extended_event_title);
        Description = findViewById(R.id.extended_event_description);
        LinkButton = findViewById(R.id.link_button);
        NotificaitonButton = findViewById(R.id.notification_button);
        Logo = findViewById(R.id.event_logo);
        Map = findViewById(R.id.map_button);
        SmsButton = findViewById(R.id.sms_button);
        Rating = findViewById(R.id.rating_textview);
        LikeButton = findViewById(R.id.like_button);
        DislikeButton = findViewById(R.id.dislike_button);
    }


    private void setupMapButton(final Event event){
        if(event.Location.length()>4){
            final Activity thisActivity = this;
            Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(thisActivity,MapDialogActivity.class);
                    startActivity(i);
                }
            });


        }else Map.setVisibility(View.GONE);
    }

    private void setupLikes(final Event event){
        final Context thisContext = this;
        final String android_id = Settings.Secure.getString(thisContext.getContentResolver(), Settings.Secure.ANDROID_ID);


            LikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final RequestCallerPOSTVote req = new RequestCallerPOSTVote();
                        int res = req.execute(Integer.toString(event.EventId), Integer.toString(1), android_id).get();
                        if(res==200){
                            Toast.makeText(thisContext,"Pomyślnie zagłosowano",Toast.LENGTH_LONG).show();
                            Rating.setText(Integer.toString(Integer.parseInt(Rating.getText().toString())+1));
                        }else if(res==403){
                            Toast.makeText(thisContext,"Zagłosowano już z tego urządzenia",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(thisContext,"Błąd po stronie serwera",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception ex){ex.printStackTrace();}
                }
            });

            DislikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final RequestCallerPOSTVote req = new RequestCallerPOSTVote();
                        int res = req.execute(Integer.toString(event.EventId), Integer.toString(-1), android_id).get();
                        if(res==200){
                            Toast.makeText(thisContext,"Pomyślnie zagłosowano",Toast.LENGTH_LONG).show();
                            Rating.setText(Integer.toString(Integer.parseInt(Rating.getText().toString())-1));
                        }else if(res==403){
                            Toast.makeText(thisContext,"Zagłosowano już z tego urządzenia",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(thisContext,"Błąd po stronie serwera",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception ex){ex.printStackTrace();}
                }
            });

    }

    private void BindText(){
        Event event;
        boolean found = false;
        for(int i = 0; i< LoadedEvents.Events.size(); i++){
            if(LoadedEvents.Events.get(i).EventId == EventId){
                event = LoadedEvents.Events.get(i);
                CurrentEvent = event;
                SetFields(event);
                found = true;
                break;
            }
        }
        if(!found){
            Toast.makeText(this,"Błąd po stronie aplikacji. Nie znaleziono wydarzenia",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    private void setLogo(Event event){
        if(event.ImageUrl.equals("null")){}else{
            Logo.setImageDrawable(event.Logo);
        }
    }

    private void SetFields(final Event event){
        Title.setText(event.Title);
        Description.setText(event.Description);
        LinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(event.URL.toString()));
                startActivity(viewIntent); }
        });
        int rating = event.VotesPositive - event.VotesNegative;
        Rating.setText(Integer.toString(rating));
        setLogo(event);
        setupNotificationButton(event);
        setupSmsButton(event);
        setupMapButton(event);
        setupLikes(event);
    }

    private void setupSmsButton(final Event event){
        SmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", "Zapraszam Cię na: "+event.Title+"\nStartujemy "+event.DMYHMDate());
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

    }

    private void setupNotificationButton(final Event event){
        NotificaitonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", event.Date.getTime());
                intent.putExtra("allDay", false);
                intent.putExtra("rrule", "FREQ=DAILY");
                intent.putExtra("endTime", event.Date.getTime());
                intent.putExtra("title", event.Title);
                startActivity(intent);
            }
        });
    }

}
