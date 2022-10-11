package ru.job4j.tracker;

import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@RequiredArgsConstructor
public class Category {
	@Getter
	@NonNull
	private int id;
	@Setter
	@Getter
	private String name;
}
