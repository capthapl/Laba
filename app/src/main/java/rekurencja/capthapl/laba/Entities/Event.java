package rekurencja.capthapl.laba.Entities;

import android.support.annotation.Nullable;

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
    public int VotesPositive;
    public int VotesNegative;

    public Event(int id,Date date,String title,String description,String location,String imgUrl,int votesP,int votesN){
        EventId = id;
        Date = date;
        Title = title;
        Description = description;
        Location = location;
        ImageUrl = imgUrl;
        VotesPositive = votesP;
        VotesNegative = votesN;
    }
}
