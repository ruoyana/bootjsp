package com.mycs.service;

import com.mycs.dao.UserDao;

import com.mycs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Userservice {

    @Autowired
    private UserDao userDao;

    public  List<User> tset(){


        return userDao.test();
    }



    public List<User> queryUser(int pageNo, int pageSize) {
        return userDao.queryUser(pageNo,pageSize);
    }


    public int qushangye(int pageNo, int pageSize) {
        return userDao.qushangye(pageNo,pageSize);
    }

    public int sums(int pageSize) {
        return userDao.sums(pageSize);
    }

    public void delete(int id) {
        userDao.delete(id);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void add(User user) {
        userDao.add(user);
    }

    public User queryid(int id) {
        return userDao.queryid(id);
    }

    public void addlist(List<User> user) {

        userDao.addlist(user);
    }

//    public List<User> querysy() {
//        List<User> user  = userDao.querysy();
//        return user;
//    }


}
