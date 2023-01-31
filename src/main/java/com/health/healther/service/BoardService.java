package com.health.healther.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.BoardLike;
import com.health.healther.domain.model.Comment;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.BoardLikeRepository;
import com.health.healther.domain.repository.BoardRepository;
import com.health.healther.domain.repository.CommentRepository;
import com.health.healther.dto.board.BoardCreateRequestDto;
import com.health.healther.dto.board.BoardDetailResponseDto;
import com.health.healther.dto.board.BoardIsLikedResponseDto;
import com.health.healther.dto.board.BoardSearchListRequestDto;
import com.health.healther.dto.board.BoardSearchListResponseDto;
import com.health.healther.dto.board.BoardUpdateRequestDto;
import com.health.healther.dto.board.CommentListResponseDto;
import com.health.healther.dto.board.CommentRegisterRequestDto;
import com.health.healther.dto.board.CommentRegisterResponseDto;
import com.health.healther.dto.board.CommentUpdateRequestDto;
import com.health.healther.dto.board.GetBoardListResponseDto;
import com.health.healther.exception.board.AlreadyBoardLikeException;
import com.health.healther.exception.board.NotFoundBoardException;
import com.health.healther.exception.board.NotFoundBoardLikeException;
import com.health.healther.exception.board.NotFoundCommentException;

import lombok.RequiredArgsConstructor;

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
	public List<GetBoardListResponseDto> getBoardList(int page, int size) {

		PageRequest pageRequest
			= PageRequest.of(page, size, Sort.by("modifiedAt").descending());

		return boardRepository.findAll(pageRequest).stream()
			.map(GetBoardListResponseDto::from)
			.collect(Collectors.toList());
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

		Optional<BoardLike> optionalBoardLike
			= boardLikeRepository.findByMemberAndBoard(member, board);

		if (optionalBoardLike.isPresent() && optionalBoardLike.get().isLiked()) {
			throw new AlreadyBoardLikeException("추천 정보가 이미 존재합니다.");
		}

		if (optionalBoardLike.isEmpty()) {

			boardLikeRepository.save(BoardLike.builder()
				.member(member)
				.board(board)
				.isLiked(true)
				.build());
		} else {
			optionalBoardLike.get().likeBoard();
		}
		board.likeBoard();
	}

	@Transactional
	public void deleteBoardLike(Long id) {

		Member member = memberService.findUserFromToken();

		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

		BoardLike boardLike = boardLikeRepository.findByMemberAndBoard(member, board)
			.orElseThrow(() -> new NotFoundBoardLikeException("추천 정보를 찾을 수 없습니다."));

		deleteBoardLike(board, boardLike);
	}

	private static void deleteBoardLike(Board board, BoardLike boardLike) {
		boardLike.deleteBoardLike();
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

	@Transactional(readOnly = true)
	public List<CommentListResponseDto> getCommentList(Long id, int page, int size) {

		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

		PageRequest pageRequest
			= PageRequest.of(page, size, Sort.by("modifiedAt").descending());

		return commentRepository.findByBoard(board, pageRequest).stream()
			.map(CommentListResponseDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateComment(Long id, CommentUpdateRequestDto request) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new NotFoundCommentException("댓글 정보를 찾을 수 없습니다."));

		comment.updateComment(request.getContent());
	}

	@Transactional(readOnly = true)
	public List<BoardSearchListResponseDto> searchBoardTitle(BoardSearchListRequestDto request) {
		PageRequest pageRequest
			= PageRequest.of(request.getPage(), request.getSize(), Sort.by("modifiedAt").descending());

		return boardRepository.findByTitleContaining(request.getKeyword(), pageRequest).stream()
			.map(BoardSearchListResponseDto::from)
			.collect(Collectors.toList());

	}
}

