package com.vandac.weather.android.network.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vandac.weather.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vanda on 8. 4. 2015.
 */
public class NavigationDrawerAdapter extends BaseAdapter
{
    private List<String> menuList        = new ArrayList<String>();
    private int[]        iconResourceIds = {};
    Context mContext;

    public NavigationDrawerAdapter(Context context, List<String> menuList, int[] iconResourceIds)
    {
        mContext = context;
        this.menuList = menuList;
        this.iconResourceIds = iconResourceIds;
    }

    @Override public int getCount()
    {
        return menuList.size();
    }

    @Override public Object getItem(int position)
    {
        if (menuList.size() > position)
        {
            return menuList.get(position);
        }
        else
        {
            return null;
        }
    }

    @Override public long getItemId(int position)
    {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_navigation_drawer, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.navigation_drawer_item_text);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.nav_drawer_item_icon);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        String menuText = (String) getItem(position);
        int resourceId = iconResourceIds[position];

        viewHolder.imageView.setImageResource(resourceId);
        viewHolder.textView.setText(menuText);

        return convertView;
    }

    private static class ViewHolder
    {
        private TextView  textView;
        private ImageView imageView;
    }
}
