package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareCollectionVO {

    private Integer id;

    private Integer userId;

    private Integer shareId;

    private Date createTime;

    private ShareVO share;

    public ShareCollectionVO() {
    }

    public ShareCollectionVO(Integer id, Integer userId, Integer shareId, Date createTime, ShareVO share) {
        this.id = id;
        this.userId = userId;
        this.shareId = shareId;
        this.createTime = createTime;
        this.share = share;
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

    public ShareVO getShare() {
        return share;
    }

    public void setShare(ShareVO share) {
        this.share = share;
    }

    @Override
    public String toString() {
        return "ShareCollectionVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", shareId=" + shareId +
                ", createTime=" + createTime +
                ", share=" + share +
                '}';
    }
}
