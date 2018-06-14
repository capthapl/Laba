package rekurencja.capthapl.laba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import rekurencja.capthapl.laba.enums.ERequestTypes;
import rekurencja.capthapl.laba.network.RequestCaller;
import rekurencja.capthapl.laba.network.RequestManger;

public class MainActivity extends Activity {

    CompactCalendarView Calendar;
    ImageButton CalendarButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar = findViewById(R.id.calendar);
        CalendarButton = findViewById(R.id.calendar_button);
        RequestManger m = new RequestManger();

        m.GetResponse(ERequestTypes.GetEvents);
        setupCalendarButton();
    }

    private void setupCalendarButton(){
        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Calendar.getVisibility()==View.GONE)
                    Calendar.setVisibility(View.VISIBLE);
                else Calendar.setVisibility(View.GONE);
            }
        });
    }
}
