package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCommentCommentLikeVO {

    private Integer id;

    private Integer userId;

    private Integer shareCommentCommentId;

    private Date createTime;

    public ShareCommentCommentLikeVO() {
    }

    public ShareCommentCommentLikeVO(Integer id, Integer userId, Integer shareCommentCommentId, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.shareCommentCommentId = shareCommentCommentId;
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

    @Override
    public String toString() {
        return "ShareCommentCommentLikeVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareCommentCommentId=" + shareCommentCommentId +
                ", createTime=" + createTime +
                '}';
    }
}
