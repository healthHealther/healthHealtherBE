package com.health.healther.service;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.BoardLike;
import com.health.healther.domain.model.Comment;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.BoardLikeRepository;
import com.health.healther.domain.repository.BoardRepository;
import com.health.healther.domain.repository.CommentRepository;
import com.health.healther.dto.board.*;
import com.health.healther.exception.board.BoardLikeAlreadyExistException;
import com.health.healther.dto.board.BoardCreateRequestDto;
import com.health.healther.dto.board.BoardDetailResponseDto;
import com.health.healther.exception.board.NotFoundBoardException;
import com.health.healther.exception.board.NotFoundBoardLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Transactional(readOnly = true)
    public BoardIsLikedResponseDto boardIsLiked(Long id) {

        Member member = memberService.findUserFromToken();

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        if (boardLikeRepository.findByMemberAndBoard(member, board).isEmpty()) {
            return BoardIsLikedResponseDto.of(false);
        }
        return BoardIsLikedResponseDto.of(true);
    }


    @Transactional
    public void updateBoard(Long id, BoardUpdateRequestDto request) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        board.updateBoard(request);

    }


    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        boardRepository.delete(board);
    }

    @Transactional
    public void likeBoard(Long id) {

        Member member = memberService.findUserFromToken();

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        if (boardLikeRepository.findByMemberAndBoard(member, board).isPresent()) {
            throw new BoardLikeAlreadyExistException("추천 정보가 이미 존재합니다.");
        }

        boardLikeRepository.save(BoardLike.builder()
                                          .member(member)
                                          .board(board)
                                          .isLiked(true)
                                          .build());

        board.likeBoard();
    }

    @Transactional
    public void deleteBoardLike(Long id) {

        Member member = memberService.findUserFromToken();

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        BoardLike boardLike = boardLikeRepository.findByMemberAndBoard(member, board)
                                                 .orElseThrow(() -> new NotFoundBoardLikeException("추천 정보를 찾을 수 없습니다."));

        boardLikeRepository.delete(boardLike);

        board.deleteBoardLike();
    }

    @Transactional
    public CommentRegisterResponseDto registerComment(Long id, CommentRegisterRequestDto request) {

        Member member = memberService.findUserFromToken();

        Board board = boardRepository.findById(id)
                                     .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        commentRepository.save(Comment.builder()
                                      .member(member)
                                      .board(board)
                                      .context(request.getContext())
                                      .build());

        return new CommentRegisterResponseDto(request.getContext());
    }

    public List<CommentListResponseDto> getCommentList(Long id, CommentListRequestDto request) {
        return null;
    }
}

