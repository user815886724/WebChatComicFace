package com.face.dao;

import com.face.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author huangwh
 * @date 2019/1/7
 * @time 17:39
 */
public interface ConfigDao extends JpaRepository<Config,String> {

    @Query(value = "SELECT c.VALUE FROM config c WHERE c.CODE = :code",nativeQuery = true)
    String findValueByCode(@Param("code") String code);
}
