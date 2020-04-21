package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentCommentVO {

    private Integer id;

    private Integer userId;

    private Integer shareCommentId;

    private Integer parentShareCommentCommentId;

    private String content;

    private Integer likes;

    private Date commentTime;

    private UserVO user;

    private UserVO parentShareCommentCommentUser;

    public ShareCommentCommentVO() {
    }

    public ShareCommentCommentVO(Integer id, Integer userId, Integer shareCommentId, Integer parentShareCommentCommentId,
                                 String content, Integer likes, Date commentTime, UserVO user,
                                 UserVO parentShareCommentCommentUser) {
        this.id = id;
        this.userId = userId;
        this.shareCommentId = shareCommentId;
        this.parentShareCommentCommentId = parentShareCommentCommentId;
        this.content = content;
        this.likes = likes;
        this.commentTime = commentTime;
        this.user = user;
        this.parentShareCommentCommentUser = parentShareCommentCommentUser;
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

    public Integer getShareCommentId() {
        return shareCommentId;
    }

    public void setShareCommentId(Integer shareCommentId) {
        this.shareCommentId = shareCommentId;
    }

    public Integer getParentShareCommentCommentId() {
        return parentShareCommentCommentId;
    }

    public void setParentShareCommentCommentId(Integer parentShareCommentCommentId) {
        this.parentShareCommentCommentId = parentShareCommentCommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public UserVO getParentShareCommentCommentUser() {
        return parentShareCommentCommentUser;
    }

    public void setParentShareCommentCommentUser(UserVO parentShareCommentCommentUser) {
        this.parentShareCommentCommentUser = parentShareCommentCommentUser;
    }

    @Override
    public String toString() {
        return "ShareCommentCommentVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareCommentId=" + shareCommentId +
                ", parentShareCommentCommentId=" + parentShareCommentCommentId +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", commentTime=" + commentTime +
                ", user=" + user +
                ", parentShareCommentCommentUser=" + parentShareCommentCommentUser +
                '}';
    }
}
