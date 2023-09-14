package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.model.domain.Users;
import ra.service.user.IUserService;

import java.util.List;

@RestController
@RequestMapping({"/admin"})
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Users>> getAllUsers() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}
	
	
}
