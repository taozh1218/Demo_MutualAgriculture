package com.taozhang.demo_mutualagriculture.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.taozhang.demo_mutualagriculture.bean.Task;
import com.taozhang.demo_mutualagriculture.model.Order;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Description:
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class MyService extends Service implements Runnable {

    private boolean isRunning = true;
    /**
     * 存储任务的队列
     */
    public static Queue<Task> tasks = new LinkedList<Task>();

    public static void addTask(Task task) {
        tasks.add(task);
    }

    @Override
    public void run() {
        Task task = null;
        while (isRunning) {
            if (tasks != null & tasks.size() > 0) {// 如果有任务，因此要先添加任务才能创建Service
                System.out.println("----Service中有任务---：" + tasks.toString());
                // 做任务，并移除
                task = tasks.poll();
                doTask(task);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 处理任务
     *
     * @param task
     */
    private void doTask(Task task) {
        if (task != null) {
            int taskId = task.getTaskId();

            switch (taskId) {
                case Task.GET_NEWS:
                    getNews();
                    break;
                case Task.GET_ORDERS:
                    getOrders();
                    break;
                case Task.GET_ORDER_DETAIL:
                    //                    HashMap<String, Object> hashmap = task.getHashmap();
                    //                    Status status = (Status) hashmap.get("status");
                    //                    System.out.println("------HomeService------status根据微博id获取评论"+status.toString());
                    //                    String id = status.id;
                    //                    getCommentInfo(id);
                    HashMap<String, Object> hashmap = task.getHashmap();
                    Order order = (Order) hashmap.get("order");
                    getOrderDetailById(order.id);
                    break;
                case Task.SEND_ORDER_INFO:
                    HashMap<String, Object> hashmap_send = task.getHashmap();
                    Order order_send = (Order) hashmap_send.get("order");
                    sendOrderDetail(order_send);
                default:
                    break;
            }
        }
    }

    /**
     * 发送订单信息到服务器
     * @param order 订单
     */
    private void sendOrderDetail(Order order) {
        //TODO

    }

    /**
     * 获取新闻列表
     */
    private void getNews() {
        //TODO
        //        statusesAPI.friendsTimeline(0l, 0l, 20, 1, false, 0, false, listener);
    }

    /**
     * 获取订单列表
     */
    private void getOrders() {
        //TODO
    }

    /**
     * 根据订单id获取订单详情
     * @param id 订单编号
     */
    private void getOrderDetailById(int id) {

    }

    /**
     * 根据订单id获取订单详情
     * @param ids 订单编号
     */
    private void getOrderDetailById(String ids){
        int id = Integer.parseInt(ids);
        getOrderDetailById(id);
    }



}

