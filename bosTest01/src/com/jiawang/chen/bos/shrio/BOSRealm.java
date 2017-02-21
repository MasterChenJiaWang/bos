/**
 * 
 */
package com.jiawang.chen.bos.shrio;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jiawang.chen.bos.dao.IFunctionDao;
import com.jiawang.chen.bos.dao.IUserDao;
import com.jiawang.chen.bos.entity.Function;
import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.web.action.DecidedzoneAction;

/**
 *<p>����: BOSRealm </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��20�� ����9:56:38
 *@�汾 
 */
public class BOSRealm extends AuthorizingRealm {

	@Resource
	private IUserDao userDao;
	
	@Resource
	private IFunctionDao functionDao;
	
	private static final Logger logger = Logger.getLogger(BOSRealm.class);
	
	/* 
	 *��Ȩ����
	 *2017��2��20������9:58:30
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addStringPermission("staff");//Ϊ��ǰ�û�����staffȨ��
//		//TODO ���ݵ�ǰ��¼�û���ѯ���ݿ⣬��ȡ���Ӧ��Ȩ������
//		info.addRole("staff");//Ϊ��ǰ�û�����staff��ɫ
		
		User user=(User)principals.getPrimaryPrincipal();
		List<Function> list=null;
		if(user.getUsername().equals("chenjiawang")){
			 list = functionDao.find();
		}else{
			list=functionDao.findListByUserid(user.getId());
		}
		for(Function function:list){
			info.addStringPermission(function.getCode());
		}
		return info;
		
		

	}

	/* 
	 * ��֤����
	 *2017��2��20������9:58:30
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		logger.info("��֤������������");
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username = upToken.getUsername();//�������л���û���
		
		logger.info("�����е��û���"+username);
		
		User user = userDao.findUserByUsername(username);// �������л���û���
		if (user == null) {
			// �û���������
			return null;
		} else {
			// �û�������
			String password = user.getPassword();// ������ݿ��д洢������
			// ��������֤��Ϣ����
			/***
			 * ����һ��ǩ�����������������λ�û�ȡ��ǰ����Ķ���
			 * �������������ݿ��в�ѯ��������
			 * ����������ǰrealm������
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password, this.getClass().getSimpleName());
			return info;//���ظ���ȫ���������ɰ�ȫ����������ȶ����ݿ��в�ѯ���������ҳ���ύ������
		}	
	}

}
