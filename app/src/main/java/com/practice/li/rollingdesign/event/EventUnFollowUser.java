package com.practice.li.rollingdesign.event;

/**
 * Created by Lazxy on 2017/3/27.
 * 取关事件
 */

public class EventUnFollowUser {

    public int position;//关注者在列表中的位置
    public int followingId;//关注者ID

    public EventUnFollowUser(int position, int followingId) {
        this.position = position;
        this.followingId = followingId;
    }
}
