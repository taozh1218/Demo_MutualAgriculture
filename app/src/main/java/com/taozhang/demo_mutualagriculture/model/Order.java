package com.taozhang.demo_mutualagriculture.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Description:Order结构体
 * Created by taozhang on 2016/1/15.
 * Company:Geowind,University of South China.
 * ContactQQ:962076337
 *
 * @updateAuthor taozhang
 * @updateDate 2016/1/15
 */
public class Order {

    private static final long serialVersionUID = 1L;
    public int id;// 主键
    private int num;// 数量
    private double commission;// 佣金
    private String state;// 状态״
    private Date endTime;// 截至时间
    private Date startTime;// 开始时间
    private int farmlandId;// 农田id
    private int agentId;// 经纪人id
    private int machineOwnerId;// 农机主id

    public static Order parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return Order.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Order parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Order order = new Order();
        //TODO:解析Order

        return order;
    }
}
