package com.board.controller;


import com.board.domain.BoardDTO;
import com.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write.do")
    public String openBoardWrite(@RequestParam(value="idx", required = false) Long idx, Model model) {
        if (idx == null) {
            model.addAttribute("board", new BoardDTO());
        } else {
            BoardDTO board = boardService.getBoardDetail(idx);
            if (board == null) {
                return "redirect:/board/list.do";
            }
            model.addAttribute("board", board);
        }

        return "board/write";
    }

    @PostMapping(value="/board/register.do")
    public String registerBoard(final BoardDTO params) {
        try {
            boolean isRegistered = boardService.registerBoard(params);
            if(isRegistered == false) {

            }
        } catch(DataAccessException e) {

        } catch(Exception e) {

        }
        return "redirect:/board/list.do";

    }

    @GetMapping(value = "/board/list.do")
    public String openBoardList(Model model){
        List<BoardDTO> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return  "board/list";
    }
    @GetMapping(value = "/board/view.do")
    public String openBoardDetail(@RequestParam(value = "idx", required = false) Long idx, Model model) {

        // 올바르지 않은 접근 시
        if(idx == null) {
            // => 올바르지 않은 접근이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list.do";
        }

        BoardDTO board = boardService.getBoardDetail(idx);

        // 없는 게시글이거나, 이미 삭제된 게시글일 경우
        if(board == null || "Y".equals(board.getDeleteYn())) {
            // => 삭제된 게시글이라는 메시지를 전달하고, 게시글 리스트로 리다이렉트
            return "redirect:/board/list.do";
        }

        model.addAttribute("board", board);

        return "board/view";
    }

}
