package kooted.kooted.controller;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
public class PostForm {

    private String title;
    private int working_year;
    private BigDecimal recommender_reward;
    private BigDecimal applicant_reward;
    private String content;
    private Long job;
    private Long company;
    private LocalDate deadline;
}
