package com.face.dao;

import com.face.entity.Shares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * @author huangwh
 * @date 2018/10/28
 * @time 23:06
 */
public interface SharesDao extends JpaRepository<Shares,String> {
}
