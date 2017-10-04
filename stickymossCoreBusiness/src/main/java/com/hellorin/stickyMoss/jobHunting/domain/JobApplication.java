package com.hellorin.stickyMoss.jobHunting.domain;

import com.hellorin.stickyMoss.documents.domain.CV;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by hellorin on 21.06.17.
 */
@Entity
@Table(name = "JOB_APPLICATIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"version", "id", "applicant"})
public class JobApplication {
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "jobApplicationIdGen")
    @SequenceGenerator(name = "jobApplicationIdGen",
        sequenceName = "JOBAPPLICATION_SEQ")
    @Getter
    private Long id;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dateSubmitted;

    @Getter
    @Setter
    private JobApplicationStatus status = JobApplicationStatus.DRAFT;

    @NonNull
    @Getter
    @Setter
    @OneToOne
    private Applicant applicant;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    @Getter
    private CV cv;

    private JobApplication(final Applicant applicant) {
        this.applicant = applicant;
    }

    public JobApplication(final Date date, final Applicant applicant) {
        this(applicant);
        this.dateSubmitted = date;
    }

    public JobApplication(final Date date, final Applicant applicant, final JobApplicationStatus jobApplicationStatus) {
        this(date, applicant);
        this.status = jobApplicationStatus;
    }
}
