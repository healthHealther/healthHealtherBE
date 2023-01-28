package com.health.healther.service;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.BoardLike;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.BoardLikeRepository;
import com.health.healther.domain.repository.BoardRepository;
import com.health.healther.domain.repository.CommentRepository;
import com.health.healther.dto.board.BoardCreateRequestDto;
import com.health.healther.dto.board.BoardDetailResponseDto;
import com.health.healther.exception.board.BoardLikeAlreadyExistException;
import com.health.healther.exception.board.NotFoundBoardException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardLikeRepository boardLikeRepository;


    private final CommentRepository commentRepository;

    private final MemberService memberService;

    @Transactional
    public void createBoard(BoardCreateRequestDto request) {

        Member member = memberService.findUserFromToken();

        boardRepository.save(Board.builder()
                                  .member(member)
                                  .title(request.getTitle())
                                  .content(request.getContent())
                                  .likeCount(0)
                                  .build());
    }

    @Transactional(readOnly = true)
    public BoardDetailResponseDto getBoardDetail(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        return BoardDetailResponseDto.of(board);
    }

    @Transactional

    public void likeBoard(Long id) {

        Member member = memberService.findUserFromToken();

    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));
                
        if(boardLikeRepository.findByMemberAndBoard(member,board).isPresent()) {
            throw new BoardLikeAlreadyExistException("추천 정보가 이미 존재합니다.");
        }

        boardLikeRepository.save(BoardLike.builder()
                .member(member)
                .board(board)
                .isLiked(true)
                .build());

        board.likeBoard();
        boardRepository.delete(board);
    }
}