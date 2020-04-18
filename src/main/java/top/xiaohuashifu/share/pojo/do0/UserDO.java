package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.constant.Gender;
import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.group.GroupPut;
import top.xiaohuashifu.share.validator.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UserDO {

    @Null(message = "INVALID_PARAMETER: The id must be null.",
            groups = {GroupPost.class})
    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The id must be not null.",
            groups = {GroupPut.class})
    @Id(groups = {Group.class})
    private Integer id;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The username must be not blank.",
            groups = {GroupPost.class})
    @Username(groups = {Group.class})
    private String username;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The password must be not blank.",
            groups = {GroupPost.class})
    @Password(groups = {Group.class})
    private String password;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The nickName must be not blank.",
            groups = {GroupPost.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of nickName must be between 4 to 20.",
            min = 4, max = 20,
            groups = {Group.class})
    private String nickName;

    @NotNull(message = "INVALID_PARAMETER_IS_NULL: The gender must be not null.",
            groups = {GroupPost.class})
    private Gender gender;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The phone must be not blank.",
            groups = {GroupPost.class})
    @Phone(groups = {Group.class})
    private String phone;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The email must be not blank.",
            groups = {GroupPost.class})
    @Email(groups = {Group.class})
    private String email;

    @Null(message = "INVALID_PARAMETER: The avatarUrl must be null.",
            groups = {GroupPost.class})
    @Url(groups = {Group.class})
    private String avatarUrl;

    @Null(message = "INVALID_PARAMETER: The followers must be null.",
            groups = {GroupPost.class})
    private Integer followers;

    @Null(message = "INVALID_PARAMETER: The followings must be null.",
            groups = {GroupPost.class})
    private Integer followings;

    @Null(message = "INVALID_PARAMETER: The shares must be null.",
            groups = {GroupPost.class})
    private Integer shares;

    @Null(message = "INVALID_PARAMETER: The comments must be null.",
            groups = {GroupPost.class})
    private Integer comments;

    @Null(message = "INVALID_PARAMETER: The collections must be null.",
            groups = {GroupPost.class})
    private Integer collections;

    @Null(message = "INVALID_PARAMETER: The available must be null.",
            groups = {GroupPost.class})
    private Boolean available;

    @Null(message = "INVALID_PARAMETER: The createTime must be null.",
            groups = {GroupPost.class, GroupPut.class})
    private Date createTime;

    @Null(message = "INVALID_PARAMETER: The updateTime must be null.",
            groups = {GroupPost.class, GroupPut.class})
    private Date updateTime;

    public UserDO() {
    }

    public UserDO( Integer id, String username, String password, String nickName, Gender gender, String phone,
                   String email, String avatarUrl, Integer followers, Integer followings, Integer shares,
                   Integer comments, Integer collections, Boolean available, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.followers = followers;
        this.followings = followings;
        this.shares = shares;
        this.comments = comments;
        this.collections = collections;
        this.available = available;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowings() {
        return followings;
    }

    public void setFollowings(Integer followings) {
        this.followings = followings;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getCollections() {
        return collections;
    }

    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
        return "UserDO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", followers=" + followers +
                ", followings=" + followings +
                ", shares=" + shares +
                ", comments=" + comments +
                ", collections=" + collections +
                ", available=" + available +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
