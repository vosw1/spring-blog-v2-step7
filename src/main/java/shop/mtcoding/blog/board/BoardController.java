package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.utils.ApiUtil;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final HttpSession session;
    private final BoardService boardService;

    @GetMapping("/") // 완료
    public ResponseEntity<?> main(){
        List<BoardResponse.MainDTO> respDTO = boardService.findAll();
        return ResponseEntity.ok(new ApiUtil(respDTO));
    }

    @GetMapping("/api/boards/{id}/detail") // 완료
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO respDTO = boardService.detail(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO)); // board에 연관된 객체가 있기에 위험함 / 무한 참조가 일어날 수 있음
    }

    @GetMapping("/api/boards/{id}") // 완료
    public ResponseEntity<?> lookUp(@PathVariable Integer id) {
        BoardResponse.LookDTO respDTO = boardService.lookUp(id);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @PostMapping("/api/boards") // 완료
    public ResponseEntity<?> save(@RequestBody BoardRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.SaveDTO respDTO = boardService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // @Transactional 트랜잭션 시간이 너무 길어져서 service에 넣어야함
    @PutMapping("/api/boards/{id}") // 완료
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BoardRequest.UpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.UpdateDTO respDTOd = boardService.update(id, sessionUser.getId(), reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTOd));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DeleteDTO respDTO = boardService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}