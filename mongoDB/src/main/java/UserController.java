import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping
    private void insertCharInfo(HttpServletRequest req, HttpServletResponse res, @RequestBody User user){
        System.out.println("res : "+res);
        System.out.println("req : "+req);
        System.out.println("User : " + user);
    }
    @GetMapping
    private ResponseEntity<String> getTest(HttpServletRequest req, HttpServletResponse res){
        return new ResponseEntity<String>("get call ok", HttpStatus.OK);
    }
}
