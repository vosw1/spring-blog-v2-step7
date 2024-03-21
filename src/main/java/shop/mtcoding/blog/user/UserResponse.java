package shop.mtcoding.blog.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class DTO {
        private int id;
        private String username;
        private String email;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    @Data
    public static class UpdateDTO {
        private int id;
        private String password;
        private String email;

        public UpdateDTO(User user) {
            this.id = user.getId();
            this.password = user.getPassword();
            this.email = user.getEmail();
        }
    }
}
