package com.example;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup() throws Throwable {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testValid() throws Exception {

		JSONObject request = new JSONObject().put("name", "");

		mockMvc.perform(
				post("/test")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(request.toString()))
                .andDo(print())
				.andExpect(status().isBadRequest());
	}
}
