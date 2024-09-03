    package com.personal.portfolio.Model;

    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.experimental.FieldDefaults;

    import java.util.Date;

    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Certification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long certificationId;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private Users user;

        private String name;
        private String issuingOrganization;
        private Date issueDate;
        private Date expirationDate;

    }
