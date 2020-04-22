package top.xiaohuashifu.share.pojo.query;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class ShareQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private Integer userId;

    private List<Integer> idList;

    /**
     * 模糊搜素
     */
    private String content;

    private Boolean open;

    // TODO: 2020/4/23 这里要做校验
    private String orderBy;

    public ShareQuery() {
    }

    public ShareQuery(Integer pageNum, Integer pageSize, Integer id, Integer userId, List<Integer> idList,
                      String content, Boolean open, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.id = id;
        this.userId = userId;
        this.idList = idList;
        this.content = content;
        this.open = open;
        this.orderBy = orderBy;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "ShareQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", userId=" + userId +
                ", idList=" + idList +
                ", content='" + content + '\'' +
                ", open=" + open +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
