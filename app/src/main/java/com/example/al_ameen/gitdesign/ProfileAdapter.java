package com.example.al_ameen.gitdesign;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Al-avatar on 09/08/2017.
 */
public class ProfileAdapter extends ArrayAdapter<Profile> {
    Activity context;
    public ProfileAdapter(Activity context, ArrayList<Profile> profile) {
        super(context, 0,profile);
        this.context = context;
    }

    private class ViewHolder{
        TextView fullName;
        TextView userName;
        CircleImageView profileIcon;
        TextView bio;
        TextView totalRepo;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Profile currentProfile = getItem(position);
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder();
            holder.profileIcon = (CircleImageView) itemView.findViewById(R.id.profile_icon);
            holder.fullName = (TextView) itemView.findViewById(R.id.txt_full_name);
            holder.userName = (TextView) itemView.findViewById(R.id.txt_user_name);
            holder.bio = (TextView) itemView.findViewById(R.id.txt_bio);
            holder.totalRepo = (TextView) itemView.findViewById(R.id.txt_total_repo);
            itemView.setTag(holder);
        }else {
            holder = (ViewHolder) itemView.getTag();
        }

        Picasso.with(context).load(currentProfile.getMProfileResourceId()).placeholder(R.drawable.avatar).into(holder.profileIcon);
        holder.fullName.setText(currentProfile.getMFullName());
        holder.userName.setText(currentProfile.getMUserName());
        holder.bio.setText(currentProfile.getMBio());
        holder.totalRepo.setText(currentProfile.getMTotalRepo());

        return itemView;
    }


}
