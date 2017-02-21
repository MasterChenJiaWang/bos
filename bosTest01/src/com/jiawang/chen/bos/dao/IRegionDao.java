/**
 * 
 */
package com.jiawang.chen.bos.dao;

import java.util.List;

import com.jiawang.chen.bos.dao.base.IBaseDao;
import com.jiawang.chen.bos.entity.Region;

/**
 *<p>标题: IRegionDao </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午10:22:52
 *@版本 
 */
public interface IRegionDao extends IBaseDao<Region> {

	//模糊查询
	public List<Region> findByQ(String q);
}
