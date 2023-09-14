package ra.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.exception.UserAuthentication;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.model.domain.Users;
import ra.model.dto.request.UserRegister;
import ra.repository.IUserRepository;
import ra.service.role.IRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IRoleService roleService;
	
	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
	}
	
	@Override
	public Users save(UserRegister userRegister) throws UserAuthentication {
		if (userRepository.existsByUsername(userRegister.getUsername())) {
			throw new UserAuthentication("username is exists");
		}
		
		Set<Role> roles = new HashSet<>();
		if (userRegister.getRoles() == null || userRegister.getRoles().isEmpty()) {
			roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
		} else {
			userRegister.getRoles().stream().forEach(role -> {
				switch (role) {
					case "admin":
						roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
					case "seller":
						roles.add(roleService.findByRoleName(RoleName.ROLE_SELLER));
					case "user":
						roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
				}
			});
		}
		
		
		return userRepository.save(Users.builder().name(userRegister.getName()).username(userRegister.getUsername()).password(passwordEncoder.encode(userRegister.getPassword())).roles(roles).status(true).build());
	}
	
	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	@Override
	public Optional<Users> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
