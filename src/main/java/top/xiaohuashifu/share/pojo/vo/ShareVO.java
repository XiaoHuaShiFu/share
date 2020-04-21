package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareVO {

    private Integer id;

    private Integer userId;

    private String content;

    private Integer views;

    private Integer collections;

    private Integer comments;

    private Integer likes;

    private Boolean open;

    private Date shareTime;

    private UserVO user;

    private List<ShareImageVO> shareImageList;

    /**
     * 已经观看
     */
    private Boolean viewed;

    /**
     * 已经收藏
     */
    private Boolean collected;

    /**
     * 已经点赞
     */
    private Boolean liked;

    public ShareVO() {
    }

    public ShareVO(Integer id, Integer userId, String content, Integer views, Integer collections, Integer comments,
                   Integer likes, Boolean open, Date shareTime, UserVO user, List<ShareImageVO> shareImageList,
                   Boolean viewed, Boolean collected, Boolean liked) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.views = views;
        this.collections = collections;
        this.comments = comments;
        this.likes = likes;
        this.open = open;
        this.shareTime = shareTime;
        this.user = user;
        this.shareImageList = shareImageList;
        this.viewed = viewed;
        this.collected = collected;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public List<ShareImageVO> getShareImageList() {
        return shareImageList;
    }

    public void setShareImageList(List<ShareImageVO> shareImageList) {
        this.shareImageList = shareImageList;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Boolean getCollected() {
        return collected;
    }

    public void setCollected(Boolean collected) {
        this.collected = collected;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "ShareVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", collections=" + collections +
                ", comments=" + comments +
                ", likes=" + likes +
                ", open=" + open +
                ", shareTime=" + shareTime +
                ", user=" + user +
                ", shareImageList=" + shareImageList +
                ", viewed=" + viewed +
                ", collected=" + collected +
                ", liked=" + liked +
                '}';
    }
}
