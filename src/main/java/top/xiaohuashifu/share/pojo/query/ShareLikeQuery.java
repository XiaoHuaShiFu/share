package top.xiaohuashifu.share.pojo.query;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareLikeQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private Integer userId;

    private List<Integer> idList;

    private Integer shareId;

    public ShareLikeQuery() {
    }

    public ShareLikeQuery(Integer pageNum, Integer pageSize, Integer id, Integer userId, List<Integer> idList,
                          Integer shareId) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.id = id;
        this.userId = userId;
        this.idList = idList;
        this.shareId = shareId;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
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

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    @Override
    public String toString() {
        return "ShareLikeQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", userId=" + userId +
                ", idList=" + idList +
                ", shareId=" + shareId +
                '}';
    }
}
