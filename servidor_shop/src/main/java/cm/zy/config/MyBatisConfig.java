package cm.zy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cm.zy.mapper") // Escanea el paquete cm.zy.mapper en busca de interfaces de mappers

public class MyBatisConfig {
}
