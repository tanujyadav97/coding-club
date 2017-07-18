package com.codingclub.tanujyadav.codingclub;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TANUJ YADAV on 7/22/2016.
 */
public class CustomListAdapter extends ArrayAdapter{
    private Context mContext;
    private int id,aa;
    private String[] items ;

    public CustomListAdapter(Context context, int textViewResourceId , String[] list ,int a)
    {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list ;
        aa=a;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent)
    {
        View mView = v ;
        if(mView == null){
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(id, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.textView);

        if(items[position] != null )
        {
            if(aa==1)
            text.setTextColor(Color.parseColor("#f9ab18"));
            else
                text.setTextColor(Color.YELLOW);
            text.setText(items[position]);

        }

        return mView;
    }
}
