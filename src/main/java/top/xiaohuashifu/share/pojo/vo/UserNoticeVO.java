package top.xiaohuashifu.share.pojo.vo;

import top.xiaohuashifu.share.constant.UserNoticeType;

import java.util.Date;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 */
public class UserNoticeVO {

    private Integer id;

    private Integer userId;

    private UserNoticeType type;

    private String content;

    private String keyValue;

    private Date noticeTime;

    public UserNoticeVO() {
    }

    public UserNoticeVO(Integer id, Integer userId, UserNoticeType type, String content, String keyValue,
                        Date noticeTime) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.content = content;
        this.keyValue = keyValue;
        this.noticeTime = noticeTime;
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

    public UserNoticeType getType() {
        return type;
    }

    public void setType(UserNoticeType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    @Override
    public String toString() {
        return "UserNoticeVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", keyValue='" + keyValue + '\'' +
                ", noticeTime=" + noticeTime +
                '}';
    }
}
