package com.face.dao;

import com.face.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture,String> {

}
