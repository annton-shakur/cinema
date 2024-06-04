package com.example.cinema.service.impl;

import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.comment.CommentUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.CommentMapper;
import com.example.cinema.model.Comment;
import com.example.cinema.model.Role;
import com.example.cinema.model.User;
import com.example.cinema.repository.CommentRepository;
import com.example.cinema.repository.RoleRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.uitl.TestParamsInitUtil;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    private static final String CANNOT_FIND_COMMENT_BY_ID_MSG = "Cannot find comment by id: ";
    private static final String CANNOT_FIND_USER_BY_ID_MSG = "Cannot find user by id: ";

    private static Long validCommentId;
    private static Long invalidCommentId;
    private static Long validUserId;
    private static Long invalidUserId;
    private static Long movieId;
    private static Comment comment;
    private static CommentResponseDto commentResponseDto;
    private static CommentCreateDto commentCreateDto;
    private static CommentUpdateDto commentUpdateDto;
    private static User user;
    private static Role moderatorRole;
    private static Pageable pageable;
    private static Page<Comment> commentsPage;
    private static Page<CommentResponseDto> commentResponseDtoPage;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private Logger logger;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeAll
    static void setUp() {
        validCommentId = 1L;
        invalidCommentId = 100L;
        validUserId = 1L;
        invalidUserId = 100L;
        movieId = 1L;

        user = new User();
        moderatorRole = new Role();
        comment = new Comment();
        commentCreateDto = new CommentCreateDto();
        commentUpdateDto = new CommentUpdateDto();
        commentResponseDto = new CommentResponseDto();

        TestParamsInitUtil.initializeAllCommentsFields(user, moderatorRole,
                comment, commentCreateDto,
                commentUpdateDto, commentResponseDto);
        List<Comment> commentsList = List.of(comment);
        List<CommentResponseDto> commentResponseDtoList = List.of(commentResponseDto);
        pageable = Pageable.unpaged();

        commentsPage = new PageImpl<>(commentsList, pageable, commentsList.size());
        commentResponseDtoPage = new PageImpl<>(
                commentResponseDtoList, pageable, commentResponseDtoList.size());
    }

    @Test
    @DisplayName("Find all comments and return response dto page")
    void findAll_ReturnResponseDtoPage() {
        Mockito.when(commentRepository.findAll(pageable)).thenReturn(commentsPage);
        Mockito.when(commentMapper.toDto(comment)).thenReturn(commentResponseDto);

        Page<CommentResponseDto> actual = commentService.findAll(pageable);
        Page<CommentResponseDto> expected = commentResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find comment by valid id and return response dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(commentRepository.findById(validCommentId)).thenReturn(Optional.of(comment));
        Mockito.when(commentMapper.toDto(comment)).thenReturn(commentResponseDto);

        CommentResponseDto actual = commentService.findById(validCommentId);
        CommentResponseDto expected = commentResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find a comment by invalid id")
    void findById_WithInvalidId_ThrowCustomException() {
        Mockito.when(commentRepository.findById(invalidCommentId))
                .thenThrow(new EntityNotFoundException(
                        CANNOT_FIND_COMMENT_BY_ID_MSG + invalidCommentId));

        Assertions.assertThrows(
                EntityNotFoundException.class, () -> commentService.findById(invalidCommentId));
    }

    @Test
    @DisplayName("Find comments by movie id and return response dto page")
    void findByMovieId_WithValidMovieId_ReturnResponseDtoPage() {
        Mockito.when(commentRepository.findByMovieId(pageable, movieId)).thenReturn(commentsPage);
        Mockito.when(commentMapper.toDto(comment)).thenReturn(commentResponseDto);

        Page<CommentResponseDto> actual = commentService.findByMovieId(pageable, movieId);
        Page<CommentResponseDto> expected = commentResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update comment by valid id and user id, return updated response dto")
    void updateById_WithValidIdAndUserId_ReturnUpdatedResponseDto() {
        Mockito.when(commentRepository.findById(validCommentId))
                .thenReturn(Optional.of(comment));
        Mockito.when(userRepository.findById(validUserId))
                .thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findByRoleName(Role.RoleName.MODERATOR))
                .thenReturn(moderatorRole);
        Mockito.when(commentRepository.save(comment))
                .thenReturn(comment);
        Mockito.when(commentMapper.toDto(comment))
                .thenReturn(commentResponseDto);

        CommentResponseDto actual = commentService.updateById(
                validCommentId, validUserId, commentUpdateDto);
        CommentResponseDto expected = commentResponseDto;
        expected.setContent("Updated comment");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to update a comment by invalid id")
    void updateById_WithInvalidId_ThrowCustomException() {
        Mockito.when(commentRepository.findById(invalidCommentId))
                .thenThrow(new EntityNotFoundException(
                        CANNOT_FIND_COMMENT_BY_ID_MSG + invalidCommentId));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> commentService.updateById(invalidCommentId, validUserId, commentUpdateDto));
    }

    @Test
    @DisplayName("Delete comment by id")
    void deleteById_WithValidId() {
        Mockito.doNothing().when(commentRepository).deleteById(validCommentId);

        Assertions.assertDoesNotThrow(() -> commentService.deleteComment(validCommentId));
        Mockito.verify(commentRepository,
                Mockito.times(1)).deleteById(validCommentId);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to save a comment with invalid user id")
    void saveComment_WithInvalidUserId_ThrowCustomException() {
        Mockito.when(userRepository.findById(invalidUserId))
                .thenThrow(new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG + invalidUserId));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> commentService.saveComment(commentCreateDto, invalidUserId));
    }
}
