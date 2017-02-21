/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.IRegionDao;
import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.service.IRegionService;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: RegionServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午11:22:59
 *@版本 
 */
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{

	@Resource
	private IRegionDao regionDao;
	/* 
	 *
	 *2017年2月16日上午11:35:06
	 */
	@Override
	public void save(Region region) {
		
		regionDao.save(region);
	}

	/* 
	 *
	 *2017年2月16日上午11:35:06
	 */
	@Override
	public void delete(Region region) {
		regionDao.delete(region);
	}

	/* 
	 *
	 *2017年2月16日上午11:39:39
	 */
	@Override
	public void saveBacth(List<Region> list) {
		regionDao.batchSave(list);
	}

	/* 
	 *
	 *2017年2月16日下午1:30:28
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		
		regionDao.pageQuery(pageBean);
	}

	/* 
	 *
	 *2017年2月16日下午5:01:26
	 */
	@Override
	public List<Region> findAll() {
	   return	regionDao.find();
		
	}

	/* 
	 *
	 *2017年2月16日下午5:14:46
	 */
	@Override
	public List<Region> findByQ(String q) {
		return regionDao.findByQ(q);
		
	}

	/* 
	 *
	 *2017年2月17日上午9:17:30
	 */
	@Override
	public void update(Region region) {
		regionDao.update(region);
	}
	
}
