package top.xiaohuashifu.share.pojo.query;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class AdminLogQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Integer id;

    private Integer adminId;

    private List<Integer> idList;

    /**
     * 模糊搜素
     */
    private String content;

    public AdminLogQuery() {
    }

    public AdminLogQuery(Integer pageNum, Integer pageSize, Integer id, Integer adminId, List<Integer> idList,
                         String content) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.id = id;
        this.adminId = adminId;
        this.idList = idList;
        this.content = content;
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

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    @Override
    public String toString() {
        return "AdminLogQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", id=" + id +
                ", adminId=" + adminId +
                ", idList=" + idList +
                ", content='" + content + '\'' +
                '}';
    }
}
