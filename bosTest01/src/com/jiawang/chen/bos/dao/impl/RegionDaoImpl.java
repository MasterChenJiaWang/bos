/**
 * 
 */
package com.jiawang.chen.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jiawang.chen.bos.dao.IRegionDao;
import com.jiawang.chen.bos.dao.base.impl.BaseDaoImpl;
import com.jiawang.chen.bos.entity.Region;

/**
 *<p>标题: RegionDaoImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午10:23:40
 *@版本 
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	/* 
	 *
	 *2017年2月16日下午5:11:58
	 */
	@Override
	public List<Region> findByQ(String q) {
		String hql="FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
		return this.getHibernateTemplate().find(hql);
	}

	
}
