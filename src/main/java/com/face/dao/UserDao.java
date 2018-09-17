package com.face.dao;

import com.face.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao extends JpaRepository<User,String>{

    @Query(value = "SELECT * from user where open_id = ?1",nativeQuery = true)
    User findUserByOpenId(String openId);
}
