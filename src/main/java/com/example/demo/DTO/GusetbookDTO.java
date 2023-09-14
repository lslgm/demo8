package com.example.demo.DTO;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
//교환할 자료에 따라 여러개로 구성해서 사용
//일반적으로 게시글을 주고받기 위한 DTO
//Entity와 1대1 매칭할 필요가 없다.
@Data //값 전달용 클래스 선언 getter,setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GusetbookDTO {
    private Long gno; //번호
    private String title; //제목
    private String content; //내용
    private String writer; //작성자
    private LocalDateTime regDate; //생성일자
    private LocalDateTime modDate; //수정일자
}
