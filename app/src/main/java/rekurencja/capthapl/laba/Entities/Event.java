package rekurencja.capthapl.laba.Entities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    public int EventId;
    public Date Date;
    public String Title;
    public String Description;
    @Nullable
    public String Location;
    @Nullable
    public String ImageUrl;
    public String URL;
    public int VotesPositive;
    public int VotesNegative;
    public Drawable Logo;

    public Event(int id,Date date,String title,String description,String location,String imgUrl,int votesP,int votesN,String url){
        EventId = id;
        Date = date;
        Title = title;
        Description = description;
        Location = location;
        ImageUrl = imgUrl;
        VotesPositive = votesP;
        VotesNegative = votesN;
        URL = url;
    }
    public String DMYDate(){
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd.MM.yyyy");
        return ft.format(Date);
    }

    public String DMYHMDate(){
        SimpleDateFormat ft =
                new SimpleDateFormat ("dd.MM.yyyy 'o godzinie' HH:mm");
        return ft.format(Date);
    }
    public int GetRating(){
        return VotesPositive-VotesNegative;
    }
}
