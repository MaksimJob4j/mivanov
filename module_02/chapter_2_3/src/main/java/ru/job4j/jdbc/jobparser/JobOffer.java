package ru.job4j.jdbc.jobparser;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
class JobOffer {
    private String authorName;
    private String authorLink;
    private String jobTopic;
    private String jobLink;
    private LocalDateTime changed;
    private Boolean closed = false;
    private LocalDateTime updated;

}
