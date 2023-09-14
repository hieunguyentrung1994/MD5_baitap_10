package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegister {
	private String email;
	private String name;
	private String username;
	private String password;
	private List<String> roles;
}
