/**
 * 
 */
package com.jiawang.chen.bos.web.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *<p>标题: String2Citycode </p>
 *<p>描述：生成城市简码 和城市码 </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 下午12:36:37
 *@版本 
 */
public class String2Citycode {

	public String string2Citycode(String city){
		
		city=city.substring(0, city.length()-1);
		String[] cityPinyin = PinYin4jUtils.stringToPinyin(city);
		String citycode = StringUtils.join(cityPinyin, "");
		return citycode;
	}
	
	/**
	 * 
	 * 
	 *@时间 2017年2月16日 下午12:44:46
	 */
	public String string2Shortcode(String city,String province,String district){
		city=city.substring(0, city.length()-1);
		province=province.substring(0, province.length()-1);
		district=district.substring(0, district.length()-1);
		String info=province+city+district;
		String[] infoPinyin = PinYin4jUtils.getHeadByString(info);
		String shortcode = StringUtils.join(infoPinyin, "");
		return shortcode;
	}
}
