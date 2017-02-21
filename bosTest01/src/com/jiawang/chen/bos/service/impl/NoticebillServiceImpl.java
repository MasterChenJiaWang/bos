/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.jiawang.chen.bos.crm.CustomerService;
import com.jiawang.chen.bos.dao.IDecidedzoneDao;
import com.jiawang.chen.bos.dao.INoticebillDao;
import com.jiawang.chen.bos.dao.IWorkbillDao;
import com.jiawang.chen.bos.entity.Decidedzone;
import com.jiawang.chen.bos.entity.Noticebill;
import com.jiawang.chen.bos.entity.Staff;
import com.jiawang.chen.bos.entity.Workbill;
import com.jiawang.chen.bos.service.INoticebillService;

/**
 *<p>标题: NoticebillServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午9:19:02
 *@版本 
 */
@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{

	@Resource
	private INoticebillDao noticebillDao;
	@Resource
	private CustomerService customerService;
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private IWorkbillDao workbillDao;
	/* 
	 *保存业务通知单，尝试自动分单
	 *2017年2月20日下午9:19:34
	 */
	@Override
	public void save(Noticebill noticebill) {
		noticebillDao.save(noticebill);
		//获取取件地址
		String pickaddress = noticebill.getPickaddress();
		//根据取件地址查询定区id---从crm服务查询 
		String dId = customerService.findDecidedzoneIdByPickaddress(pickaddress);
		if(dId != null){
					//查询到定区id，可以自动分单
					Decidedzone decidedzone = decidedzoneDao.get(dId);
					Staff staff = decidedzone.getStaff();
					noticebill.setStaff(staff);//业务通知单关联匹配到的取派员
					noticebill.setOrdertype("自动");//分单类型
					//需要为取派员创建一个工单
					Workbill workbill = new Workbill();
					workbill.setAttachbilltimes(0);//追单次数
					workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单创建的时间
					workbill.setNoticebill(noticebill);//工单关联业务通知单
					workbill.setPickstate("未取件");//取件状态
					workbill.setRemark(noticebill.getRemark());//备注
					workbill.setStaff(staff);//工单关联取派员
					workbill.setType("新单");
					workbillDao.save(workbill);//保存工单
					//调用短信平台服务，给取派员发送短信
				}else{
					//没有查询到定区id，转为人工分单
					noticebill.setOrdertype("人工");
		}
	}
}
