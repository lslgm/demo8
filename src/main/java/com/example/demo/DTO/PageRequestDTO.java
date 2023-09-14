package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


//폼에서 페이지 정보를 받아올 DTO
@Builder
@Data
@AllArgsConstructor

public class PageRequestDTO {
    //사용자가 정의한 맴버변수
    private int page; //페이지 번호
    private int size; //페이지당  개수
    private String type; //검색분류
    private  String keyword; //검색어

    public PageRequestDTO(){
        this.page=1; //기본값으로 페이지 1
        this.size=10; //기본값으로 페이지 당 표시 개수 10개
    }

    public Pageable getPageable(Sort sort){ //값을 정렬해서 읽어온다
        return  PageRequest.of(page-1,size,sort);
    }
}
