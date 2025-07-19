package com.example.springprepare.service;

import com.example.springprepare.dto.MemoRequestDto;
import com.example.springprepare.dto.MemoResponseDto;
import com.example.springprepare.entity.Memo;
import com.example.springprepare.repository.MemoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;


    public MemoResponseDto createMemo(MemoRequestDto memoRequestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(memoRequestDto);

        // DB 저장
        Memo savedMemo = memoRepository.save(memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(savedMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
        return memoRepository.findAll().stream()
                .map(MemoResponseDto::new).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new RuntimeException("Memo not found"));

        memo.update(requestDto);
        return id;
    }


    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new RuntimeException("Memo not found"));

        memoRepository.delete(memo);

        return id;
    }

    public List<MemoResponseDto> getMemosContents(String keyword) {
        return memoRepository.findAllByContentsContains(keyword).stream()
                .map(MemoResponseDto::new).toList();
    }
}
