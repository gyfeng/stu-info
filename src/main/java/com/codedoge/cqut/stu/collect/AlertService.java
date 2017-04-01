package com.codedoge.cqut.stu.collect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Code Doge(http://www.codedoge.com/)
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Component
@EnableScheduling
public class AlertService {
    @Value("${EMAIL_USERNAME}")
    private String userName;

    private final UserInfoDao userInfoDao;

    private final JavaMailSender mailSender;

    @Autowired
    public AlertService(UserInfoDao userInfoDao, JavaMailSender mailSender) {
        this.userInfoDao = userInfoDao;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void alert() {
        log.info("定时提醒JOB启动");
        Iterable<UserInfo> userInfos = userInfoDao.findAll();
        userInfos.forEach((stu) -> {
            if (stu.getPhone() == null || stu.getPhone().length() < 5) {
                if (stu.getQq() != null && stu.getQq().length() > 5) {
                    log.info("send email to {}", stu.getStuName());
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(userName);
                    message.setTo(stu.getQq() + "@qq.com");
                    message.setSubject("主题：重庆理工大学10级信息与计算科学专业校友信息收集");
                    message.setText(stu.getStuName() + "同学，您好：\r\n" +
                            "    抱歉打扰。由于校友信息是学校就业处、教务处、档案馆等部门数据整理的校友信息数据，而经过多年的专业整合，" +
                            "数据在统计过程中难免有缺失、重复等情况，根据学校对校友工作的要求，需要重新采集完善的校友信息，" +
                            "麻烦各位填一下表格信息，地址：http://110010101.daoapp.io/，双击行进行编辑，谢谢！\r\n" +
                            "此信息为每天定时发送。若不需要填写，请忽略！郭远峰");
                    mailSender.send(message);
                }
            }
        });
    }

    @Scheduled(cron = "0 30 21 * * *")
    public void sendBack() {
        log.info("bak 工作 启动");
        Iterable<UserInfo> userInfos = userInfoDao.findAll();
        StringBuilder all = new StringBuilder("学院名称,专业名称,班级名称,学号,姓名,性别,联系电话,电子邮箱,联系地址,工作单位,工作职位,行业,QQ,学历,入学年份,毕业年份,备注");
        all.append("\r\n");
        userInfos.forEach((stu) -> {
            all.append('"').append(stringConvert(stu.getCollegeName())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getSpecialtyName())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getClsNo())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getStuNo())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getStuName())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getSex())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getPhone())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getEmail())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getAddress())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getCompany())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getJob())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getIndustry())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getQq())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getEducation())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getStartYear())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getEndYear())).append('"').append(',');
            all.append('"').append(stringConvert(stu.getRemark())).append('"').append("\r\n");
        });
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(userName);
        message.setTo("243044136@qq.com");
        message.setSubject("主题：重庆理工大学10级信息与计算科学专业校友信息收集备份");
        message.setText(all.toString());
        mailSender.send(message);
    }

    private String stringConvert(String string) {
        return string == null ? "" : string;
    }

}
