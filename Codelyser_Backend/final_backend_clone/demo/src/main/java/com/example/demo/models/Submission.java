package com.example.demo.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;
    private int score;
    private String status;
    private Date submissionDateAndTime;
    @Lob
    @Column(name = "submittedZip", columnDefinition = "LONGBLOB")
    private byte[] submittedZip;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "qId")
    private Question question;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;

}
