package com.face.dao;

import com.face.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordDao extends JpaRepository<Word,String> {
}
