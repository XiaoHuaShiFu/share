package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentLikeVO {

    private Integer id;

    private Integer userId;

    private Integer shareCommentId;

    private Date createTime;

    public ShareCommentLikeVO() {
    }

    public ShareCommentLikeVO(Integer id, Integer userId, Integer shareCommentId, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.shareCommentId = shareCommentId;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ShareCommentLikeVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareCommentId=" + shareCommentId +
                ", createTime=" + createTime +
                '}';
    }
}
