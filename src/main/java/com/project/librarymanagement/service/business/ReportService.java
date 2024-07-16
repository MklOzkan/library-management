package com.project.librarymanagement.service.business;

import com.project.librarymanagement.payload.response.business.ReportResponse;
import com.project.librarymanagement.service.helper.MethodHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {


        private final MethodHelper methodHelper;



    public ReportResponse getALLReport() {
        return methodHelper.gerReport();
    }

//    public Page<ReportResponse> findMostPopularBooksByPage(int page, int size) {
//
//        Pageable pageable = pageableHelper.getPageableWithProperties( sort, type);
//        return authorRepository.findAll(pageable)
//                .map(authorMapper::mapAuthorToAuthorResponse);
//    }
}
