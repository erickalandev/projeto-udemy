package io.github.erickalandev.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {
	
	@Getter
	private List<String> messageErrors;
	
	public ApiErrors(String messageError) {
		this.messageErrors = Arrays.asList(messageError);
	}
}