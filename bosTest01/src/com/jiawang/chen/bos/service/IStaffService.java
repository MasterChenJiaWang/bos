/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.entity.Staff;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IStaffService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午7:01:32
 *@版本 
 */
public interface IStaffService {

	public void save(Staff staff);
	
	public void update(Staff staff);
	
	public void delete(Staff staff);
	
	public void restoreBatch(String ids);
	
	public void pageQuery(PageBean pageBean);
	
	public void deleteBatch(String ids);
	
	public Staff findById(String id);
	
	public List<Staff> findNotDelete();


}
