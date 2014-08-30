package com.example.demohlistview;

import java.util.ArrayList;
import java.util.List;
import com.example.demohlistview.MyHScrollView.OnScrollChangedListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    ListView mListView1;
    MyAdapter myAdapter;
    RelativeLayout rlHead;
    LinearLayout main;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlHead = (RelativeLayout) findViewById(R.id.rlHead);
        rlHead.setFocusable(true);
        rlHead.setClickable(true);
        rlHead.setBackgroundColor(Color.parseColor("#b2d235"));
        rlHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        mListView1 = (ListView) findViewById(R.id.listView1);
        mListView1.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());

        myAdapter = new MyAdapter(this, R.layout.item);
        mListView1.setAdapter(myAdapter);
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            // 当在列头 和 listView控件上touch时，将这个touch的事件分发给ScrollView
            //（不管touch哪里，都要通知列头跟着动，下文的每个item向列头订阅了滚动事件，从而与列头联动）
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) rlHead
                    .findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            //继续向子view下发touch事件
            return false;
        }
    }

    public class MyAdapter extends BaseAdapter {
        public List<ViewHolder> mHolderList = new ArrayList<ViewHolder>();

        int id_row_layout;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int id_row_layout) {
            super();
            this.id_row_layout = id_row_layout;
            mInflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 250;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parentView) {
            ViewHolder holder = null;
            if (convertView == null) {
                synchronized (MainActivity.this) {
                    convertView = mInflater.inflate(id_row_layout, null);
                    holder = new ViewHolder();

                    MyHScrollView scrollView1 = (MyHScrollView) convertView.findViewById(R.id.horizontalScrollView1);

                    holder.scrollView = scrollView1;
                    holder.txt1 = (TextView) convertView.findViewById(R.id.textView1);
                    holder.txt2 = (TextView) convertView.findViewById(R.id.textView2);
                    holder.txt3 = (TextView) convertView.findViewById(R.id.textView3);
                    holder.txt4 = (TextView) convertView.findViewById(R.id.textView4);
                    holder.txt5 = (TextView) convertView.findViewById(R.id.textView5);

                    MyHScrollView headSrcrollView = (MyHScrollView) rlHead.findViewById(R.id.horizontalScrollView1);
                    //列头动的时候通知该item动
                    headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));

                    convertView.setTag(holder);
                    mHolderList.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txt1.setText(position + "" + 1);
            holder.txt2.setText(position + "" + 2);
            holder.txt3.setText(position + "" + 3);
            holder.txt4.setText(position + "" + 4);
            holder.txt5.setText(position + "" + 5);

            return convertView;
        }

        class OnScrollChangedListenerImp implements OnScrollChangedListener {
            MyHScrollView mScrollViewArg;

            public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
                mScrollViewArg = scrollViewar;
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                mScrollViewArg.smoothScrollTo(l, t);
            }
        };

        class ViewHolder {
            TextView txt1;
            TextView txt2;
            TextView txt3;
            TextView txt4;
            TextView txt5;
            HorizontalScrollView scrollView;
        }
    }// end class my

}
