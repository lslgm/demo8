package com.example.demo.Repository;

import com.example.demo.Entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@EnableJpaRepositories
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
    //JpaRepository<테이블명(Entity명), 기본키의 데이터형> -> 삽입 수정 삭제 조회처리 가능
    //QuerydslPredicateExecutor<테이블명(Entity명)> -> 페이징하고 검색처리
    //DTO 이동해서 작업할 DTO생성
}
