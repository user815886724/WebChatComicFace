package com.face.dao;

import com.face.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordDao extends JpaRepository<Word,String> {

    @Query(value = "SELECT * from word where user_id = ?1",nativeQuery = true)
    List<Word> findUserByUserId(String userId);
}
