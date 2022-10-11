package ru.job4j.tracker;

import lombok.*;

import java.util.List;
@Builder(builderMethodName = "of")
@Data
public class Permission {
	private int id;
	private String name;
	@Singular
	private List<String> rules;
	
	public static void main(String[] args) {
		Permission nata = Permission.of().id(1).name("Nata").rule("1").rule("2").build();
		System.out.println(nata);
	}
}
