package rekurencja.capthapl.laba;

import android.app.Activity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import rekurencja.capthapl.laba.Entities.Event;

public class EventListAdapter extends BaseAdapter {

    private Activity context_1;

    private ArrayList<Event> pairs;

    public EventListAdapter(Activity context,
                            ArrayList<Event> pairs) {
        context_1 = context;
        this.pairs = pairs;
    }

    @Override
    public int getCount() {
        return pairs.size();
    }

    @Override
    public Object getItem(int position) {
        return pairs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(context_1).inflate(
                    R.layout.event_row, null);

            SetupViewHolder(viewHolder,convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        RedirectToClubPageHandler(convertView,position);
        FillViewHolderWithContent(viewHolder,position);
        return convertView;
    }


    private void RedirectToClubPageHandler(View view, final int position){
          /*  view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowClubFragment.ClubId = pairs.get(position).Club_id;
                    ((MainActivity)context_1).SetFragment(EClubResultTypes.ShowClub);
                }
            });*/
    }

    private void FillViewHolderWithContent(ViewHolder viewHolder,int position){
        viewHolder.Title.setText(pairs.get(position).Title);
        viewHolder.Ocena.setText(Integer.toString(pairs.get(position).VotesPositive));
        viewHolder.Description.setText(pairs.get(position).Description);
        viewHolder.Date.setText(pairs.get(position).DMYDate());
        viewHolder.Logo.setImageResource(R.drawable.placeholder_event);
    }



    private void SetupViewHolder(ViewHolder viewHolder,View convertView){
        viewHolder.Logo = (CircleImageView) convertView.findViewById(R.id.profile_image);
        viewHolder.Title = convertView.findViewById(R.id.event_title);
        viewHolder.Ocena = convertView.findViewById(R.id.event_rating);
        viewHolder.Description = convertView.findViewById(R.id.event_description);
        viewHolder.Date = convertView.findViewById(R.id.event_date);

    }

    public class ViewHolder {
        public TextView Title;
        public TextView Ocena;
        public TextView Description;
        public CircleImageView Logo;
        public TextView Date;
    }
}