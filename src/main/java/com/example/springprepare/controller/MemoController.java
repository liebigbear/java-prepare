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

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto memoRequestDto) {
        // 해당 메모가 DB 존재하는지 확인
        if (memos.containsKey(id)) {
            //해당 메모 가져오기
            Memo memo = memos.get(id);

            //메모 수정
            memo.update(memoRequestDto);
            return memo.getId();

        } else {
            throw new IllegalArgumentException("Memo not found");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        if (memos.containsKey(id)) {
            Memo memo = memos.get(id);
            memos.remove(memo.getId());
            return memo.getId();
        } else {
            throw new IllegalArgumentException("Memo not found");
        }
    }
}
