package com.School.service.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "checkin_checkout_activity_details")
public class CheckInOut {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "check_in_time")
    private ZonedDateTime checkIn;

    @Column(name = "check_out_time")
    private ZonedDateTime checkOut;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "update_by")
    private String updatedBy;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

}
