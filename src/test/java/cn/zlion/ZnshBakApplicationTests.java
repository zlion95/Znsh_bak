package cn.zlion;

import cn.zlion.service.TableNameException;
import cn.zlion.service.YbTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ZnshBakApplicationTests {

	@Autowired
	private YbTestService ybTestService;

	@Test
	public void contextLoads() {

		try {
			ybTestService.saveClusterDataToYbTest("TestApp");
		}catch (IOException e){
			e.printStackTrace();
		}catch (URISyntaxException e){
			e.printStackTrace();
		}catch (TableNameException e){
			e.printStackTrace();
		}

	}

}
