package com.hedon.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Hedon Wang
 * @create 2020-10-02 16:58
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)  //加一个监听器，后面的@CreateDate等注解才能监听得到当前时间
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String method;      //请求方式
    private String path;        //请求路径
    private Integer status;      //http返回的状态码
    private String username;    //发起请求的用户

    @Temporal(TemporalType.TIMESTAMP)       //这个注解表示要存储到数据库中的时候以"时间戳"的形式存储
    @CreatedDate                            //JPA 会在 save 到数据库之前自动获取当前时间赋值给该值
    private Date createTime;    //创建时间

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;    //修改时间
}
