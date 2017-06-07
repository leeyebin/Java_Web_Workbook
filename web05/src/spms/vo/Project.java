package spms.vo;

import java.util.Date;

public class Project {
	protected int no;
	protected String title;
	protected String content;
	protected Date startDate;
	protected Date endDate;
	protected int state;
	protected Date createdDate;
	protected String tags;
	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public Project setNo(int no) {
		this.no = no;
		return this;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public Project setTitle(String title) {
		this.title = title;
		return this;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public Project setContent(String content) {
		this.content = content;
		return this;
	}
	/**
	 * @return the starDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param starDate the starDate to set
	 */
	public Project setStartDate(Date starDate) {
		this.startDate = starDate;
		return this;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public Project setEndDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public Project setState(int state) {
		this.state = state;
		return this;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public Project setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		return this;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public Project setTags(String tags) {
		this.tags = tags;
		return this;
	}

	
}
