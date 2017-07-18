package com.codingclub.tanujyadav.codingclub;

/**
 * Created by TANUJ YADAV on 8/25/2016.
 */
import com.codingclub.tanujyadav.codingclub.FeedImageView;
import com.codingclub.tanujyadav.codingclub.R;
import com.codingclub.tanujyadav.codingclub.AppController;
import com.codingclub.tanujyadav.codingclub.FeedItem;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import static java.lang.Long.parseLong;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader;

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        imageLoader = AppController.getInstance().getImageLoader();
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        Button bb=(Button) convertView.findViewById(R.id.register);
        Button bb1=(Button) convertView.findViewById(R.id.reg);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);
        TextView timee = (TextView) convertView.findViewById(R.id.time);
        TextView loc = (TextView) convertView.findViewById(R.id.venue);
        TextView organiser = (TextView) convertView.findViewById(R.id.type);
        TextView contact1 = (TextView) convertView.findViewById(R.id.coordinator);
        FeedItem item = feedItems.get(position);


        if(System.currentTimeMillis()>parseLong(item.getTimeStamp()))
        {
            bb.setEnabled(false);
            bb1.setEnabled(false);
        }
        else
        {
            bb.setEnabled(true);
            bb1.setEnabled(true);
        }

        bb.setText(item.getName()+item.getTimeStamp());
        bb.setBackgroundColor(Color.TRANSPARENT);
        bb.setTextColor(Color.TRANSPARENT);
        name.setText(item.getName());
        timee.setText(item.gettime());
        loc.setText(item.getloc());
        organiser.setText(item.getorganiser());
        contact1.setText(item.getcontact());
        // Converting timestamp into x ago format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeAgo);


        // Chcek for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        // Checking for null feed url
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }

        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        if (item.getImge() != null) {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}