/**
 * 
 */
package bosTest01;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.jiawang.chen.bos.web.utils.PinYin4jUtils;

/**
 *<p>标题: Pinyin4Jtest </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 下午12:12:23
 *@版本 
 */
public class Pinyin4Jtest {
	
	@Test
	public void testPinyin4J(){
		String province="湖北省";
		String city="武汉市";
		String district="新洲区";
		
		//城市简码----->>>wuhan
		String substring = city.substring(0, city.length()-1);
		System.out.println(substring);
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(substring);
		String citycode = StringUtils.join(stringToPinyin, "");
		System.out.println(citycode);
		
		//简码--->>>hbswhxz
		province = province.substring(0, province.length()-1);
		district = district.substring(0, district.length()-1);
		String info=province+substring+district;//湖北武汉新洲
		String[] stringToPinyin2 = PinYin4jUtils.getHeadByString(info);
		String shortcode = StringUtils.join(stringToPinyin2, "");
		System.out.println(shortcode);
	}

}
