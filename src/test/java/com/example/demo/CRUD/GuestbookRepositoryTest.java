package com.example.demo.CRUD;

import com.example.demo.Entity.Guestbook;
import com.example.demo.Repository.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest //테스트 클래스
public class GuestbookRepositoryTest {
    @Autowired //테스트하고 싶은 클래스 주입
    public GuestbookRepository guestbookRepository;
    @Test //메소드 별로 작업하고 싶거나 확인하고 싶은 부분을 메소드 구성
    public  void insertTest(){
        //범위 값 만큼 동작 후 종료
        //임의값으로 300번 반복해서 테이블에 저장하는 작업
        //i -> i값이 변할때 마다처리
        IntStream.rangeClosed(1,300).forEach(i->
                {
                    //gyestbook 생성자를 만듬
                    //.맴버변수(값)
                    Guestbook guestbook = Guestbook.builder()
                            .title("제목..."+i)
                            .content("내용..."+i)
                            .writer("작성자"+(i%10))
                            .build();
                    //Repository.save(Entity) 저장, 수정
                    System.out.println(guestbookRepository.save(guestbook));
        }
        );
    }
    @Test
    public void updateTest(){
        //수정은 수정할 레코드를 지정->수정한 내용을 변경
        //optianal<Entity>
        Optional<Guestbook> result = guestbookRepository.findById(1L); //수정할 레코드번호를 지정
        if(result.isPresent()){ //해당 레코드가 존재하면
            Guestbook guestbook = result.get(); //해당 레코드를 읽어 와서
            guestbook.changTitle("제목변경");
            guestbook.changeContent("내용 변경");
            guestbookRepository.save(guestbook);
        }
    }
}
