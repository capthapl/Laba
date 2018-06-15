package rekurencja.capthapl.laba;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import rekurencja.capthapl.laba.Entities.Event;
import rekurencja.capthapl.laba.enums.ERequestTypes;
import rekurencja.capthapl.laba.network.ImageDownloader;
import rekurencja.capthapl.laba.network.RequestManger;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends Activity {

    CompactCalendarView Calendar;
    ImageButton CalendarButton;
    ImageButton SortButton;
    Button ShowAllEventsBtn;
    RequestManger Requests;
    public static ArrayList<Event> Events = new ArrayList<>();
    ListView EventList;
    LinearLayout SortContainer;
    EventListAdapter adapter;
    Activity ThisActivity;
    public static boolean first = true;
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

        setContentView(R.layout.activity_main);
        Calendar = findViewById(R.id.calendar);
        CalendarButton = findViewById(R.id.calendar_button);
        SortContainer = findViewById(R.id.sort_container);
        ShowAllEventsBtn = findViewById(R.id.show_all_events_btn);

        SortButton = findViewById(R.id.sort_button);
        ThisActivity = this;
        Requests = new RequestManger();
        setupCalendarButton();
        setupSortButton();
        if(first==true) {
            try {
                ParseEvents();
                DownloadAndSetImages(Events);
            } catch (Exception e) {
                e.printStackTrace();
            }
            first = false;
        }
        printEvents();
        EventList = findViewById(R.id.event_listview);
        adapter = new EventListAdapter(this,Events);
        EventList.setAdapter(adapter);
        setCalendarEvents();
        setupSortButton();
        setupSortOptions();
        setupShowAllEventsButton();
        setupCalendarEventClick();
        Calendar.setCurrentSelectedDayBackgroundColor(Color.RED);

    }

    private void setupCalendarEventClick(){
        Calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
                String formatedDate = ft.format(dateClicked);
                ArrayList<Event> restrictedEvents = new ArrayList<Event>();
                for(int i = 0;i<Events.size();i++){
                    if(Events.get(i).DMYDate().equals(formatedDate)){
                        restrictedEvents.add(Events.get(i));
                    }
                }
                if(restrictedEvents.size()>0){
                    adapter = new EventListAdapter(ThisActivity,restrictedEvents);
                    EventList.setAdapter(adapter);
                    ShowAllEventsBtn.setVisibility(View.VISIBLE);
                    Calendar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ThisActivity,"Tego dnia nie ma żadnych wydarzeń",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });
    }

    private void setupShowAllEventsButton(){
        ShowAllEventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainAdapter();
                ShowAllEventsBtn.setVisibility(View.GONE);
            }
        });
    }

    private void setMainAdapter(){
        adapter = new EventListAdapter(ThisActivity,Events);
        EventList.setAdapter(adapter);
    }

    private void setupCalendarButton(){
        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Calendar.getVisibility()==View.GONE)
                    Calendar.setVisibility(View.VISIBLE);
                else Calendar.setVisibility(View.GONE);
                SortContainer.setVisibility(View.GONE);
            }
        });
    }

    private void setupSortButton(){
        SortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SortContainer.getVisibility()==View.GONE)
                    SortContainer.setVisibility(View.VISIBLE);
                else SortContainer.setVisibility(View.GONE);
                Calendar.setVisibility(View.GONE);
            }
        });
    }

    private void setupSortOptions(){
        Button byName = findViewById(R.id.sort_by_name);
        Button byRating = findViewById(R.id.sort_by_rating);
        Button byDate = findViewById(R.id.sort_by_date);

        byRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(Events, new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        return (o1.GetRating() - o2.GetRating())*-1; // Ascending
                    }
                });
                adapter.notifyDataSetChanged();
                SortContainer.setVisibility(View.GONE);
            }
        });

        byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(Events, new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        return (o2.Date.compareTo(o1.Date));
                    }
                });
                adapter.notifyDataSetChanged();
                SortContainer.setVisibility(View.GONE);
            }
        });

        byName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(Events, new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        return (o2.Title.toLowerCase().compareTo(o1.Title.toLowerCase()));
                    }
                });
                adapter.notifyDataSetChanged();
                SortContainer.setVisibility(View.GONE);
            }
        });

    }


    private String DownloadICS(){
        return Requests.GetResponse(ERequestTypes.GetEvents);
    }

    private void ParseEvents() throws Exception{
        String ICS = DownloadICS();
        ICalendar ical = Biweekly.parse(ICS).first();
        for(int i = 0;i<ical.getEvents().size();i++){
             VEvent e = ical.getEvents().get(i);
             Date today = new Date();

             if(e.getDateStart().getValue().getTime() < today.getTime())
                 continue;
             int positive = Integer.parseInt(e.getExperimentalProperty("X-VOTES-POSITIVE").getValue());
             int negative = Integer.parseInt(e.getExperimentalProperty("X-VOTES-NEGATIVE").getValue());
             int eventId = Integer.parseInt(e.getExperimentalProperty("X-EVENT-ID").getValue());
             String imageUrl;
             try{
                 imageUrl = e.getExperimentalProperty("X-IMAGE-URL").getValue();
             }catch (NullPointerException ex){imageUrl = "null";}
             Date date = e.getDateStart().getValue();
             String title = e.getSummary().getValue();
             String url = e.getUrl().getValue();
             String description = e.getSummary().getValue();
             String location = e.getLocation().getValue();
             Event tempEvent = new Event(eventId,date,title,description,location,imageUrl,positive,negative,url);
             Events.add(tempEvent);
        }
    }


    public void DownloadAndSetImages(ArrayList<Event> events){
        for(int i = 0;i<events.size();i++){
            if(events.get(i).ImageUrl.equals("null")){
                events.get(i).Logo = getResources().getDrawable(R.drawable.placeholder_event);
            }else{
                try {
                    ImageDownloader d = new ImageDownloader();
                    Bitmap b = d.execute(events.get(i).ImageUrl).get();
                    events.get(i).Logo = new BitmapDrawable(getResources(),b);
                }catch (Exception ex){
                    events.get(i).Logo = getResources().getDrawable(R.drawable.placeholder_event);
                }

            }
        }
    }



    private void setCalendarEvents(){
        for(int i = 0;i<Events.size();i++) {
            com.github.sundeepk.compactcalendarview.domain.Event event = new com.github.sundeepk.compactcalendarview.domain.Event(Color.BLUE,Events.get(i).Date.getTime());

            Calendar.addEvent(event);
        }
    }

    private void printEvents(){
        for(Event i : Events){
            Log.d("Event: ",i.EventId + " "+i.Title+" "+i.Description+" "+i.Date.toString()+ " "+i.VotesPositive+" "+i.VotesNegative+" "+i.ImageUrl);
        }
    }
}
