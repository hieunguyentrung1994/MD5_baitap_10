package ra.security.user_principle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.model.domain.Users;
import ra.service.user.IUserService;

@Service
public class UserDetailService implements UserDetailsService {
	
	@Autowired
	private IUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Users user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("username not found"));
		return UserPrinciple.build(user);
	}
}
