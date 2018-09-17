package com.face.dao;

import com.face.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLogDao extends JpaRepository<UserLog,String> {

    @Query(value = "SELECT * FROM user_log where user_id = ?1 and session_id = ?2",nativeQuery = true)
    UserLog getUserLogByUserIdAndSessionId(String userId,String sessionId);

}
