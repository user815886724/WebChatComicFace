package com.face.dao;

import com.face.entity.Shares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:06
 */
@Service
public interface SharesDao extends JpaRepository<Shares,String> {

    @Query(value = "SELECT * FROM shares WHERE CODE = ?1",nativeQuery = true)
    Shares findByCode(String code);

    @Query(value = "SELECT count(*) FROM shares WHERE 1=1 " +
            "AND (code = :code OR :code IS NULL )" +
            "AND  (code_name LIKE CONCAT('%',:codeName,'%') OR :codeName IS NULL )",nativeQuery = true)
    long getCountByCodeOrCodeName(@Param("code") String code,@Param("codeName") String codeName);


}
