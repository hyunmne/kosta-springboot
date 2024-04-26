package com.kosta.board.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.board.dto.BoardDto;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService brdService;
	
	@Autowired
	private HttpSession session;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@GetMapping("/boardWrite")
	public String boardWrite() {
		return "writeform";
	}
	
	@PostMapping("/boardWrite")
	public String boardWrite(BoardDto boardDto, MultipartFile file, Model model) {
		try {
			brdService.boardWrite(boardDto, file);
			return "redirect:boardList";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", "게시글 작성 오류");
			return "error";
		}
	}

	
	// localhost:8090/boardList?page=1 ... : 기존 page url
	// localhost:8090/boardList/1/title/hong : pathvariable을 사용한 url
//	@GetMapping("/boardList/{page}/{type}/{word}") 
	@GetMapping( "/boardList") 
	public ModelAndView boardList(@RequestParam(name="page", required=false, defaultValue="1") Integer page,
								  @RequestParam(name="type", required=false) String type,
								  @RequestParam(name="word", required=false) String word
								  ) {
		ModelAndView mav = new ModelAndView();
		try {
			PageInfo pageInfo = PageInfo.builder().curPage(page).build();
			List<BoardDto> brdList = brdService.boardListByPage(pageInfo, type, word);
			mav.addObject("brdList", brdList);
			mav.addObject("pageInfo", pageInfo);
			mav.setViewName("boardList");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시물 전체 조회 실패");
			mav.setViewName("error");
		}
		return mav;
	}
	
	@GetMapping("/boardDetail/{num}")
	public ModelAndView boardDetail(@PathVariable Integer num) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("brd", brdService.boardDetail(num));
			mav.setViewName("boardDetail");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시물 상세 조회 실패");
			mav.setViewName("error");
		}
		return mav;
	}
	
	@GetMapping("/image/{num}")
	public void imageView(@PathVariable Integer num, HttpServletResponse response) {
		try {
			FileInputStream fis = new FileInputStream(uploadPath+num+"");
			OutputStream out = response.getOutputStream();
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/boardModify/{num}")
	public ModelAndView boardModify(@PathVariable Integer num) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("board", brdService.boardDetail(num));
			String user = (String)session.getAttribute("user"); // 세션에서 사용자 정보를 갖고 옴
			// 사용자의 좋아요 여부를 문자열로 갖고와서 like란 이름으로 jsp로 보냄
			mav.addObject("like",String.valueOf(brdService.isSelectedBrdLike(user, num)));
			mav.setViewName("modifyForm");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시물 수정 실패");
			mav.setViewName("error");
		}
		return mav;
	}
	
	@PostMapping("/boardModify")
	public ModelAndView boardModify(BoardDto boardDto, MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		try {
			brdService.boardModify(boardDto, file);
			mav.setViewName("redirect:/boardDetail/"+boardDto.getNum());
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시물 수정 실패");
			mav.setViewName("error");
		}
		return mav;
	}
	
	@ResponseBody
	@PostMapping("/boardLike")
	public String boardLike(@RequestParam("like") String like) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			// json 형태의 문자열을 파싱하여 map에 넣어주낟.
			Map<String, String> param = mapper.readValue(like, Map.class);
			// map에 있는 데이터를 json형태의 문자열로 변환해준다,
			//String j
			Boolean check = brdService.checkBoardLike
							(param.get("memberId"), Integer.parseInt(param.get("boardNum")));
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "none";
		}
	}
}
