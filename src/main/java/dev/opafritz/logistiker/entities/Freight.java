package dev.opafritz.logistiker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
public class Freight {
    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @Column(nullable = false)
    private String start;
    @Column(nullable = false)
    private String destination;

    @CreationTimestamp
    @Column(updatable = false, name="created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updatedAt;
}
