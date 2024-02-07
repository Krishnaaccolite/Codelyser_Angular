package com.example.demo.models;

import com.example.demo.Repositories.QuestionRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Question{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    private Long qId;

    private String questionname;

    @Lob
    @Column(name = "questionZip",columnDefinition = "LONGBLOB")
    private byte[] questionZip;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateTime")
    private Date dateTime;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private List<Submission> submissions;

    public Question(String questionname, Date date, byte[] questionBytes) {
        this.questionname=questionname;
        this.dateTime=date;
        this.questionZip=questionBytes;
    }

    public Question() {

    }

    public Long getqId() {
        return qId;
    }

    public String getQuestionname() {
        return questionname;
    }

    public byte[] getQuestionZip() {
        return questionZip;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public void setQuestionZip(byte[] questionZip) {
        this.questionZip = questionZip;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
    }
    public void setQuestion(String questionname,Date date,byte[] questionZip)
    {
        this.questionname=questionname;
        this.dateTime=date;
        this.questionZip=questionZip;
    }
}
