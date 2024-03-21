package shop.mtcoding.blog.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.board.BoardJPARepository;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserResponse;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final BoardJPARepository boardJPARepository;
    private final ReplyJPARepository replyJPARepository;

    @Transactional
    public ReplyResponse.SaveDTO save(ReplyRequest.SaveDTO reqDTO, User sessionUser) {
        Board board = boardJPARepository.findById(reqDTO.getBoardId())
                        .orElseThrow(() -> new Exception404("없는 게시글에 댓글을 작성할 수 없어요"));

        Reply reply = reqDTO.toEntity(sessionUser, board);

        replyJPARepository.save(reply);
        return new ReplyResponse.SaveDTO(reply);
    }

    @Transactional
    public ReplyResponse.DeleteDTO delete(int replyId, int sessionUserId) {
        Reply reply = replyJPARepository.findById(replyId)
                        .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 없어요"));

        if(reply.getUser().getId() != sessionUserId){
            throw new Exception403("댓글을 삭제할 권한이 없어요");
        }
        replyJPARepository.deleteById(replyId);
        return new ReplyResponse.DeleteDTO(reply);
    }
}
