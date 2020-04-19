package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.group.GroupPut;
import top.xiaohuashifu.share.validator.annotation.*;

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
public class ShareDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.", groups = {GroupPost.class})
    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.", groups = {GroupPut.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.", groups = {GroupPost.class})
    @Id(groups = {Group.class})
    private Integer userId;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The content must be not blank.", groups = {GroupPost.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of content must not be greater 300.", max = 300,
            groups = {Group.class})
    private String content;

    @Null(message = "INVALID_PARAMETER: The views must be null.", groups = {GroupPost.class})
    private Integer views;

    @Null(message = "INVALID_PARAMETER: The collections must be null.", groups = {GroupPost.class})
    private Integer collections;

    @Null(message = "INVALID_PARAMETER: The comments must be null.", groups = {GroupPost.class})
    private Integer comments;

    @Null(message = "INVALID_PARAMETER: The likes must be null.", groups = {GroupPost.class})
    private Integer likes;

    @NotNull(message = "INVALID_PARAMETER: The open must not be null.", groups = {GroupPost.class})
    private Boolean open;

    @Null(message = "INVALID_PARAMETER: The shareTime must be null.", groups = {GroupPost.class, GroupPut.class})
    private Date shareTime;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.", groups = {GroupPost.class, GroupPut.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.", groups = {GroupPost.class, GroupPut.class})
    private Date updateTime;

    public ShareDO() {
    }

    public ShareDO(Integer id, Integer userId, String content, Integer views, Integer collections, Integer comments,
                   Integer likes, Boolean open, Date shareTime, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.views = views;
        this.collections = collections;
        this.comments = comments;
        this.likes = likes;
        this.open = open;
        this.shareTime = shareTime;
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
        return "ShareDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", views=" + views +
                ", collections=" + collections +
                ", comments=" + comments +
                ", likes=" + likes +
                ", open=" + open +
                ", shareTime=" + shareTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
