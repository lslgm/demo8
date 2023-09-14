package com.example.demo.Controller;

import com.example.demo.DTO.GusetbookDTO;
import com.example.demo.DTO.PageRequestDTO;
import com.example.demo.Service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller //컨트롤 클래스로 선언
@Log4j2 //log처리
@RequiredArgsConstructor //Autowired 대체로 자동 주입
public class GuestController {
    //반드시 service를 지정
    //          서비스클래스 이름   컨트롤러에서 사용할 이름
    private final GuestService guestService;

    //각 매핑별 작업을 지정
    //@GetMapping 페이지 연결, 일반 href 버튼으로 클릭했을때(조회 삭제)
    //@PostMapping Form 처리시 (삽입 수정)
    //@GetMapping("/매핑명") 여러개의 매핑 사용시 GetMapping({"/매핑명","/매핑명"})
    //return에 같은 파일인데 주고받는 내용이 다르면
    //반드시 redirect를 이용해 연결
    //시작페이지 브라우저에서 http://localhost:8080/ 시 처리
    //String을 void 선언하면 매핑이름으로 파일이 연결결
    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    //목록페이지
    //검색분류 검색어 현재페이지... 가져와서 해당 정보를 읽어서 화면 목록출력
    @GetMapping("/guestbook/list")
    //                    html에서 전달받은 내용         html에 전달할 값
    public String list(PageRequestDTO pageRequestDTO , Model model){
        //html에 실질적으로 전달할 값 "변수명" 중요!!!!
        model.addAttribute("result",guestService.getList(pageRequestDTO));
        return "guestbook/list";
    }
    //등록/수정은 폼이동 작업, 데이터베이스 처리 작업

    //게시물 등록
    @GetMapping("/guestbook/register") //화면으로이동
    public String register(){
        return "guestbook/register";
    }
    //등록폼에서 입력한 내용을 DTO로 컨트롤에 전달해서 서비스 처리하고, 지정한 페이지로 이동
    //DTO를 받아서 서비스 처리를 하고 gno를 컨트롤에 전달... 등록이 아닌 목록페이지로 이동
    @PostMapping("/guestbook/register") //서비스 작업
    //                     html에서 받은값(현재위치 등록)을 목록에 값을 전달
    public String registerPost(GusetbookDTO dto, RedirectAttributes redirectAttributes){

        Long gno=guestService.register(dto); //게시물번호를 결과로 받는다.
        redirectAttributes.addFlashAttribute("msg",gno); //다른페이지에 결과값을 전달
        return "redirect:/guestbook/list"; //등록 후 목록페이지로 이동
    }
    //값전달시
    //return "주소" -> model 선언
    //return "redirect:주소" redirectAttribute로 선언

    //상세페이지(상세페이지(보기만)-> 수정페이지(활성화 입력가능))
    //상세페이지에 자료와 수정페이지의 자료가 동일
    @GetMapping({"/guestbook/read", "/guestbook/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model){
        GusetbookDTO gusetbookDTO = guestService.read(gno); //해당 자료를 읽어 온다
        model.addAttribute("dto",gusetbookDTO); //읽어온 자료를 전달
        //return "/guestbook/read";
    }

    //수정페이지 이동, 서비스작업
   /* @GetMapping("/guestbook/modify")
    public String modify(Long gno, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model){
        GusetbookDTO gusetbookDTO = guestService.read(gno); //해당 자료를 읽어 온다
        model.addAttribute("dto",gusetbookDTO); //읽어온 자료를 전달
        return "/guestbook/modify";
    }*/

    //수정처리(수정페이지에서 수정내용을 받아서-> 서비스에 처리->목록으로 이동
    @PostMapping("/guestbook/modify")
    public String modifyPost(GusetbookDTO dto,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                             RedirectAttributes redirectAttributes){
        guestService.modify(dto); //수정작업
        //상세보기에 필요한 값 저장(변수값을 전달)
        redirectAttributes.addAttribute("gno",dto.getGno());
        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type",pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword",pageRequestDTO.getKeyword());
        return "redirect:/guestbook/read"; //상세보기로 이동
    }

    @PostMapping("/guestbook/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        guestService.remove(gno);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }
    //컨트롤에 기본 매핑 작업 6~7개정도로 구성
    //Get           Get         Get     Get     Post    Post    Post
    //목록폼 이동, 삽입폼 이동 수정폼 이동 상세폼이동 삽입처리 수정처리 삭제처리=>가장 기본작업
}
