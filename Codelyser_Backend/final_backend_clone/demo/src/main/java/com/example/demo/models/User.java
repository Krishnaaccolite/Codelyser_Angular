package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long userId;
    @NotBlank
    @Column(name = "user_email", unique = true)
    private String userEmail;
    private String feedback;
    private int rating;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "qId")
    private Question question;

}
