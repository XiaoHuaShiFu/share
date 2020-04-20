package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentVO {

    private Integer id;

    private Integer userId;

    private Integer shareId;

    private String content;

    private Integer comments;

    private Integer likes;

    private Date commentTime;

    private UserVO user;

    public ShareCommentVO() {
    }

    public ShareCommentVO(Integer id, Integer userId, Integer shareId, String content, Integer comments, Integer likes,
                          Date commentTime, UserVO user) {
        this.id = id;
        this.userId = userId;
        this.shareId = shareId;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
        this.commentTime = commentTime;
        this.user = user;
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

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShareCommentVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareId=" + shareId +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                ", likes=" + likes +
                ", commentTime=" + commentTime +
                ", user=" + user +
                '}';
    }
}
