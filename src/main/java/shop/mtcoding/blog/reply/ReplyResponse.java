package shop.mtcoding.blog.reply;

import lombok.Data;

public class ReplyResponse {

    @Data
    public static class SaveDTO {
        private Integer id;
        private Integer boardId;
        private String comment;

        public SaveDTO(Reply reply) {
            this.id = reply.getId();
            this.boardId = reply.getBoard().getId();
            this.comment = reply.getComment();
        }
    }

    @Data
    public static class DeleteDTO {
        private Integer id;
        private Integer boardId;
        private String comment;

        public DeleteDTO(Reply reply) {
            this.id = reply.getId();
            this.boardId = reply.getBoard().getId();
            this.comment = reply.getComment();
        }
    }
}
