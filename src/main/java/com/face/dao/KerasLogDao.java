package com.face.dao;

import com.face.entity.KerasLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author huangwh
 * @date 2019/1/8
 * @time 14:53
 */
public interface KerasLogDao extends JpaRepository<KerasLog,String> {
}
