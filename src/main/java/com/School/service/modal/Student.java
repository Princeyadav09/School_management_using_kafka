package com.School.service.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "class_id")
    private Long classId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "update_by")
    private String updatedBy;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "total_fine", columnDefinition = "Long default 0")
    private Long fine;

    @OneToOne(mappedBy = "student",cascade = CascadeType.ALL)
    private CheckInOut checkInOut;

}
