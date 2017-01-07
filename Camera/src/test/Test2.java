package test;

import org.junit.Test;

import com.dgut.app.pck.Encrypt;
import com.dgut.main.Constants;

public class Test2 {
	
	@Test
	public void test1(){
		String s="_t=2016-11-11 17:14:16&mobile=13416955362&opt=2&pwd=778904ADF14F58F4";
		System.out.println(Encrypt.MD5(s+Constants.APP_ENCRYPTION_KEY));
	}
}
