package top.xiaohuashifu.share.pojo.vo;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareImageVO {

    private Integer id;

    private Integer shareId;

    private String imageUrl;

    private Integer imageIndex;

    public ShareImageVO() {
    }

    public ShareImageVO(Integer id, Integer shareId, String imageUrl, Integer imageIndex) {
        this.id = id;
        this.shareId = shareId;
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
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

    @Override
    public String toString() {
        return "ShareImageVO{" +
                "id=" + id +
                ", shareId=" + shareId +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageIndex=" + imageIndex +
                '}';
    }
}
