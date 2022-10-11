package ru.job4j.tracker;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "items")
@NoArgsConstructor
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    String description;
    @ToString.Exclude
    private final LocalDateTime created = LocalDateTime.now();
    
    @ToString.Include(name = "created")
    private String created() {
        return created.format(FORMATTER);
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");

    public Item(String name) {
        this.name = name;
    }
    
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
