package top.xiaohuashifu.share.pojo.vo;

import java.util.Date;

public class AdminLogVO {

	private Integer id;

	private Integer adminId;

	private String content;

	private Date createTime;

	private Date updateTime;

	public AdminLogVO() {}

	public AdminLogVO(Integer id, Integer adminId, String content, Date createTime, Date updateTime) {
		this.id = id;
		this.adminId = adminId;
		this.content = content;
		this.createTime = createTime;
		this.updateTime = updateTime;
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
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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
		return "AdminLogVO{" +
				"id=" + id +
				", adminId=" + adminId +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
