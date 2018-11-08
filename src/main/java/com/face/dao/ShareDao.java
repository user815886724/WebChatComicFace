package com.face.dao;

import com.face.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author huangwh
 * @date 2018/11/3
 * @time 11:00
 */
public interface ShareDao extends JpaRepository<Share,String> {

    @Query(value = "SELECT * FROM share WHERE SHARE_CODE = ?1 AND TIME = ?2",nativeQuery = true)
    Share findByShareCodeAndTime(String code,String time);

    @Query(value = "SELECT * FROM share WHERE SHARES_ID = :id ORDER BY TIME ASC",nativeQuery = true)
    List<Share> queryAllBySharesIdOrOrderByTime(@Param("id") String id);
}
