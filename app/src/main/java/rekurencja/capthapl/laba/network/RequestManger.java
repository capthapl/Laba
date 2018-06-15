package rekurencja.capthapl.laba.network;

import android.graphics.Bitmap;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rekurencja.capthapl.laba.Entities.Event;
import rekurencja.capthapl.laba.enums.ERequestTypes;

public class RequestManger {

    private String MakeRequest(ERequestTypes type,String...args) throws Exception{
        RequestCaller caller = new RequestCaller();
        switch(type){
            case GetEvents:
                Date today = new Date();
                SimpleDateFormat ft =
                        new SimpleDateFormat ("yyyy.MM.dd");
                String todayS =  ft.format(today);
                return caller.execute("https://tumskanova.pl/event/get-events-calendar?date_from="+todayS).get();
             default: return "";
        }
    }


    public String GetResponse(ERequestTypes type){
        try {
            return MakeRequest(type);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


}
