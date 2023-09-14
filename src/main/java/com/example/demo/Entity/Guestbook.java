package com.example.demo.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


//Entity는 클래스명에 테이블명+Entity로 사용하는것보다 테이블명으로 지정
//Entity는 맴버변수와 테이블을 매핑(클래스명+테이블명)
@Entity //엔티티 클래스선언
@Getter //맴버변수에 값을 읽기위한 get메소드 자동생성
@Setter //맴버변수에 값을 저장하기위한 set메소드 자동생성
@AllArgsConstructor //생성자
@NoArgsConstructor //
@ToString //전체 주고받기 위해서
@Builder //전체맴버변수에 값을 쉽게 저장하기위해
//@Table(name="연결할 테이블명") 클래스이름과 테이블이름이 다른경우
public class Guestbook {
    //필드와 변수의 관계 지정
    // 1. 변수를 선언하고 DTO에 복사
    // 2. 테이블과의 관계를 어노테이션 지정
    @Id //기본키만 id 선언
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno; //번호
    //기본키를 제외하고는 모두 column 선언
    //@column(name="필드명",length=길이,nullable=생략가능여부) 이름이 다를때,길이를 생략하면 255 자동
    @Column(length = 100, nullable = false) //길이 100 생략 불가능
    private String title; //제목


    @Column(length = 1000,nullable = false)
    private String content; //내용

    @Column(length = 30,nullable = false)
    private String writer; //작성자

    //이름은 대소문자 구분
    @CreatedDate //생성시 날짜를 자동 생성
    @Column(name="regdate",updatable = false) //updatable=false  update 불가능
    private LocalDateTime regDate; //생성일자

    @LastModifiedDate //최종 수정시의 날짜를 자동생성
    @Column(name="moddate")
    private LocalDateTime modDate; //수정일자

    //추가로 필요한 메소드 생성(getter setter 생성자를 제외한 일반 메소드)
    //부분적으로 값을 처리할 작업에 맞는 메소드를 임의로 생성
    public void changTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content=content;
    }
    public void changeWriter(String writer){
        this.writer=writer;
    }
    //실행 후 데이터베이스에서 테이블생성을 확인한 후
    //application에서 create를 none으로 설정
    //다음에 Repository 생성
}
