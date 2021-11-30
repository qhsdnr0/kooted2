package kooted.kooted.controller;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class ResumeForm {

    private String college;
    private String title;
    private String status;

    private List<HashMap<String, Object>> careers;

    private List<String> skills;
}
