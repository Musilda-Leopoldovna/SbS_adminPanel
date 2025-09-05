package kata.springBootSecurity.adminPanel;

import kata.springBootSecurity.adminPanel.rest.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminPanelApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(AdminPanelApplicationTests.class);

	@LocalServerPort
	private int port;
	private final TestRestTemplate restTemplate = new TestRestTemplate("user", "password");

	@Test
	void contextLoads() {
		String url = "http://localhost:" + port + "/api";
		UserDto user = restTemplate.getForObject(url, UserDto.class);
		log.info("Имя пользователя: {}", user.userName());
		assertThat(user.userName())
				.as("Имя пользователя: %s", user.userName())
				.isEqualTo("user");
	}
}
