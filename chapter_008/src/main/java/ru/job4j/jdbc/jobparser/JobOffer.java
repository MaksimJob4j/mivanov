package ru.job4j.jdbc.jobparser;

import java.time.LocalDateTime;

class JobOffer {
    String authorName;
    String authorLink;
    String jobTopic;
    String jobLink;
    LocalDateTime changed;
    Boolean closed = false;
}
