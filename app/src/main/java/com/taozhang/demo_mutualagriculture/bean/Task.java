package com.taozhang.demo_mutualagriculture.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description: 任务类
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private int taskId;

    private HashMap<String, Object> hashmap = new HashMap<String, Object>();

    /**获取新闻列表*/
    public static final int GET_NEWS = 1;
    /**获取订单列表*/
    public static final int GET_ORDERS = 2;
    /**发送订单*/
    public static final int SEND_ORDER_INFO = 3;
    /** 获取新闻详情 */
    public static final int GET_NEWS_DETAIL = 4;
    /** 获取订单详情 */
    public static final int GET_ORDER_DETAIL = 5;


    public Task() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Task(int task_Id, HashMap<String, Object> hashmap) {
        super();
        this.taskId = task_Id;
        this.hashmap = hashmap;
    }

    public int getTaskId() {
        return taskId;
    }
    public void setTaskId(int task_Id) {
        this.taskId = task_Id;
    }
    public HashMap<String, Object> getHashmap() {
        return hashmap;
    }
    public void setHashmap(HashMap<String, Object> hashmap) {
        this.hashmap = hashmap;
    }
}
