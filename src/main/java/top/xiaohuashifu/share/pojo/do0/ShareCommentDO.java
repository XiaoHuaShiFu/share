package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.", groups = {GroupPost.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.", groups = {GroupPost.class})
    @Id(groups = {Group.class})
    private Integer userId;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The shareId must be not null.", groups = {GroupPost.class})
    @Id(groups = {Group.class})
    private Integer shareId;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The content must be not blank.", groups = {GroupPost.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of content must not be greater 100.", max = 100,
            groups = {Group.class})
    private String content;

    @Null(message = "INVALID_PARAMETER: The comments must be null.", groups = {GroupPost.class})
    private Integer comments;

    @Null(message = "INVALID_PARAMETER: The likes must be null.", groups = {GroupPost.class})
    private Integer likes;

    @Null(message = "INVALID_PARAMETER: The commentTime must be null.", groups = {GroupPost.class})
    private Date commentTime;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.", groups = {GroupPost.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.", groups = {GroupPost.class})
    private Date updateTime;

    public ShareCommentDO() {
    }

    public ShareCommentDO(Integer id, Integer userId, Integer shareId, String content, Integer comments,
                          Integer likes, Date commentTime, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.shareId = shareId;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
        this.commentTime = commentTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
