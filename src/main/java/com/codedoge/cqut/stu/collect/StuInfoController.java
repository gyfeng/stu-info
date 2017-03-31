package com.codedoge.cqut.stu.collect;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Code Doge(http://www.codedoge.com/)
 * @version 1.0
 * @since 1.0
 */
@RestController
public class StuInfoController {
    private final UserInfoDao userInfoDao;

    @Autowired
    public StuInfoController(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @RequestMapping(value = "students", method = RequestMethod.GET)
    public Iterable<UserInfo> list() {
        return userInfoDao.findAll();
    }

    @RequestMapping(value = "student/{id}", method = RequestMethod.GET)
    public UserInfo queryStudent(@PathVariable("id") Integer id) {
        return userInfoDao.findOne(id);
    }

    @RequestMapping(value = "student/{id}", method = RequestMethod.POST)
    public UserInfo modifyStudent(@PathVariable("id") Integer id, UserInfo userInfo) {
        UserInfo stu = userInfoDao.findOne(id);
        userInfo.setId(id);
        BeanUtils.copyProperties(userInfo, stu, "id"
                , "collegeName"
                , "specialtyName"
                , "clsNo"
                , "sex"
                , "startYear"
                , "endYear");
        userInfoDao.save(stu);
        return stu;
    }
}
