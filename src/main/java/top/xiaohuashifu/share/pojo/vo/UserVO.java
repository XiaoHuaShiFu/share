package top.xiaohuashifu.share.pojo.vo;

import top.xiaohuashifu.share.constant.Gender;


/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UserVO {

    private Integer id;

    private String username;

    private String nickName;

    private Gender gender;

    private String phone;

    private String email;

    private String avatarUrl;

    private Integer followers;

    private Integer followings;

    private Integer shares;

    private Integer comments;

    private Integer collections;

    private Boolean available;

    public UserVO() {
    }

    public UserVO(Integer id, String username, String nickName, Gender gender, String phone, String email,
                  String avatarUrl, Integer followers, Integer followings, Integer shares, Integer comments,
                  Integer collections, Boolean available) {
        this.id = id;
        this.username = username;
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

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
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
                '}';
    }
}
