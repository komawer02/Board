package com.board.service;

import com.board.domain.BoardDTO;

import java.util.List;

public interface BoardService {
    public Boolean registerBoard(BoardDTO param);

    public BoardDTO getBoardDetail(Long idx);

    public Boolean deleteBoard(Long idx);

    public List<BoardDTO> getBoardList();
}
