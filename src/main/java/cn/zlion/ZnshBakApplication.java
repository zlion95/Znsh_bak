package cn.zlion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ResultHttpSetting.class, SchemaSetting.class, DynamicTableSetting.class})
public class ZnshBakApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZnshBakApplication.class, args);
	}
}
