package Login;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    ArrayList<Object> user_list = new ArrayList<>();
    @Autowired
    private final Login login;
    public UserController(Login login){
        this.login=login;
    }


    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            login.addUser(user);
            user_list.add(user);
            return ResponseEntity.ok("User added successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            User user = new User(id, null, null, null);
            login.deleteUser(user);
            user_list.remove(user);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArrayList<Object>>> getAllUsers() {
        return ResponseEntity.ok(Collections.singletonList(user_list));
    }
}
