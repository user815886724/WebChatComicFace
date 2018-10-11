package com.face.dao;

import com.face.entity.Face;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaceDao extends JpaRepository<Face,String>{
}
