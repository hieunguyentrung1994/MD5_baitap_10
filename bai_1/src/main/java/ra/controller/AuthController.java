package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ra.exception.UserAuthentication;
import ra.model.dto.request.UserLogin;
import ra.model.dto.request.UserRegister;
import ra.model.dto.response.JwtResponse;
import ra.security.jwt.JwtProvider;
import ra.security.user_principle.UserPrinciple;
import ra.service.mail.MailService;
import ra.service.user.IUserService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private MailService mailService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping
	public ResponseEntity<String> home() {
		return new ResponseEntity<>("Welcome to my website", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> handleLogin(@RequestBody UserLogin userLogin) throws UserAuthentication {
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					  new UsernamePasswordAuthenticationToken(userLogin.getUsername(), userLogin.getPassword())
			); // tạo dối tượng authentication khi xác thực được người dùng
		} catch (AuthenticationException e) {
			throw new UserAuthentication("Username or password is wrong");
		}
		
		// tạo token
		UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
		String token = jwtProvider.generateToken(userPrinciple);
		List<String> roles = userPrinciple.getAuthorities().stream()
				  .map(GrantedAuthority::getAuthority)
				  .collect(Collectors.toList());
		return new ResponseEntity<>(JwtResponse.builder()
				  .token(token)
				  .name(userPrinciple.getName())
				  .username(userPrinciple.getUsername())
				  .roles(roles)
				  .status(userPrinciple.isStatus())
				  .build(), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> handleRegister(@RequestBody UserRegister userRegister) throws MessagingException, UserAuthentication {
		mailService.sendMail(userRegister.getEmail(), "test", "<h1>Con chó này</h1>");
		userService.save(userRegister);
		return new ResponseEntity<>(randomNumber(), HttpStatus.CREATED);
	}
//	"phatnvt04@gmail.com"
	public String randomNumber() {
		StringBuilder number = new StringBuilder("0");
		for (int i = 0; i < 6; i++) {
			number.append(Math.round(Math.random() * 10));
		}
		return number.toString();
	}
	
}
