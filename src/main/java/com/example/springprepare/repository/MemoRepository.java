package com.example.springprepare.repository;

import com.example.springprepare.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
}
