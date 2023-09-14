package ra.service.user;

import ra.exception.UserAuthentication;
import ra.model.domain.Users;
import ra.model.dto.request.UserRegister;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	List<Users> findAll();
	
	Users save(UserRegister userRegister) throws UserAuthentication;
	
	boolean existsByUsername(String username);
	
	Optional<Users> findByUsername(String username);
}
