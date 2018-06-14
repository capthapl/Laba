package rekurencja.capthapl.laba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import rekurencja.capthapl.laba.Entities.Event;
import rekurencja.capthapl.laba.enums.ERequestTypes;
import rekurencja.capthapl.laba.network.RequestManger;

public class MainActivity extends Activity {

    CompactCalendarView Calendar_;
    ImageButton CalendarButton;
    RequestManger Requests;
    List<Event> Events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar_ = findViewById(R.id.calendar);
        CalendarButton = findViewById(R.id.calendar_button);
        Requests = new RequestManger();
        setupCalendarButton();
        try {
            ParseEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEvents();
    }

    private void setupCalendarButton(){
        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Calendar_.getVisibility()==View.GONE)
                    Calendar_.setVisibility(View.VISIBLE);
                else Calendar_.setVisibility(View.GONE);
            }
        });
    }

    private String DownloadICS(){
        return Requests.GetResponse(ERequestTypes.GetEvents);
    }

    private void ParseEvents() throws Exception{
        String ICS = DownloadICS();
        ICalendar ical = Biweekly.parse(ICS).first();
        Log.d("XD",Integer.toString(ical.getEvents().size()));
        for(int i = 0;i<ical.getEvents().size();i++){
             VEvent e = ical.getEvents().get(i);
             int positive = Integer.parseInt(e.getExperimentalProperty("X-VOTES-POSITIVE").getValue());
             int negative = Integer.parseInt(e.getExperimentalProperty("X-VOTES-NEGATIVE").getValue());
             int eventId = Integer.parseInt(e.getExperimentalProperty("X-EVENT-ID").getValue());
             String imageUrl;
             try{
                 imageUrl = e.getExperimentalProperty("X-IMAGE-URL").getValue();
             }catch (NullPointerException ex){imageUrl = "null";}
             Date date = e.getDateStart().getValue();
             String title = e.getSummary().getValue();
             String description = e.getSummary().getValue();
             String location = e.getLocation().getValue();
             Event tempEvent = new Event(eventId,date,title,description,location,imageUrl,positive,negative);
             Events.add(tempEvent);
        }
    }

    private void printEvents(){
        for(Event i : Events){
            Log.d("Event: ",i.EventId + " "+i.Title+" "+i.Description+" "+i.Date.toString()+ " "+i.VotesPositive+" "+i.VotesNegative+" "+i.ImageUrl);
        }
    }
}
