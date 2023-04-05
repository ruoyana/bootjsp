package com.example.bootjsp;





import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@EnableAutoConfiguration
//@Import(FdfsClientConfig.class)   //#导入FastDFS-Client组件
@ComponentScan(basePackages = {"com.mycs,com.example.bootjsp"})
@MapperScan(basePackages ={"com.mycs.dao"})
public class BootjspApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootjspApplication.class, args);
    }

}
