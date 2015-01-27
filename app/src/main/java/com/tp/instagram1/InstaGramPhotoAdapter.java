package com.tp.instagram1;

import android.content.Context;
import android.graphics.Movie;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by seshasa on 1/25/15.
 */
public class InstaGramPhotoAdapter extends ArrayAdapter<InstaGramPhoto>{

    public InstaGramPhotoAdapter(Context context, List<InstaGramPhoto> objects) {
        //super(context, android.R.layout.simple_list_item_1, objects);
        super(context, R.layout.item_photo, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstaGramPhoto photo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhone = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);

        ivPhone.getLayoutParams().height = photo.imageHeight;
        ivPhone.setImageResource(0);
        Picasso.with(getContext()).load(photo.image_url).into(ivPhone);

        tvCaption.setText(photo.caption);
        tvUserName.setText(Html.fromHtml("<b><i> by </i></b>"+photo.username));
        tvLikes.setText(NumberFormat.getInstance().format(photo.likesCount));
        return convertView;
    }
}
