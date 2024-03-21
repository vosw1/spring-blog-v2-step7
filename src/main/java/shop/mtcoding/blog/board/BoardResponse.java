package shop.mtcoding.blog.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.util.ArrayList;
import java.util.List;

public class BoardResponse {
    // DTO로 바로 받기 위한 테스트 DTO
    @AllArgsConstructor
    @Data
    public static class CountDTO {
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private Long replyCount;
    }

    // 게시글 상세보기 화면
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private UserDTO user;
        private Boolean isOwner; // 좋아요도 마찬가지
        private List<ReplyDTO> replies = new ArrayList<>();

        @Data
        public class UserDTO {
            private int id;
            private String username;

            public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }

        @Data
        public class ReplyDTO {
            private Integer id;
            private String comment;
            private Integer userId; // 댓글 작성자 아이디
            private String username; // 댓글 작성자 이름
            private Boolean isOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment(); // lazyloading 발동
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername(); // lazyloading 발동
                this.isOwner = false;
                if (sessionUser != null) {
                    if (sessionUser.getId() == reply.getUser().getId()) {
                        isOwner = true;
                    }
                }
            }
        }

        // 응답할 때에는 생성자를 만들어야함
        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserDTO(board.getUser());

            this.isOwner = false;
            if (sessionUser != null) {
                if (sessionUser.getId() == board.getUser().getId()) {
                    isOwner = true;
                }
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
        }
    }

    // 게시글 목록보기 화면
    @Data
    public static class MainDTO {
        private Integer id;
        private String title;

        public MainDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }

    // 게시글 조회하기 화면
    @Data
    public static class LookDTO {
        private Integer id;
        private String title;
        private String content;

        public LookDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    // 글 쓰기
    @Data
    public static class SaveDTO {
        private Integer id;
        private String title;
        private String content;


        public SaveDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    // 글 수정하기
    @Data
    public static class UpdateDTO {
        private Integer id;
        private String title;
        private String content;

        public UpdateDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

    @Data
    public static class DeleteDTO {
        private Integer id;
        private String title;
        private String content;

        public DeleteDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }
}