package com.rebot;

import java.util.ArrayList;
import java.util.List;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class rebotAdapter extends BaseAdapter {
    List<rebotBean> list = new ArrayList<rebotBean>();
    LayoutInflater inflater;
    Context context;
    ClipboardManager cm;

    public rebotAdapter(Context context, List<rebotBean> list) {
        cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {// listview返回类型有两种,角标从0开始
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 1)
            return 0;// 返回的数据位角标
        else
            return 1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String info = list.get(position).getText();
        int type = list.get(position).getType();
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            if (type == 1) {
                convertView = inflater.inflate(R.layout.rebot_me_talk_item, parent, false);
                holder.tv = (TextView) convertView.findViewById(R.id.chat_me_text);
            } else {
                convertView = inflater.inflate(R.layout.rebot_net_talk_item, parent, false);
                holder.tv = (TextView) convertView.findViewById(R.id.chat_net_text);
            }
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cm.setText(holder.tv.getText());
                    Toast.makeText(context, "已剪切到剪贴板", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(info);
        return convertView;
    }

    class ViewHolder {
        private TextView tv;
    }

}
