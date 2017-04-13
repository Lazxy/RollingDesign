package com.practice.li.rollingdesign.common;

/**
 * Created by Lazxy on 2017/3/8.
 * 用到的常数管理接口
 */

public interface CommonConstants {

    interface Login {
        String EXTRA_WEB_TITLE = "extra_web_title";

        String EXTRA_WEB_LOAD_URL = "extra_web_load_url";

        String EXTRA_AUTHORIZE_CODE = "extra_authorize_code";

        int REQUEST_CODE_LOGIN = 0x1;

        int REQUEST_CODE_AUTHORIZE = 0x10;
    }

    interface Config {

        String RUN_GIF_AUTO = "config_run_gif";
    }

    interface Shots {
        String EXTRA_SHOT_DETAIL = "shot_entity";

        String EXTRA_SHOT_AUTHOR = "shot_author";

        String EXTRA_SHOT_URL = "shot_url";

        String EXTRA_SHOT_IMAGE_URL = "shot_image_url";

        String EXTRA_SHOT_GIF = "shot_gif";

        String VIEW_MODE = "view_mode";

        int VIEW_MODE_LARGE_WITH_INFO = 0x24;

        int VIEW_MODE_SMALL_WITH_INFO = 0x32;

        int VIEW_MODE_LARGE = 0x40;

        int VIEW_MODE_SMALL = 0x48;

        String[] SHOT_TAB_TITLE = {"简介", "评论"};
    }

    interface User {
        String CURRENT_USER = "current_user";

        String ACCESS_TOKEN = "access_token";

        String IS_FIRST_RUN = "is_first_run";

        String EXTRA_SHOULD_LOGIN = "should_login";

        String EXTRA_PROFILE_USER_ID = "profile_user_id";

        String EXTRA_PROFILE_AUTHOR = "profile_author";

        String Extra_PROFILE_TEAM_ID = "profile_team_Id";

        String USER_TYPE_PLAYER = "Player";

        String USER_TYPE_TEAM = "Team";

        String[] PROFILE_USER_TAB_TITLE = {"简介", "作品", "粉丝", "喜欢"};

        String[] PROFILE_TEAM_TAB_TITLE = {"简介", "成员", "作品"};
    }

    interface Bucket {
        String EXTRA_BUCKET_ID = "bucket_id";

        String EXTRA_BUCKET = "bucket";

        String EXTRA_BUCKET_TITLE = "bucket_title";

        String EXTRA_BUCKET_DESCRIPTION = "bucket_description";
    }

    interface Error {
        String ERROR_MESSAGE_CHECK_FOLLOW = "error_check_follow";

        String ERROR_MESSAGE_CHANGE_FOLLOW_STATUS = "error_change_follow_status";
    }

    interface Theme {
        String THEME_KEY = "theme_key";

        String THEME_PINK = "theme_pink";

        String THEME_BLUE = "theme_blue";

        String THEME_RED = "theme_red";

        String THEME_GREY = "theme_grey";

        String THEME_GREEN = "theme_green";

        int[][] STATE_CHECKED = {{-android.R.attr.state_enabled}, {android.R.attr.state_checked}, {}};
    }

}
