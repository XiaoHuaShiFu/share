package top.xiaohuashifu.share.pojo.do0;

import top.xiaohuashifu.share.pojo.group.Group;
import top.xiaohuashifu.share.pojo.group.GroupPost;
import top.xiaohuashifu.share.pojo.group.GroupPut;
import top.xiaohuashifu.share.validator.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

public class AdminLogDO {

	@Null(message = "INVALID_PARAMETER: The id must be null.", groups = {GroupPost.class})
	private Integer id;

	@NotNull(message = "INVALID_PARAMETER_IS_NULL: The adminId must be not null.",
			groups = {GroupPut.class})
	@Id(groups = {Group.class})
	private Integer adminId;

	@NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The content must be not blank.", groups = {GroupPost.class})
	@Size(message = "INVALID_PARAMETER_SIZE: The size of content must not be greater 200.", max = 200,
			groups = {Group.class})
	private String content;

	@Null(message = "INVALID_PARAMETER: The createTime must be null.", groups = {GroupPost.class, GroupPut.class})
	private Date createTime;

	@Null(message = "INVALID_PARAMETER: The updateTime must be null.", groups = {GroupPost.class, GroupPut.class})
	private Date updateTime;
	
	public AdminLogDO() {}

	public AdminLogDO(Integer id, Integer adminId, String content, Date createTime, Date updateTime) {
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
		return "AdminLogDO{" +
				"id=" + id +
				", adminId=" + adminId +
				", content='" + content + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
