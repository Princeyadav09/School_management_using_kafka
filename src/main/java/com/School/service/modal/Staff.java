package com.School.service.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Id;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "name")
    private String name;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "update_by")
    private String updatedBy;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @Column(name = "user_type")
    private String userType;

    @OneToMany(mappedBy = "staff",cascade = CascadeType.ALL)
    private List<CheckInOut> checkInOuts = new ArrayList<>();

}
