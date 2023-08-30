package com.todotest.prjtodotest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todotest.prjtodotest.Class.clsTodo;
import com.todotest.prjtodotest.Class.clslsttodo;

@SpringBootTest
class PrjtodotestApplicationTests {
	private MockMvc mockMvc;
	@Autowired
   	WebApplicationContext webApplicationContext;

	protected void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }

	@Test
	void contextLoads()  throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/task/testrun"));
		response.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void Test_Login_fail()  throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/oa/login?code=ABC"))		
			.andExpect(MockMvcResultMatchers.status().isOk())	
			.andExpect(asst -> assertEquals("Please login !", asst.getResponse().getContentAsString()))
			.andReturn();	
	}

	@Test
	void Test_Login_success()  throws Exception {
		String logincode = "AQC5hMzzrEgmmq-FxSbT9ruVhLi7A0KhQ2X0ra38z6t_ZF682GjHVkU2k0YACP6uV_U9qlxgrdsNyWHaljOk6d2pm9Lssh0gw0yeojeOxGxCBPPdhuILc4NSCyTIlXrTGK9XZbY4NNxY1NM4WpxPddlSHAp5H8EpvTUulIWd9B7HPDTuvs10Jp64AalEji2lOftgJ5OI2Fu5jRXK0usPyfMzWBMzXgEZsyK3aqVOpXJRlXa2BQ1uleYxVSesleLTrILump38J8lATrgJdA1hHfKfCXMC-zcsauYvbGQg53Lot7bt6QbmVUAMcRmT4MHCJ8rsiXFa9YQ21Fz7j8-MZbYwonqmZY85bKBrzzzY4kucjJZMyt2ZEyUS5x5cC3e-1AI";
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/oa/login?code="+logincode))		
			.andExpect(MockMvcResultMatchers.status().isOk())	
			.andExpect(asst -> assertEquals("Login success", asst.getResponse().getContentAsString()))
			.andReturn();	

	}

	@Test
	void createtask_wrongmethod()  throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/createtask"))		
			.andExpect(MockMvcResultMatchers.status().isMethodNotAllowed())			
			.andReturn();	

	}

	@Test
	void createtask_post()  throws Exception {
		
		String bearerToken = "EAAc5mHOQj9sBOZCd8xd9DZARhkx3e1BMaKhNjG3H5kwGDeGIvOpkNBfZBNAZCJmbXRxsxWWRWRbZB2cOZBSmo3rezUOyh5ZAAsLIXoJ3ZCMH0Jsb71RZCwIYRBPZBxrlBQVe39qTOnZCTqZCcTQvFfBCygEV0ezh3P9bTjLMZBRofFQtbNyi8YLdFol21SMxA7ZA5rJk7v1rFowCcQboX5m64EZAsZAD6gHgGnUl9UJpNvF6rEXcZAlWA4iAtCQZDZD";
		int uid = 1;

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/task/createtask?uid="+ uid)
			.contentType(MediaType.TEXT_PLAIN)
			.content("Something 22 abc")
			.header("AUTHORIZATION", bearerToken)
			)			
			.andExpect(MockMvcResultMatchers.status().isOk())						
			.andReturn();	
	}

	@Test
	void completetask_post()  throws Exception {
		
		String bearerToken = "EAAc5mHOQj9sBOZCd8xd9DZARhkx3e1BMaKhNjG3H5kwGDeGIvOpkNBfZBNAZCJmbXRxsxWWRWRbZB2cOZBSmo3rezUOyh5ZAAsLIXoJ3ZCMH0Jsb71RZCwIYRBPZBxrlBQVe39qTOnZCTqZCcTQvFfBCygEV0ezh3P9bTjLMZBRofFQtbNyi8YLdFol21SMxA7ZA5rJk7v1rFowCcQboX5m64EZAsZAD6gHgGnUl9UJpNvF6rEXcZAlWA4iAtCQZDZD";
		int uid = 1;
		int TaskId = 1;
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/task/complete/"+TaskId+"/"+uid)			
			.header("AUTHORIZATION", bearerToken)
			)			
			.andExpect(MockMvcResultMatchers.status().isOk())						
			.andReturn();	
			//MvcResult result = mockMvc.perform(post("/administration")			
			//.characterEncoding("utf-8"))
			//.andReturn();

	}

	@Test
	void removetask_post()  throws Exception {
		
		String bearerToken = "EAAc5mHOQj9sBOZCd8xd9DZARhkx3e1BMaKhNjG3H5kwGDeGIvOpkNBfZBNAZCJmbXRxsxWWRWRbZB2cOZBSmo3rezUOyh5ZAAsLIXoJ3ZCMH0Jsb71RZCwIYRBPZBxrlBQVe39qTOnZCTqZCcTQvFfBCygEV0ezh3P9bTjLMZBRofFQtbNyi8YLdFol21SMxA7ZA5rJk7v1rFowCcQboX5m64EZAsZAD6gHgGnUl9UJpNvF6rEXcZAlWA4iAtCQZDZD";
		int uid = 1;
		int TaskId = 1;
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/task/remove/"+TaskId+"/"+uid)			
			.header("AUTHORIZATION", bearerToken)
			)			
			.andExpect(MockMvcResultMatchers.status().isOk())						
			.andReturn();				
	}


	@Test
	void listtodo()  throws Exception {
		
		String bearerToken = "EAAc5mHOQj9sBOZCd8xd9DZARhkx3e1BMaKhNjG3H5kwGDeGIvOpkNBfZBNAZCJmbXRxsxWWRWRbZB2cOZBSmo3rezUOyh5ZAAsLIXoJ3ZCMH0Jsb71RZCwIYRBPZBxrlBQVe39qTOnZCTqZCcTQvFfBCygEV0ezh3P9bTjLMZBRofFQtbNyi8YLdFol21SMxA7ZA5rJk7v1rFowCcQboX5m64EZAsZAD6gHgGnUl9UJpNvF6rEXcZAlWA4iAtCQZDZD";
		int uid = 1;
		List<com.todotest.prjtodotest.Class.clsTodo> clsTodo;
	    com.todotest.prjtodotest.Class.clslsttodo abc;

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/task/listtodo/"+uid)			
			.header("AUTHORIZATION", bearerToken)
			)			
			.andExpect(MockMvcResultMatchers.status().isOk())						
			.andReturn();	
		String content = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		
		clsTodo = mapper.readValue(content, new TypeReference<List<com.todotest.prjtodotest.Class.clsTodo>>(){});
		

	}
}
