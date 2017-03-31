package com.codedoge.cqut.stu.collect;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Code Doge(http://www.codedoge.com/)
 * @version 1.0
 * @since 1.0
 */
@Data
@Entity(name = "stu_info")
public class UserInfo implements Serializable {
    private String collegeName;
    private String specialtyName;
    private String clsNo;
    private String stuNo;
    @Id
    private int id;
    private String stuName;
    private String sex;
    private String phone;
    private String email;
    private String address;
    private String company;
    private String job;
    private String industry;
    private String qq;
    private String education;
    private String startYear;
    private String endYear;
    private String remark;
}
