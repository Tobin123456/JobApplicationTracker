package com.example.application_tracker.domain;

import jakarta.persistence.*;


@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq_generator")
    @SequenceGenerator(
            name = "app_seq_generator",
            sequenceName = "application_seq",
            allocationSize = 50
    )
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", unique = true)
    private Job job;


    @Enumerated(EnumType.STRING)
    private Status status;

    // JPA requires a no-arg constructor!
    protected Application() {
        this.job = null;
        this.status = Status.APPLIED;
    }

    public Application(Job job) {
        this.job = job;
        this.status = Status.APPLIED;
    }

    public Long getId() {
        return id;
    }

    public Job getJob() {
        return job;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        APPLIED,
        INTERVIEW,
        OFFER,
        ACCEPTED,
        REJECTED
    }
}

