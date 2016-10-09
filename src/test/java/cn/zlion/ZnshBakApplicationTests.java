package cn.zlion;

import cn.zlion.dao.CourseDao;
import cn.zlion.dao.JobResultDao;
import cn.zlion.dao.TaskResultDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ZnshBakApplicationTests {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private TaskResultDao taskResultDao;

	@Autowired
	private JobResultDao jobResultDao;

	@Test
	public void contextLoads() {
//		List<Course> courses = courseDao.findAll("TestApp");
//		for (Course course : courses){
//			System.out.println(course.toString());
//		}
//		System.out.println(courseDao.findAmount("TestApp"));
		System.out.println(jobResultDao.findByPage("TestApp", 1, 100));
	}

}
