package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentCommentLikeDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The userId must be not null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer userId;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The shareCommentCommentId must be not null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer shareCommentCommentId;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.",
            groups = {GroupPost.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.",
            groups = {GroupPost.class})
    private Date updateTime;

    public ShareCommentCommentLikeDO() {
    }

    public ShareCommentCommentLikeDO(Integer id, Integer userId, Integer shareCommentCommentId, Date createTime,
                                     Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.shareCommentCommentId = shareCommentCommentId;
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

    public Integer getShareCommentCommentId() {
        return shareCommentCommentId;
    }

    public void setShareCommentCommentId(Integer shareCommentCommentId) {
        this.shareCommentCommentId = shareCommentCommentId;
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
        return "ShareCommentCommentLikeDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareCommentCommentId=" + shareCommentCommentId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
