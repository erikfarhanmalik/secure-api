package com.erik.learn.secureapi.persistent.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

	private Long id;
	private String name;
	private String address;
}
