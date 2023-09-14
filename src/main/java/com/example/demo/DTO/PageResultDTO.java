package com.example.demo.DTO;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN> {
    private List<DTO> dtoList; //페이지 번호를 저장, 10페이지 1,2,3,4.....10
    private int totalPage; //전체페이지수
    private int page; //현제 페이지 번호
    private int size; //페이지 당 항목 개수 전체 100개를 10단위=10개의 페이지
    private int start,end; //시작페이지 번호 끝페이지 번호
    private boolean prev, next; //이전과 다음버튼의 상태, 1페이지이면 이전(비활성화) 마지막페이지면 다음(비활성화)
    private List<Integer> pageList;

    //페이지의 정보를 전달하는 메소드
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage= result.getTotalPages();
        makePageList(result.getPageable()); //페이지 계산하는 메소드
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber()+1; //데이터베이스페이지+1 =>화면 페이지번호
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page/10.0))*10; //끝 페이지 번호
        start = tempEnd - 9; //0~9 시작페이지 번호
        prev = start>1; //시작 페이지가 1보다 크면 이전버튼 활성화
        end = totalPage>tempEnd?tempEnd:totalPage;  //마지막번호
        next = totalPage>tempEnd; //다음버튼 활성화

        //지정범위
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
