package de.othr.fitnessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Entity
@Table(name = "Rating")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long id;

    @Column(name = "rating_points")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int points;
    @Column(name = "rating_comment")
    @Length(max = 250, message = "Comment length shall not exceed {max} characters")
    private String comment;

    Course course;

    public Rating(Long id, int points, String comment) {
        this.id = id;
        this.points = points;
        this.comment = comment;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCourse(Course course){
        this.course = course;
    }
}


