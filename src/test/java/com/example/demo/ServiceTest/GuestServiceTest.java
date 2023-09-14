package com.example.demo.ServiceTest;

import com.example.demo.DTO.GusetbookDTO;
import com.example.demo.Service.GuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//1.데이터베이스 연결 테스트
//2. repository 데이터베이스 조작 테스트(삽입 수정 삭제 조회)
//3. Service 연동 테스트(service에 선언된 모든 메소드)
@SpringBootTest
public class GuestServiceTest {
    @Autowired

    private GuestService guestService;

    @Test
    public void testRegister(){ // 등록 테스트
        //가상의 컨트롤에서 값을 전달
        GusetbookDTO gusetbookDTO = GusetbookDTO.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .writer("테스터")
                .build();
        //                  컨트롤러에게 받아온값   /    컨트롤러에 전달할 값
        //guestService.register(GusetbookDTO gusetbookDTO) Long
        System.out.println(guestService.register(gusetbookDTO));
    }

    @Test
    public void testRemove(){ //레코드 삭제
        Long gno = 5L;
        //컨트롤러에서 전달 받은 입력값 서비스가 컨트롤러에 전달값
        //Remove(Long gno )         void
        guestService.remove(gno);

    }
}
