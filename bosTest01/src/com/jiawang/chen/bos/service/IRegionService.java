/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IRegionService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午11:22:27
 *@版本 
 */
public interface IRegionService {

	public void save(Region region);
	
	public void delete(Region region);
	
	public void saveBacth(List<Region> list);
	
	public void pageQuery(PageBean pageBean);

	/**
	 * 
	 *@时间 2017年2月16日 下午5:01:17
	 */
	public List<Region> findAll();

	/**
	 * 
	 *@时间 2017年2月16日 下午5:14:38
	 */
	public List<Region> findByQ(String q);

	/**
	 * 
	 *@时间 2017年2月17日 上午9:17:02
	 */
	public void update(Region region);
}
