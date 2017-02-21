/**
 * 
 */
package bosTest01;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.jiawang.chen.bos.web.utils.PinYin4jUtils;

/**
 *<p>����: Pinyin4Jtest </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��16�� ����12:12:23
 *@�汾 
 */
public class Pinyin4Jtest {
	
	@Test
	public void testPinyin4J(){
		String province="����ʡ";
		String city="�人��";
		String district="������";
		
		//���м���----->>>wuhan
		String substring = city.substring(0, city.length()-1);
		System.out.println(substring);
		String[] stringToPinyin = PinYin4jUtils.stringToPinyin(substring);
		String citycode = StringUtils.join(stringToPinyin, "");
		System.out.println(citycode);
		
		//����--->>>hbswhxz
		province = province.substring(0, province.length()-1);
		district = district.substring(0, district.length()-1);
		String info=province+substring+district;//�����人����
		String[] stringToPinyin2 = PinYin4jUtils.getHeadByString(info);
		String shortcode = StringUtils.join(stringToPinyin2, "");
		System.out.println(shortcode);
	}

}
