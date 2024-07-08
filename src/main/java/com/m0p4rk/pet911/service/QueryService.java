package com.m0p4rk.pet911.service;

import com.m0p4rk.pet911.model.QueryRequest;
import com.m0p4rk.pet911.model.QueryResponse;
import com.m0p4rk.pet911.enums.QueryCategory;

public interface QueryService {
    /**
     * 주어진 쿼리 요청을 처리하고 응답을 생성합니다.
     *
     * @param request 처리할 쿼리 요청
     * @return 생성된 쿼리 응답
     */
    QueryResponse processQuery(QueryRequest request);

    /**
     * 주어진 질문 텍스트를 분석하여 적절한 쿼리 카테고리를 결정합니다.
     *
     * @param questionText 분류할 질문 텍스트
     * @return 결정된 쿼리 카테고리
     */
    QueryCategory categorizeQuery(String questionText);

    /**
     * 건강 가이드 관련 쿼리에 대한 응답을 생성합니다.
     *
     * @param request 처리할 쿼리 요청
     * @return 생성된 건강 가이드 응답
     */
    QueryResponse provideHealthGuide(QueryRequest request);

    /**
     * 일반적인 쿼리에 대한 응답을 생성합니다.
     *
     * @param request 처리할 쿼리 요청
     * @return 생성된 일반 응답
     */
    QueryResponse provideGeneralResponse(QueryRequest request);

    // 향후 추가될 수 있는 다른 카테고리의 응답 생성 메서드들...
    // 예: QueryResponse provideEmergencyResponse(QueryRequest request);
    //     QueryResponse provideWebsiteNavigationGuide(QueryRequest request);
}