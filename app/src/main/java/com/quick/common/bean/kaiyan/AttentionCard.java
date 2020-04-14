package com.quick.common.bean.kaiyan;

import com.quick.common.app.BaseBean;

/**
 * Created by zhaolong.
 * Description:
 * Date: 2020/4/10 0010 15:52
 */
public class AttentionCard extends BaseBean {
    public String avatarUrl;

    public String issuerName;

    public long releaseTime;

    public String title;

    public String description;

    public String coverUrl;

    public String blurredUrl;

    public String playUrl;

    public String category;

    public String authorDescription;

    public int videoId ;

    // 点赞
    public int collectionCount;

    // 分享
    public int shareCount;

    // 评论
    public int replyCount;

    // 收藏
    public int realCollectionCount;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBlurredUrl() {
        return blurredUrl;
    }

    public void setBlurredUrl(String blurredUrl) {
        this.blurredUrl = blurredUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getRealCollectionCount() {
        return realCollectionCount;
    }

    public void setRealCollectionCount(int realCollectionCount) {
        this.realCollectionCount = realCollectionCount;
    }
}
