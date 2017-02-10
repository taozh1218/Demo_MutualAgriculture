package com.taozhang.demo_mutualagriculture.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.taozhang.demo_mutualagriculture.adapter.NewsAdapter;
import com.taozhang.demo_mutualagriculture.bean.Task;
import com.taozhang.demo_mutualagriculture.model.News;
import com.taozhang.demo_mutualagriculture.model.User;
import com.taozhang.demo_mutualagriculture.service.MyService;
import com.taozhang.demo_mutualagriculture.util.DrawerArrowDrawable;
import com.taozhang.demo_mutualagriculture.util.ImageLoader;

import java.util.ArrayList;

import cn.smssdk.gui.ContactsPage;

import static android.view.Gravity.START;

/**
 * Des:主界面
 */
public class DrawerArrowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;
    private DrawerLayout mDrawer;
    /**
     * 顶部条左边的图片
     */
    private ImageView mImageView;
    public static boolean mloginState = false;//登录状态，需要在登录成功时候修改
    private static TextView mTv_arrowMenu_username;//用户名，需要在登录成功时候修改
    private ListView mListView;
    /**
     * 当前item的总数
     */
    private int currentTotal;
    private ImageView mImg_arrowMenu_user;
    private TextView mStyleButton;
    private NewsAdapter mAdapter;
    private int index = 0;

    //TODO:测试完后删除
    public ArrayList<News> test_lists = new ArrayList<News>();
    public User mUser_test;
    public News news_test;
    public TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        System.out.println("DrawerArrowLayout----------OnCreate");
        setContentView(R.layout.activity_drawer_arrow);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);//为侧滑菜单中的
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        System.out.println("onCreateOptionsMenu:---");
        return true;
    }

    /**
     * 监听ActionBar中的item
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //        Toast.makeText(getApplicationContext(),"onOptionsItemSelected:"+item.getTitle(),Toast.LENGTH_SHORT).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 监听侧滑菜单中的item
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        //        Toast.makeText(getApplication(),"onNavigationItemSelected:"+item.getTitle(),Toast.LENGTH_SHORT).show();

        if (id == R.id.nav_subRequest) {//申请收割
            // Handle the camera action
            intent = new Intent(getApplication(), ApplyOrderActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_orders) {//历史订单
            intent = new Intent(getApplication(), HistoryOrderActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_setting) {//设置
            intent = new Intent(getApplication(), MySettingActivity.class);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.nav_searchFriend) {//查看好友
            intent = new Intent(getApplication(), FriendsActivity.class);
            startActivity(intent);
            this.finish();

        } else if (id == R.id.nav_share_com) {//分享

        } else if (id == R.id.nav_send_com) {//发送

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void init() {
        //        mAdapter = new NewsAdapter(getApplicationContext(),this);
        mTestAdapter = new TestAdapter();
        System.out.println("DrawerArrowActivity--------init()");
        initData();
        //获取组件并添加监听
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);//抽屉的根布局DrawerLayout
        mImageView = (ImageView) findViewById(R.id.drawer_indicator);//抽屉的开关(左上角那个)
        final Resources resources = getResources();

        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.white));//设置画笔的颜色（那开关是画出来的）
        mImageView.setImageDrawable(drawerArrowDrawable);

        mImg_arrowMenu_user = (ImageView) findViewById(R.id.img_navView_header);//用户头像
        ImageLoader imageLoader = new ImageLoader();
        //        Drawable drawable = getResources().getDrawable(R.drawable.avatar_default);
        //        BitmapDrawable drawable1 = (BitmapDrawable) drawable;
        //        Bitmap bitmap = drawable1.getBitmap();
        //        Bitmap bitmap1 = imageLoader.toRoundCorner(bitmap, 2);
        Bitmap bmp = BitmapFactory.decodeResource(resources, R.drawable.avatar_default);
        Bitmap bitmap = imageLoader.toRoundCorner(bmp, 2);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        mImg_arrowMenu_user.setImageDrawable(bitmapDrawable);

        mTv_arrowMenu_username = (TextView) findViewById(R.id.tv_navView_header);//用户名
        mStyleButton = (TextView) findViewById(R.id.indicator_style);
        //        final TextView styleButton = (TextView) findViewById(R.id.indicator_style);//顶部右边的标签

        mListView = (ListView) findViewById(R.id.lv_drawerArrow_content);//获取listView
        View footerView = getLayoutInflater().inflate(R.layout.list_bottom, null);//listView的底部
        mListView.addFooterView(footerView);//添加footerView
        //        mListView.setAdapter(mAdapter);//设置适配器
        mListView.setAdapter(mTestAdapter);//设置适配器
        /***************************监听器**************************/
        //为listView添加监听
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * 状态改变监听
             * @param view
             * @param scrollState 状态
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && currentTotal == mAdapter.getCount()) {//滚动停止，并且currentTotal==adapter的大小
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && currentTotal == mTestAdapter.getCount()) {//滚动停止，并且currentTotal==adapter的大小
                    //TODO:
                    Task task = new Task();
                    task.setTaskId(Task.GET_NEWS);
                    MyService.addTask(task);
                    MyService homeService = new MyService();
                    new Thread(homeService).start();
                }
            }

            /**
             * 滑动监听
             * @param view
             * @param firstVisibleItem 第一个可见的item
             * @param visibleItemCount 可见的item数量
             * @param totalItemCount
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                System.out.println("DrawerArrowActivity------------onScroll------totalItemCount:" + totalItemCount);
                currentTotal = firstVisibleItem + visibleItemCount - 1;
                System.out.println("DrawerArrowActivity---------onScroll------currentTotal:" + currentTotal);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //                News news = mAdapter.arrayList_news.get(position);
                //TODO:测试完删除
                News news = test_lists.get(position);
                Toast.makeText(getApplicationContext(), "position:" + position + ",title:" + news.title, Toast.LENGTH_SHORT).show();
                NewsDetailActivity.news = news;
                Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });


        //用户手划
        mDrawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });

        //由图标触发
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果drawer已经打开，就关了
                if (mDrawer.isDrawerVisible(START)) {
                    mDrawer.closeDrawer(START);
                } else {//drawer已经关了，那么打开
                    mDrawer.openDrawer(START);
                }
            }
        });

        //顶部右边的标签

        //        styleButton.setOnClickListener(new View.OnClickListener() {
        //            boolean rounded = false;
        //
        //            @Override
        //            public void onClick(View v) {
        //                styleButton.setText(rounded
        //                        ? resources.getString(R.string.rounded)
        //                        : resources.getString(R.string.squared));
        //
        //                rounded = !rounded;
        //
        //                drawerArrowDrawable = new DrawerArrowDrawable(resources, rounded);
        //                drawerArrowDrawable.setParameter(offset);
        //                drawerArrowDrawable.setFlip(flipped);
        //                drawerArrowDrawable.setStrokeColor(resources.getColor(R.color.light_gray));
        //
        //                mImageView.setImageDrawable(drawerArrowDrawable);
        //            }
        //        });


        mImg_arrowMenu_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                //如果未登录
                if (!mloginState) {
                    //跳到登录界面
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    finish();
                } else {
                    //否则跳转到个人信息界面
                    intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    finish();
                }
                startActivity(intent);

            }
        });


    }

    /**
     * 监听器
     */
    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;
            switch (id) {
                case R.id.nav_searchFriend:
                    //打开通信录好友列表页面
                    ContactsPage contactsPage = new ContactsPage();
                    contactsPage.show(getApplicationContext());
                    break;
            }

        }
    };


    private void initData() {
        for (int i = 0; i < 15; i++) {
            News news = new News();
            User user = new User();
            news.title = "title" + index;
            news.author = "author" + index;
            news.time = "time" + index;
            news.text = "text" + index++;
            //            System.out.println(news.title+":"+news.author+":"+news.time+":"+news.time+":"+news.text);
            test_lists.add(news);
        }
    }

    private class TestAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (test_lists != null && test_lists.size() > 0) {
                return test_lists.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {//数据项
            if (test_lists != null && test_lists.size() > 0) {
                return test_lists.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            news_test = test_lists.get(position);
            mUser_test = news_test.mUser;//作者信息
            ViewHolder holder = null;
            if (holder == null) {
                //反射出item
                holder = new ViewHolder();
                convertView = getLayoutInflater().inflate(R.layout.list_item_homenews, null);//convertView就是listItem
                holder.title = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_title);
                holder.author = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_author);
                holder.time = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_timeAndWhere);
                holder.figure = (ImageView) convertView.findViewById(R.id.img_lvitem_homenews_figure);
                holder.text = (TextView) convertView.findViewById(R.id.tv_lvitem_homenews_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //设置内容
            holder.title.setText(news_test.title);
            holder.author.setText(news_test.author);
            holder.time.setText(news_test.time);
            holder.figure.setImageResource(R.drawable.avatar_default);//暂时用户头像设置为把配图
            //        new ImageLoader().showImageByAsyncTask(holder.figure, mUser.profile_image_url);//把头像重绘成圆形
            holder.text.setText(news_test.text);
            return convertView;
        }
    }

    static class ViewHolder {
        /**
         * 标题
         */
        private TextView title;
        /**
         * 作者
         */
        private TextView author;
        /**
         * 时间地点
         */
        private TextView time;
        /**
         * 配图
         */
        private ImageView figure;
        /**
         * 微博正文
         */
        private TextView text;
        /** 微博略缩图片 */
        //        private ImageView thumbnail_pic;
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //        System.out.println("DrawerArrowAct------onCreateView");
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onRestart() {
        //        System.out.println("onRestart----------");
        //        Toast.makeText(getApplicationContext(),"Restart",Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        //        System.out.println("onResume----------");
        //        Toast.makeText(getApplicationContext(),"Resume",Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onStart() {
        //        System.out.println("onStart----------");
        //        Toast.makeText(getApplicationContext(),"Onstart",Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onPostResume() {
        //        System.out.println("onPostResume----------");
        //        Toast.makeText(getApplicationContext(),"onPostResume",Toast.LENGTH_SHORT).show();
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        //        System.out.println("onPause----------");
        //        Toast.makeText(getApplicationContext(),"onPause",Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        //        System.out.println("onStop----------");
        //        Toast.makeText(getApplicationContext(),"onStop",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("注意")
                        .setMessage("确定要退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //1.Dalvik VM的本地方法
                                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                                System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出

                                //2. 任务管理器方法
                                // 首先要说明该方法运行在Android 1.5 API Level为3以上才可以，同时需要权限
                                // 系统会将，该包下的 ，所有进程，服务，全部杀掉，就可以杀干净了，要注意加上
                                //                                ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                                //                                am.restartPackage(getPackageName());

                                //3.  我们知道Android的窗口类提供了历史栈，
                                // 我们可以通过stack的原理来巧妙的实现，
                                // 这里我们在A窗口打开B窗口时在Intent中直接加入标志   Intent.FLAG_ACTIVITY_CLEAR_TOP，
                                // 这样开启B时将会清除该进程空间的所有Activity。 在A窗口中使用下面的代码调用B窗口  接下来在B窗口中需要退出时直接使用finish方法即可全部退出。(还有点问题，但是一般不建议这样做)
                                //                                Intent intent = new Intent();
                                //                                intent.setClass(Android123.this, CWJ.class);
                                //                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                                //                                startActivity(intent);

                                //4.自定义一个Actiivty 栈，道理同上，不过利用一个单例模式的Activity栈来管理所有Activity。并提供退出所有Activity的方法。代码如下：
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub  or do nothing
                            }
                        })
                        .show();
                break;

            default:
                break;
        }
        return false;
    }

}
