/**
 * 
 */
package com.jiawang.chen.bos.web.utils;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 *<p>标题: PageBean </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午6:45:14
 *@版本 
 */
public class PageBean {
	private int currentPage;//当前页面
	private int pageSize;//每页显示数量
	private int total=0;//总记录数
	private DetachedCriteria detachedCriteria;//离线查询对象
	private List rows;//当前页需要展示的数据集合
	/**
	 * @return currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage 要设置的 currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize 要设置的 pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total 要设置的 total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * @return detachedCriteria
	 */
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	/**
	 * @param detachedCriteria 要设置的 detachedCriteria
	 */
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	/**
	 * @return rows
	 */
	public List getRows() {
		return rows;
	}
	/**
	 * @param rows 要设置的 rows
	 */
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
