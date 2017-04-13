package com.practice.li.rollingdesign.event;

import java.util.List;

/**
 * Created by Lazxy on 2017/3/18.
 * 收藏选择事件
 */
public class EventSelectBucket {

    public List<Integer> bucketsId;//所选择的收藏夹ID

    public EventSelectBucket(List<Integer> buckets) {
        this.bucketsId = buckets;
    }
}
