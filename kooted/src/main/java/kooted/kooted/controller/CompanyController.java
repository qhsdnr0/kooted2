package kooted.kooted.controller;

import kooted.kooted.model.Company;
import kooted.kooted.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("companies")
@Transactional
@CrossOrigin
public class CompanyController {

    private final CompanyRepository companyRepository;

    @PostMapping("")
    public void createCompany(@RequestBody CompanyForm companyForm) {
        Company company = new Company();
        company.setDescription(companyForm.getDescription());
        company.setEmployeeNumber(companyForm.getEmployee_number());
        company.setLocation(companyForm.getLocation());
        company.setSalary(companyForm.getSalary());
        company.setName(companyForm.getName());
        companyRepository.save(company);
    }
}
