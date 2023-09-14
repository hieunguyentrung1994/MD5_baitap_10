package ra.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.domain.Role;
import ra.model.domain.RoleName;
import ra.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Override
	public Role findByRoleName(RoleName roleName) {
		return roleRepository.findByRoleName(roleName).orElseThrow(() -> new RuntimeException("role not found"));
	}
}
