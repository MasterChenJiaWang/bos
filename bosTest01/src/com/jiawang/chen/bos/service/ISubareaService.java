/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.Subarea;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: ISubareaService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月17日 上午9:31:44
 *@版本 
 */
public interface ISubareaService {

	public void add(Subarea subarea);
	public void edit(Subarea subarea);
	public void delete(Subarea subarea);
	public void pageQuery(PageBean pageBean);
	/**
	 * 
	 *@时间 2017年2月17日 上午10:06:12
	 */
	public List<Subarea> findAll();
	/**
	 * 
	 *@时间 2017年2月17日 下午4:08:29
	 */
	public List<Subarea> findListNotAssociation();
	
}
