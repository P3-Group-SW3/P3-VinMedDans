package com.vmd.vmdwebshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false, length = 10)
    private String date;

    @Column(name = "time", nullable = false, length = 5)
    private String time;

    @Column(name = "location", nullable = false, length = 50)
    private String location;

    @Column(name = "title", nullable = false, length = 50)
    private String Title;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

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
