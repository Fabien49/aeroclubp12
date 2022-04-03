package com.fabienit.biblioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiblioApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiblioApiApplication.class, args);
	}

/*	public void run(String... args) throws Exception {

		createUser();
	}

	private void createUser(){
		System.out.println("je suis en train de tester createUser");
		List<String> userdata = Arrays.asList("ADMIN", "ADMIN", "admin@test.com","admin2021");
//		logger.debug("**** Create default user  {}", userdata);

		Usager usager = new Usager();
		usager.setName("ADMIN");
		usager.setLastName("ADMIN");
		usager.setEmail("admin@test.com");
		BCryptPasswordEncoder bCryptPasswordEncoderLocal = new BCryptPasswordEncoder();
		String encodepwd = bCryptPasswordEncoderLocal.encode("admin2017");
		usager.setPassword(encodepwd);
		System.out.println("admin2021  encoder = " +encodepwd);
//		usager.setPassword("$2a$10$fE7BKQcc.tesDzaptjL8luXZB6MV5rvUJ13ub5aVYKqnoPmMqYd8m");
		usager.setActive(true);
		//Role
		HashSet<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("ADMIN");
		roles.add(role);
		usager.setRoles(roles);
		userRepository.save(usager);
	}*/

}
