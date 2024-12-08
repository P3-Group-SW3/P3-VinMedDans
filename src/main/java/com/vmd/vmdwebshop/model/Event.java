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
    private Long ID;

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
    @Column(name = "location", nullable = false, length = 80)
    private String location;

    @NotBlank (message = "This field must not be blank")
    @Size(max = 50)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotBlank (message = "This field must not be blank")
    @Size(max = 500)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @NotBlank (message = "This field must not be blank")
    @Column(name = "imgURL", nullable = false, length = 200)
    private String imgURL;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled;

    // Empty Constructor
    public Event() {}

    // Constructor
    public Event(String date, String time, String location, String title,
                 String description, String imgURL, boolean cancelled) {
        this.date = date;
        this.time = time;
        this.location = location;
        this.title = title;
        this.description = description;
        this.imgURL = imgURL;
        this.cancelled = cancelled;

    }

    // Getters and setters
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
