package com.hellorin.stickyMoss.jobHunting.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by hellorin on 21.06.17.
 */
@Entity
@Table(name = "JOB_OFFERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"version", "id"})
public class JobOffer {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "jobOfferIdGen")
    @SequenceGenerator(name = "jobOfferIdGen",
            sequenceName = "JOBOFFER_SEQ")
    @Getter
    private Long id;

    @Getter
    @Temporal(TemporalType.DATE)
    private Date dateDiscovered;

    @Getter
    private JobOfferStatus status;

    public JobOffer(final Date dateDiscovered, final JobOfferStatus jobOfferStatus) {
        this.dateDiscovered = dateDiscovered;
        this.status = jobOfferStatus;
    }

    public JobOffer(final Date dateDiscovered) {
        this(dateDiscovered, JobOfferStatus.OPEN);
    }

    public void close() {
        this.status = JobOfferStatus.CLOSE;
    }
}
