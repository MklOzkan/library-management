package com.project.librarymanagement.configuration;

import com.project.librarymanagement.payload.response.business.ReportResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportConfiguration {


    @Bean
    public ReportResponse getReportResponse()
    {
        return new ReportResponse();
    }
}
