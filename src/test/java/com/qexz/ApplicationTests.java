package com.qexz;

import com.qexz.common.QexzConst;
import com.qexz.config.DruidConfig;
import com.qexz.util.MD5;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	DruidConfig dataSource;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testDB() throws SQLException {
		Connection connection = dataSource.dataSource().getConnection();
		System.out.println(dataSource);
	}

	@Test
	public void testMD5(){

		System.out.println(MD5.md5(QexzConst.MD5_SALT + "1234"));

	}

}
