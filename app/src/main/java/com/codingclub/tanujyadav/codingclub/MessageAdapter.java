package com.codingclub.tanujyadav.codingclub;

/**
 * Created by 15121 on 2/2/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Pc on 1/14/2017.
 */

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {
    private static String username;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects, String username) {
        super(context, resource, objects);
        this.username = username;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView authorTextView1 = (TextView) convertView.findViewById(R.id.nameTextView1);
        LinearLayout ll=(LinearLayout) convertView.findViewById(R.id.me);
        LinearLayout ll1=(LinearLayout) convertView.findViewById(R.id.me1);

        //  LinearLayout ll1=(LinearLayout) convertView.findViewById(R.id.message);
        FriendlyMessage message = getItem(position);

        boolean isPhoto = message.getPhotoUrl() != null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        if (isPhoto) {

           // messageTextView.setVisibility(View.GONE);
            ll1.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
            authorTextView1.setText(message.getName());
            if (authorTextView1.getText().toString().equals(username)) {
                // Toast.makeText(parent.getContext(), username, Toast.LENGTH_SHORT).show();
                //params.weight = 1.0f;

                ll1.setBackground(ResourcesCompat.getDrawable(getContext().getResources(),R.drawable.bubble_a,null));// getResources().getDrawable( R.drawable.bubble_a));
                //authorTextView.setHeight(0);
                authorTextView1.setText("");
                authorTextView1.setHeight(0);
                //messageTextView.setGravity(Gravity.END);
                //  authorTextView.setHeight(0);

            }
            else
            {
                ViewGroup.LayoutParams paramss = authorTextView1.getLayoutParams();
                paramss.height = getContext().getResources().getDimensionPixelSize(R.dimen.ct4);
                authorTextView1.setLayoutParams(paramss);
                //authorTextView1.setHeight(getResources().getDimensionPixelSize(R.dimen.fh3));

                ll1.setBackground(ResourcesCompat.getDrawable(getContext().getResources(),R.drawable.bubble_b,null));

            }
        //    authorTextView.setText(message.getName());
            Glide.with(photoImageView.getContext()).load(message.getPhotoUrl()).into(photoImageView);
        } else {
            messageTextView.setGravity(Gravity.START);
            //messageTextView.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
            authorTextView.setText(message.getName());
         //   authorTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));



            if (authorTextView.getText().toString().equals(username)) {
               // Toast.makeText(parent.getContext(), username, Toast.LENGTH_SHORT).show();
                 //params.weight = 1.0f;
                params.gravity = Gravity.RIGHT;
                ll.setBackground(ResourcesCompat.getDrawable(getContext().getResources(),R.drawable.bubble_a,null));// getResources().getDrawable( R.drawable.bubble_a));
                //authorTextView.setHeight(0);
                authorTextView.setText("");
                authorTextView.setHeight(0);
                ll.setLayoutParams(params);
                //messageTextView.setGravity(Gravity.END);
              //  authorTextView.setHeight(0);

            }
            else
            {
                params.gravity = Gravity.LEFT;
                ViewGroup.LayoutParams paramss = authorTextView.getLayoutParams();
                paramss.height = getContext().getResources().getDimensionPixelSize(R.dimen.ct4);
                authorTextView.setLayoutParams(paramss);

                ll.setBackground(ResourcesCompat.getDrawable(getContext().getResources(),R.drawable.bubble_b,null));
                ll.setLayoutParams(params);
            }
        }


        return convertView;

    }

}