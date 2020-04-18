package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupDelete;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UserFollowerDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The followederId must be not null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer followederId;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The followerId must be not null.",
            groups = {GroupPost.class, GroupDelete.class})
    @Id(groups = {Group.class})
    private Integer followerId;

    private Date createTime;

    private Date updateTime;

    public UserFollowerDO() {
    }

    public UserFollowerDO(Integer id, Integer followederId, Integer followerId, Date createTime, Date updateTime) {
        this.id = id;
        this.followederId = followederId;
        this.followerId = followerId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFollowederId() {
        return followederId;
    }

    public void setFollowederId(Integer followederId) {
        this.followederId = followederId;
    }

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
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
        return "UserFollowerDO{" +
                "id=" + id +
                ", followederId=" + followederId +
                ", followerId=" + followerId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
