package com.rebot;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RebotActivity extends AppCompatActivity {
    private ListView mListView;
    private AppCompatEditText edit_info;
    List<rebotBean> list = new ArrayList<rebotBean>();
    private ImageView back_cancel;
    rebotAdapter adapter;
    String msge;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String backData = (String) msg.obj;
                    String text;
                    String code;
                    Log.i("backdata", backData);
                    try {
                        JSONObject obj = new JSONObject(backData);
                        text = obj.getString("text");
                        rebotBean bean = new rebotBean();
                        bean.setType(1);
                        bean.setText(text);
                        list.add(bean);
                        adapter.notifyDataSetChanged();// 提醒adapter更新数据
                        mListView.setSelection(list.size() - 1);
                        code = obj.getString("code");

                        if (code.equals("200000")) {
                            Intent intent = new Intent(RebotActivity.this, WebActivity.class);
                            intent.putExtra("url", obj.getString("url"));
                            startActivity(intent);
                        } else if (code.equals("302000")) {
                            Intent intent = new Intent(RebotActivity.this, NewsActivity.class);
                            intent.putExtra("result", backData);
                            intent.putExtra("title", msge);
                            intent.putExtra("code", "302000");
                            startActivity(intent);
                        } else if (code.equals("308000")) {
                            Intent intent = new Intent(RebotActivity.this, NewsActivity.class);
                            intent.putExtra("result", backData);
                            intent.putExtra("title", msge);
                            intent.putExtra("code", "308000");
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reboot);
        mListView = (ListView) findViewById(R.id.rebot_listView);
        edit_info = (AppCompatEditText) findViewById(R.id.rebot_info);

        rebotBean bean = new rebotBean();
        bean.setType(1);
        bean.setText("你好啊");
        list.add(bean);
        adapter = new rebotAdapter(this, list);
        mListView.setAdapter(adapter);
    }

    public void send_info(View v) {
        msge = edit_info.getText().toString().trim();
        if (msge.equals("")) return;


        edit_info.setText("");
        rebotBean bean = new rebotBean();
        bean.setType(2);
        bean.setText(msge);
        list.add(bean);
        adapter.notifyDataSetChanged();// 提醒adapter更新数据
        mListView.setSelection(list.size() - 1);// 将滚屏始终对着最后一条item

        if (!NetWorkOK.isOK(this)) {
            rebotBean bean1 = new rebotBean();
            bean1.setType(1);
            bean1.setText("当前网络不可用");
            list.add(bean1);
            adapter.notifyDataSetChanged();// 通知listview更新当前的item
            mListView.setSelection(list.size() - 1);// 将滚屏始终对着最后一条item
            return;
        }

        new Thread() {
            public void run() {
                String netMsg = getHtmlInfo.getMsg(msge);
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = netMsg;
                handler.sendMessage(msg);
            }

            ;
        }.start();
    }

}
