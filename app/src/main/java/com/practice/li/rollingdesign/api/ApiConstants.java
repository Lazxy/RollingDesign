package com.practice.li.rollingdesign.api;

/**
 * Created by Lazxy on 2017/3/5.
 */

public interface ApiConstants {
    interface Url {

        String BASE_URL = "https://api.dribbble.com/v1/";

        String OAUTH_URL = "https://dribbble.com/oauth/";

        String REDIRECT_URL = "https://dribbble.com/";
    }

    interface Path {

        String AUTHORIZE = "authorize";

        String TOKEN = "token";

        String SHOTS = "shots";

        String SHOTS_DETAIL = "shots/{id}";

        String SHOTS_COMMENTS = "shots/{id}/comments";

        String SHOTS_COMMENTS_DETAIL = "shots/{shots}/comments/{id}";

        String SHOTS_CREATE_COMMENTS = "shots/{shot}/comments";

        String SHOTS_COMMENTS_LIKE = "shots/{shot}/comments/{id}/like";

        String SHOTS_LIKE = "shots/{id}/like";

        String SEARCH = "search";

        String USER = "user";

        String USER_FOLLOWERS = "users/{id}/followers";

        String CURRENT_USER_FOLLOWING= "user/following";

        String USER_FOLLOW= "users/{id}/follow";

        String USER_FOLLOWING = "users/{id}/following";

        String CHECK_USER_FOLLOWING="user/following/{id}";

        String USER_SHOTS = "users/{id}/shots";

        String USER_LIKE_SHOTS = "users/{id}/likes";

        String USER_BUCKETS="users/{id}/buckets";

        String CURRENT_USER_BUCKETS = "user/buckets";

        String CREATE_BUCKET="buckets";

        String MANAGE_BUCKET="buckets/{id}";

        String BUCKETS_DETAIL = "shots/{id}/buckets";

        String ADD_SHOTS_TO_BUCKETS = "buckets/{id}/shots";

        String SHOTS_FROM_BUCKET="buckets/{id}/shots";

        String USER_TEAM = "users/{id}/teams";

        String CURRENT_USER_TEAM = "user/teams";

        String TEAM_SHOTS= "teams/{id}/shots";

        String TEAM_MEMBERS= "teams/{id}/members";
    }

    interface ParamKey {

        String CLIENT_ID = "client_id";

        String CLIENT_SECRET = "client_secret";

        String REDIRECT_URI = "redirect_uri";

        String SCOPE = "scope";

        String STATE = "state";

        String CODE = "code";

        String TYPE = "list";

        String TIME_FRAME = "timeframe";

        String SORT = "sort";

        String PAGE = "page";

        String PER_PAGE = "per_page";

        String SEARCH_KEY = "q";

        String BODY = "body";

        String SHOTS_ID = "shot_id";

        String BUCKET_NAME="name";

        String BUCKET_DESCRIPTION="description";
    }

    interface ParamValue {

        String CLIENT_ID = "d6b8c242643ae825c0056550d4f78a65065dacac0e79235a71a7a51ee069b7ba";

        String CLIENT_SECRET = "ae11777201efe6ac96c95a93c1d853fb0d407763695781acbd195c91de41b08e";

        String TOKEN = "5acfc8ea6f4a554c438a8ff67def6ec948122148bb79e7d461840f0e80c9a2b6";

        String REDIRECT_URI = "https://dribbble.com/";

        String SCOPE = "public write comment upload";

        String STATE = "lazxy";

        int PAGE_SIZE = 20;

        int SEARCH_PAGE_SIZE = 10;

        int TIME_OUT_SECONDS=15;
        /**
         * 类型，默认返回所有类型
         */
        String[] LIST_VALUES = {"", "debuts","teams", "playoffs", "rebounds", "animated"};

        /**
         * 排序，默认返回综合排序
         */
        String[] SORT_VALUES = {"", "recent", "views", "comments"};

        /**
         * 时间段，默认返回最新
         */
        String[] TIME_VALUES = {"", "week", "month", "year", "ever"};
    }

}
