package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class HelloControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void curieLinksNotRendered() throws Exception {
		mvc.perform(get("/hello").param("name", "World")) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$._links").doesNotExist());
	}

	@Test
	void curieLinksRendered() throws Exception {
		mvc.perform(get("/hello").param("name", "Bill")) //
				.andExpect(status().isOk()) //
				.andExpect(jsonPath("$._links.a:world.href").value(endsWith("/hello?name=World")))
				.andExpect(jsonPath("$._links.curies").exists())
				.andExpect(jsonPath("$._links.curies.length()").value(is(1)))
				.andExpect(jsonPath("$._links.curies[0].name").value(is("a")))
				.andExpect(jsonPath("$._links.curies[0].href").value(is("https://aaa/rels/{rel}")))
				.andExpect(jsonPath("$._links.curies[0].templated").value(is(true)));
	}
}
