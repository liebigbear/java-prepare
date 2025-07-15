package com.example.springprepare.controller;

import com.example.springprepare.dto.MemoRequestDto;
import com.example.springprepare.dto.MemoResponseDto;
import com.example.springprepare.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memos = new HashMap<Long, Memo>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto memoRequestDto) {
        // RequestDto -> entity
        Memo memo = new Memo(memoRequestDto);

        //Max ID
        Long maxId = memos.size() > 0 ? Collections.max(memos.keySet()) + 1 : 1;
        memo.setId(maxId);

        //DB
        memos.put(memo.getId(), memo);

        //Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getAllMemos() {
        //Map to List
        List<MemoResponseDto> responseList = memos.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;
    }
}
