package com.taozhang.demo_mutualagriculture.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taozhang.demo_mutualagriculture.R;
import com.taozhang.demo_mutualagriculture.bean.OrderInfo;
import com.taozhang.demo_mutualagriculture.bean.Task;
import com.taozhang.demo_mutualagriculture.service.MyService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 历史订单类
 */
public class HistoryOrderActivity extends AppCompatActivity {

    //TODO:测试用
    public TestAdapter mTestAdapter;
    private ArrayList<OrderInfo> mOrderInfos = new ArrayList<OrderInfo>();
    private OrderInfo mOrderInfo;
    public int currentTotal;
    OrderInfo orderInfo = new OrderInfo();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        mTestAdapter = new TestAdapter();
        initData();
        //获取组件
        ImageView img_historyAct_return = (ImageView) findViewById(R.id.img_historyAct_return);
        TextView tv_historyAct_return = (TextView) findViewById(R.id.tv_historyAct_return);
        TextView tv_orderAct_edit = (TextView) findViewById(R.id.tv_historyAct_edit);
        ListView listView = (ListView) findViewById(R.id.lv_historyOrder);

        //添加监听
        img_historyAct_return.setOnClickListener(mOnClickListener);
        tv_historyAct_return.setOnClickListener(mOnClickListener);
        tv_orderAct_edit.setOnClickListener(mOnClickListener);
        //listView
        listView.setAdapter(mTestAdapter);
        listView.setOnScrollListener(mOnScrollListener);
        listView.setOnItemClickListener(mOnItemClickListener);

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;
            switch (id) {
                case R.id.img_historyAct_return:
                    intent = new Intent(getApplicationContext(), DrawerArrowActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.tv_historyAct_return:
                    intent = new Intent(getApplicationContext(), DrawerArrowActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.tv_historyAct_edit:
                    //TODO:
                    Toast.makeText(getApplicationContext(), "edit", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && currentTotal == mTestAdapter.getCount()) {
            //滚动停止，并且currentTotal==adapter的数量(数据的数量)
                //发送请求，获取数据
                Task task = new Task();
                task.setTaskId(Task.GET_ORDERS);
                MyService.addTask(task);
                MyService homeService = new MyService();
                new Thread(homeService).start();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            currentTotal = firstVisibleItem + visibleItemCount - 1;
            System.out.println("onScroll------currentTotal:" + currentTotal);
        }
    };

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            OrderInfo orderInfo = mOrderInfos.get(position);
            Toast.makeText(getApplicationContext(),"position:"+position+",订单编号:"+orderInfo.getId(),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), OrderDetailActivity.class);
            startActivity(intent);
            finish();
        }
    };

    private void initData() {
        for (int i=0;i<15;i++){
            orderInfo.setId(1000000 + i);
            orderInfo.setState("已完成");
            orderInfo.setCommission(105.5 + i);
            orderInfo.setStartTime(new java.sql.Date(System.currentTimeMillis()));
            orderInfo.setMachineOwnerId(000001 + i);
            mOrderInfos.add(orderInfo);
        }
    }

    private class TestAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mOrderInfos != null && mOrderInfos.size() > 0) {
                return mOrderInfos.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {//数据项
            if (mOrderInfos != null && mOrderInfos.size() > 0) {
                return mOrderInfos.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //获取订单项（内容）
            //1.获取数据
            mOrderInfo = mOrderInfos.get(position);
            //2.把数据添加到item上，并显示（即return）
            ViewHolder holder = null;
            if (holder == null) {
                //反射出item
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.list_item_historyorder, null);//convertView就是listItem
                holder.id = (TextView) convertView.findViewById(R.id.tv_historyOrderItem_id);
                holder.state = (TextView) convertView.findViewById(R.id.tv_historyOrderItem_state);
                holder.price = (TextView) convertView.findViewById(R.id.tv_historyOrderItem_price);
                holder.figure = (ImageView) convertView.findViewById(R.id.img_historyOrderItem_figure);
                holder.time = (TextView) convertView.findViewById(R.id.tv_historyOrderItem_time);
                holder.actor = (TextView) convertView.findViewById(R.id.tv_historyOrderItem_actor);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //设置内容
            holder.id.setText("订单编号：" + mOrderInfo.getId());
            holder.price.setText("订单价格："+mOrderInfo.getCommission());
            holder.state.setText("订单状态：" + mOrderInfo.getState());
            holder.time.setText("订单开始时间：" + mOrderInfo.getStartTime());
            holder.actor.setText("收割者：" + mOrderInfo.getMachineOwnerId());
//            holder.figure.setImageResource(R.drawable.avatar_default);//暂时用户头像设置为把配图
            holder.figure.setImageResource(R.drawable.avatar_default);
            //        new ImageLoader().showImageByAsyncTask(holder.figure, mUser.profile_image_url);//把头像重绘成圆形
            System.out.println("设置成功----HistoryOrder");
            return convertView;
        }
    }

    static class ViewHolder {
        /**
         * 订单编号
         */
        private TextView id;
        /**
         * 状态
         */
        private TextView state;
        /**
         * 订单价格
         */
        private TextView price;
        /**
         * 订单时间
         */
        private TextView time;
        /**
         * 收割者
         */
        private TextView actor;
        /**
         * 配图
         */
        private ImageView figure;
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(getApplicationContext(), DrawerArrowActivity.class);
                startActivity(intent);
                HistoryOrderActivity.this.finish();
                break;

            default:
                break;
        }
        return false;
    }

}
