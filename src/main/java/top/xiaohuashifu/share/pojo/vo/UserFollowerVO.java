package top.xiaohuashifu.share.pojo.vo;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UserFollowerVO {

    private Integer id;

    private Integer followederId;

    private Integer followerId;

    public UserFollowerVO() {
    }

    public UserFollowerVO(Integer id, Integer followederId, Integer followerId) {
        this.id = id;
        this.followederId = followederId;
        this.followerId = followerId;
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

    @Override
    public String toString() {
        return "UserFollowerVO{" +
                "id=" + id +
                ", followederId=" + followederId +
                ", followerId=" + followerId +
                '}';
    }
}
