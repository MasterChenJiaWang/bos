package com.jiawang.chen.bos.crm;

import java.util.List;

import cn.jiawang.chen.crm.entity.Customer;



// 瀹㈡埛鏈嶅姟鎺ュ彛 
public interface CustomerService {
		// 来关联定区客户
		public List<Customer> findnoassociationCustomers();

		// 查询未关联指定定区客户
		public List<Customer> findhasassociationCustomers(String decidedZoneId);

		//将未关联定区客户关联到定区上
		public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId);
		
		//根据手机号查询客户信息
		public Customer findCustomerByPhonenumber(String phonenumber);
		
		//根据区间地址查询定区id
		public String findDecidedzoneIdByPickaddress(String address);
}
