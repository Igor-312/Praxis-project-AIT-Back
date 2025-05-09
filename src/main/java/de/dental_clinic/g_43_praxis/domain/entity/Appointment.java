package de.dental_clinic.g_43_praxis.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dental_service_id", nullable = false)
    private DentalService service;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone1", nullable = false)
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "available_time", columnDefinition = "TEXT")
    private String availableTime;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "language")
    private String language;

    @Column(name = "created_time")
//    private Long createdTime;
    private String createdTime;

    @Column(name = "is_new", nullable = false)
    private Boolean isNew = true;

//    @Column(name = "status")
//    private String status;
//
//    @Column(name = "is_active", nullable = false)
//    private Boolean isActive = true;

}
