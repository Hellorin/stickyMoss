package com.hellorin.stickyMoss.jobHunting.domain;

import lombok.*;

import javax.persistence.*;
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
    @NonNull
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

    /*@OneToOne
    @NotNull
    private CV cv;*/

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
