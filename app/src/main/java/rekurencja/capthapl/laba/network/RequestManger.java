package rekurencja.capthapl.laba.network;

import android.util.Log;

import rekurencja.capthapl.laba.enums.ERequestTypes;

public class RequestManger {

    private String MakeRequest(ERequestTypes type) throws Exception{
        RequestCaller caller = new RequestCaller();
        switch(type){
            case GetEvents:
                return caller.execute("http://tumska.sldemo.pl/event/get-events-calendar").get();
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
