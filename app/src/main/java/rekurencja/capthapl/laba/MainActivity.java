package rekurencja.capthapl.laba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import rekurencja.capthapl.laba.enums.ERequestTypes;
import rekurencja.capthapl.laba.network.RequestCaller;
import rekurencja.capthapl.laba.network.RequestManger;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestManger m = new RequestManger();
        String x = m.GetResponse(ERequestTypes.GetEvents);
    }

}
