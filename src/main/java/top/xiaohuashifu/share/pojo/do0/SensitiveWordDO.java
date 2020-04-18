package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.group.GroupPut;
import top.xiaohuashifu.share.validator.annotation.Id;

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
public class SensitiveWordDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.",
            groups = {GroupPost.class})
    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.",
            groups = {GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The sensitiveWord must be not blank.",
            groups = {GroupPost.class})
    private String sensitiveWord;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.",
            groups = {GroupPost.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.",
            groups = {GroupPost.class})
    private Date updateTime;

    public SensitiveWordDO() {
    }

    public SensitiveWordDO(Integer id, String sensitiveWord, Date createTime, Date updateTime) {
        this.id = id;
        this.sensitiveWord = sensitiveWord;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SensitiveWordDO{" +
                "id=" + id +
                ", sensitiveWord='" + sensitiveWord + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
