package com.wd.erp.zra.bean.config;

import lombok.Data;

@Data
public class ZraConfig {	
	private String username;
	private String password;
	private String loginUrl ;
	private String grantService;
	private String clientId ;
	private String clientSecret;
	private String dataServiceUrl;
}
