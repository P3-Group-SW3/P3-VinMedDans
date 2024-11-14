package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "This field must not be blank")
    @Future
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @Column(name = "date", nullable = false, length = 10)
    private String date;

    @NotBlank (message = "This field must not be blank")
    @Future
    @Column(name = "time", nullable = false, length = 5)
    private String time;

    @NotBlank (message = "This field must not be blank")
    @Size(max = 80)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    @Column(name = "location", nullable = false, length = 50)
    private String location;

    @NotBlank (message = "This field must not be blank")
    @Length(max = 50)
    @Column(name = "title", nullable = false, length = 50)
    private String Title;

    @NotBlank (message = "This field must not be blank")
    @Size(max = 500)
    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @NotBlank (message = "This field must not be blank")
    @Column(name = "imgURL", nullable = false, length = 200)
    private String imgURL;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
}
