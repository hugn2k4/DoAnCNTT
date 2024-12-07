package com.laptop.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String name;
    private String description;
    private String imageName;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    public Promotion() {}

    public Promotion(long id,
                    String name,
                    String description,
                    String imageName,
                     LocalDateTime startsAt,
                     LocalDateTime endsAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("imageName='" + imageName + "'")
                .add("startsAt=" + startsAt)
                .add("endsAt=" + endsAt)
                .toString();
    }
}
