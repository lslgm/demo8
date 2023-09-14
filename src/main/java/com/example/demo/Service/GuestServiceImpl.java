package com.example.demo.Service;

import com.example.demo.DTO.GusetbookDTO;
import com.example.demo.DTO.PageRequestDTO;
import com.example.demo.DTO.PageResultDTO;
import com.example.demo.Entity.Guestbook;
import com.example.demo.Entity.QGuestbook;
import com.example.demo.Repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service //서비스 클래스 선언
@Log4j2
@RequiredArgsConstructor //사용할 Repository를 자동 주입, 컨트롤에서도 반드시 선언
public class GuestServiceImpl implements GuestService{
    //Repository를 반드시 선언
    private final GuestbookRepository guestbookRepository; //주 작업대상

    @Override
    public Long register(GusetbookDTO gusetbookDTO) {
        Guestbook entity= dtoToEntity(gusetbookDTO);
        guestbookRepository.save(entity);
        return entity.getGno();//번호를 전달
    }

    @Override
    public void modify(GusetbookDTO gusetbookDTO) {
    //수정할 레코드를 읽어온다
        Optional<Guestbook> result = guestbookRepository.findById(gusetbookDTO.getGno());
        if(result.isPresent()){ //레코드가 전제하면
            Guestbook entity = result.get();    //읽어온 레코드를 변수에 저장
            entity.changTitle(gusetbookDTO.getTitle());
            entity.changeContent(gusetbookDTO.getContent());
            entity.changeWriter(gusetbookDTO.getWriter());
            guestbookRepository.save(entity); //수정 내용 저장
        }
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno); //해당 번호로 삭제
    }

    @Override
    public GusetbookDTO read(Long gno) {
        //해당하는 번호의 레코드를 읽어온다
        Optional<Guestbook> result = guestbookRepository.findById(gno);
        return  result.isPresent()?entityToDto(result.get()):null;

    }

    @Override
    public PageResultDTO<GusetbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        //정렬 방식(번호로 내림차순
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        //검색 조건 처리
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //
        //해당 조건에 맞는 자료를 정렬해서 읽어온다.
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
        //결과를 전달
        Function<Guestbook, GusetbookDTO> fn =(entity->entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }
    //조건을 처리하는 메소드
    public  BooleanBuilder getSearch(PageRequestDTO requestDTO){
        //메소드를 이용해서 질의어를 만들어서 제공
        String type = requestDTO.getType(); //분류
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //querydsl를 사용하기 위해서 기본 Entity이름 앞에 Q를 붙여 사용
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword= requestDTO.getKeyword(); //검색어
        //질의어 문법  where gno>0
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(expression); //기존 조건에 추가

        //검색할 분류가 없으면 더이상 조건없이 전달
        //분류가 없거나 여백제거후 길이가 0이면
        if (type==null || type.trim().length()==0){
            return booleanBuilder;
        }
        //검색할 내용이 존재하면
        BooleanBuilder conditionBuilber = new BooleanBuilder();
        if(type.contains("t")){ //분류가 t 포함되어 있으면
            //제목에 검색어가 포함되어 있으면
            conditionBuilber.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){ //분류가 c 포함되어 있으면
            //제목에 검색어가 포함되어 있으면
            conditionBuilber.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){ //분류가 w 포함되어 있으면
            //제목에 검색어가 포함되어 있으면
            conditionBuilber.or(qGuestbook.writer.contains(keyword));
        }
        //위 조건을 합친다
        // 번호는 0 보다 크고 제목에 검색어가포함되어 있거나 내용에 검색어가 포함되어 있거나 작성자에 검색어가 포함되어 있거나
        booleanBuilder.and(conditionBuilber);

        return  booleanBuilder;
    }
    //Test에서 서비스에 등록된 메소드에 대한 단위테스트 진행
}
