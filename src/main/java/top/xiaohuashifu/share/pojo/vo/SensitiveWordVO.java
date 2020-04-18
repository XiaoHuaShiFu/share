package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class SensitiveWordVO {

    private Integer id;

    private String sensitiveWord;

    private Date createTime;

    public SensitiveWordVO() {
    }

    public SensitiveWordVO(Integer id, String sensitiveWord, Date createTime) {
        this.id = id;
        this.sensitiveWord = sensitiveWord;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SensitiveWordVO{" +
                "id=" + id +
                ", sensitiveWord='" + sensitiveWord + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
