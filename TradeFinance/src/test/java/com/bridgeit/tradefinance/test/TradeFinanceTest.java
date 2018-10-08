package com.bridgeit.tradefinance.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bridgeit.tradefinance.configuration.AppConfig;
import com.bridgeit.tradefinance.trade.model.CreateContractDto;
import com.bridgeit.tradefinance.user.model.LoginDto;
import com.bridgeit.tradefinance.user.model.RegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class TradeFinanceTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	static String header = null;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

//	@Test
//	public void testRegister() throws Exception {
//		// fail("Not yet implemented");
//		RegisterDto register = new RegisterDto();
//		register.setOrg_name("sim");
//		register.setEmail("ankitaunhalkar19@gmail.com");
//		register.setPassword("123456");
//		register.setRole("importer");
//		register.setBankname("BOI");
//
//		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(register)))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("code").value("200"))
//				.andExpect(jsonPath("message").value("User registration successfully! Verify your Email Id and Activate your account"));
//				
//	}
//	
	@Test
	public void testLogin() throws Exception {
		// fail("Not yet implemented");
		LoginDto login = new LoginDto();
		login.setEmail("ankitaunhalkar19@gmail.com");
		login.setPassword("123456");

		MvcResult result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(login)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("code").value("200"))
				.andExpect(jsonPath("message").value("Logged in succesfully"))
				.andReturn();
		header = result.getResponse().getHeader("Authorization");
		System.out.println(header);
//		System.out.println("Header "+result.getResponse().getHeader("Authorization"));
				
	}	

	@Test
	public void testContract() throws Exception {
		System.out.println("contract"+header);
		CreateContractDto contract = new CreateContractDto();
		contract.setContract_id("111");
		contract.setContract_Description("100 flowers");
		contract.setContract_amount(2000);
		contract.setContract_importer("1");
		contract.setContract_exporter("2");
		contract.setContract_importerbank("3");
		contract.setContract_insurance("4");
		contract.setContract_custom("5");
		contract.setContract_loadingport("mumbai");
		contract.setContract_entryport("goa");
		contract.setBillofLading("http://localhost:8080/tradefinance/getfile/bol.pdf");
		contract.setLetterofCredit("http://localhost:8080/tradefinance/getfile/loc.pdf");
		
		mockMvc.perform(post("/createcontract").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", header)
				.content(new ObjectMapper().writeValueAsString(contract)))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("code").value("200"))
				.andExpect(jsonPath("message").value("Created successfully"));
	}
}
