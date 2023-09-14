package ra.security.user_principle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ra.model.domain.Users;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPrinciple implements UserDetails {
	private Long id;
	private String name;
	private String username;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean status;
	
	public static UserDetails build(Users users) {
		return UserPrinciple.builder()
				  .id(users.getId())
				  .name(users.getName())
				  .username(users.getUsername())
				  .password(users.getPassword())
				  .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName().name())).collect(Collectors.toList()))
				  .build();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
}
