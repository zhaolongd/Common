package com.quick.common.config;

import com.quick.common.BuildConfig;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2019/12/6 17:09
 */
public interface HttpConfig {
    String BASE_URL = BuildConfig.URL;
    String BANNER = BASE_URL + "/banner/json"; //首页banner
    String HOT_KEY_LIST = BASE_URL + "/hotkey/json";
    String SEARCH = BASE_URL + "/article/query/%d/json"; //按照作者昵称搜索文章
    String ARTICLE_LIST = BASE_URL + "/article/list/%d/json";
    String KNOWLEDGE_LIST = BASE_URL + "/tree/json"; //体系数据
    String NAVI_LIST = BASE_URL + "/navi/json"; //搜索热词
    String WX_ARTICLE_CHAPTER = BASE_URL + "/wxarticle/chapters/json" ; //获取公众号列表 TAB
    String WX_ARTICLE_LIST = BASE_URL + "/wxarticle/list/%d/%d/json"; //获取某个公众号中历史文章
    String PROJECT_CHAPTER = BASE_URL + "/project/tree/json"; //项目分类
    String PROJECT_ARTICLE_LIST = BASE_URL + "/project/list/%d/json"; //获取某个分类下项目列表数据
    String USER_ARTICLE_LIST = BASE_URL + "/user_article/list/%d/json";
    //开眼视频api
    String KAIYAN_RECOMMEND = "http://baobab.kaiyanapp.com/api/v7/community/tab/rec"; //开眼视频推荐
    String KAIYAN_ATTENTION = "http://baobab.kaiyanapp.com/api/v6/community/tab/follow"; //开眼视频关注
}
