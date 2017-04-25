package com.example;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
@RequestMapping("/test")
class TestController {

	// This should be validated by the JSR-303
	@PostMapping
	public Pojo test(@Valid @RequestBody Pojo pojo) {
		return pojo;
	}
}

@RestController
@RequestMapping("/test2")
class Test2Controller {

	private final VeryHardToValidatePojoValidator validator;

	// The validator is a bean and is injected by spring
	public Test2Controller(VeryHardToValidatePojoValidator validator) {
		this.validator = validator;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	// This is validated using the VeryHardToValidatePojoValidator
	@PostMapping
	public VeryHardToValidatePojo test2(@Valid @RequestBody VeryHardToValidatePojo pojo)  {
		return pojo;
	}
}

class Pojo {

	@NotBlank
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class VeryHardToValidatePojo {

	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}


@Component
class VeryHardToValidatePojoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return VeryHardToValidatePojo.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty", "must be present");
	}
}
