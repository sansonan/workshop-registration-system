package com.example.school.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "workshops") // Use plural form for table name if consistent with your convention
@Data
public class Workshop{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workshop_id")
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100) // Example length constraint
    private String title;

    @Column(length = 1000) // Optional limit on description length
    private String description;

    @NotNull
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate endDate;


    @Column(nullable = false)
    private String location;

    private String information;

    @Column(nullable = false)
    private int seat;

    private String summary;

    // Many-to-Many relationship with users attending the workshop
    @ManyToMany
    @JoinTable(
            name = "user_workshops",
            joinColumns = @JoinColumn(name = "workshop_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> attendees = new HashSet<>(); // Renamed for clarity

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<StudentRegisterWorkshop> registrations; // Link to student registrations

}

