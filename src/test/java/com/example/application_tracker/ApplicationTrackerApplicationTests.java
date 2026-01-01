package com.example.application_tracker;

import com.example.application_tracker.domain.Application;
import com.example.application_tracker.domain.Company;
import com.example.application_tracker.domain.Job;
import com.example.application_tracker.repository.ApplicationRepository;
import com.example.application_tracker.repository.CompanyRepository;
import com.example.application_tracker.repository.JobRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // Rollback after each test
class ApplicationTrackerIntegrationTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void testCreateApplicationHierarchy() {
        // Create entities
        Company company = companyRepository.save(new Company("MyCompany"));
        Job job = jobRepository.save(new Job("Java Developer", company, "https://example.com/job/1"));
        Application application = applicationRepository.save(new Application(job));

        // Read back from DB
        Company dbCompany = companyRepository.findById(company.getId()).orElseThrow();
        Job dbJob = jobRepository.findById(job.getId()).orElseThrow();
        Application dbApplication = applicationRepository.findById(application.getId()).orElseThrow();

        // Assertions
        Assertions.assertEquals(company.getName(), dbCompany.getName());
        Assertions.assertEquals(job.getTitle(), dbJob.getTitle());
        Assertions.assertEquals(company.getId(), dbJob.getCompany().getId());
        Assertions.assertEquals(job.getId(), dbApplication.getJob().getId());

        // Check counts in DB
        Assertions.assertEquals(1, companyRepository.count());
        Assertions.assertEquals(1, jobRepository.count());
        Assertions.assertEquals(1, applicationRepository.count());
    }
}
