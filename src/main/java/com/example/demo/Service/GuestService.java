package com.example.demo.Service;

import com.example.demo.DTO.GusetbookDTO;
import com.example.demo.DTO.PageRequestDTO;
import com.example.demo.DTO.PageResultDTO;
import com.example.demo.Entity.Guestbook;


//서비스에 사용할 메소드 뼈대 지정
//Controller에서 호출할 메소드이름 -> 작업내역과 비슷하게 작업
//default 기본형 - 오버라이 불필요
//일반메소드는 implement에서 내용을 채우기가 필요
public interface GuestService {
    //데이터베이스 작업을 위해 필요한 메소드
    //저장(DTO를 받아서 Entity에 전달)
    Long register(GusetbookDTO gusetbookDTO);
    //수정 (DTO를 받아서 Entity에 전달)
    void modify(GusetbookDTO gusetbookDTO);
    //삭제(번호를 받아서 -> Entity에 전달)
    void remove(Long gno);
    //상세보기(번호를 받아서 -> Entity에 전달->DTO결과를 받기)
    GusetbookDTO read(Long gno);
    //페이지별 조회(requestDTO값을 Entity에 전달->guestbookDTO, Guestbook 결과를 받기)
    PageResultDTO<GusetbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    //부수적으로 작업에 필요한 메소드
    //DTO=>Entity 변환 , Entity->DTO 변환
    default Guestbook dtoToEntity(GusetbookDTO gusetbookDTO){ // DTO에서 Entity
        Guestbook entity=Guestbook.builder()
                .gno(gusetbookDTO.getGno())
                .title(gusetbookDTO.getTitle())
                .content(gusetbookDTO.getContent())
                .writer(gusetbookDTO.getWriter())
                .build();
        return  entity;
    }

    default GusetbookDTO entityToDto(Guestbook gusetbook){ // Entity에서 DTO
        GusetbookDTO dto=GusetbookDTO.builder()
                .gno(gusetbook.getGno())
                .title(gusetbook.getTitle())
                .content(gusetbook.getContent())
                .writer(gusetbook.getWriter())
                .build();
        return  dto;
    }
}
