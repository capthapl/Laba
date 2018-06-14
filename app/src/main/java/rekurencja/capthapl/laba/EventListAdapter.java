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

import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.rekurencja.delta.R;
import pl.rekurencja.delta.activities.MainActivity;
import pl.rekurencja.delta.entities.Club;
import pl.rekurencja.delta.enums.EClubResultTypes;
import pl.rekurencja.delta.fragments.ShowClubFragment;
import rekurencja.capthapl.laba.Entities.Event;

public class ClubListAdapter extends BaseAdapter {

    private Activity context_1;

    private ArrayList<Event> pairs;

    public ClubListAdapter(Activity context,
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
            FillFlowLayout(position,viewHolder);
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
       // viewHolder.Title.setText(pairs.get(position).Name);
    }



    private void SetupViewHolder(ViewHolder viewHolder,View convertView){
      //  viewHolder.Logo = (CircleImageView) convertView.findViewById(R.id.club_row_logo);

    }

    public class ViewHolder {
        public TextView Title;
        public TextView Ocena;
        public ImageView Owner;
        public FlowLayout FlowLayout;
        public CircleImageView Logo;
        public TextView Members;
    }
}