package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.group.GroupPut;
import top.xiaohuashifu.share.validator.annotation.Id;
import top.xiaohuashifu.share.validator.annotation.Url;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareImageDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.", groups = {GroupPost.class})
    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.", groups = {GroupPut.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The shareId must be not null.",
            groups = {GroupPost.class, GroupPut.class})
    @Id(groups = {Group.class})
    private Integer shareId;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The imageUrl must be not blank.",
            groups = {GroupPost.class, GroupPut.class})
    @Url(groups = {Group.class})
    private String imageUrl;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The imageIndex must be not null.",
            groups = {GroupPost.class, GroupPut.class})
    private Integer imageIndex;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.", groups = {GroupPost.class, GroupPut.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.", groups = {GroupPost.class, GroupPut.class})
    private Date updateTime;

    public ShareImageDO() {
    }

    public ShareImageDO(Integer id, Integer shareId, String imageUrl, Integer imageIndex, Date createTime,
                        Date updateTime) {
        this.id = id;
        this.shareId = shareId;
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(Integer imageIndex) {
        this.imageIndex = imageIndex;
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
        return "ShareImageDO{" +
                "id=" + id +
                ", shareId=" + shareId +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageIndex=" + imageIndex +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
