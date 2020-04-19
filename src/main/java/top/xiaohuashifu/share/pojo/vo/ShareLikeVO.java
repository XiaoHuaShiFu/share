package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareLikeVO {

    private Integer id;

    private Integer userId;

    private Integer shareId;

    private Date createTime;

    public ShareLikeVO() {
    }

    public ShareLikeVO(Integer id, Integer userId, Integer shareId, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.shareId = shareId;
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

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "ShareLikeVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareId=" + shareId +
                ", createTime=" + createTime +
                '}';
    }
}
