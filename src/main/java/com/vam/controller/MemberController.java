package com.vam.controller;

import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vam.model.MemberVO;
import com.vam.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberservice;
	
	@Autowired
	private JavaMailSender mailSender;	
	
	
	//회원가입 페이지 이동
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public void loginGET() {
		
		logger.info("회원가입 페이지 진입");
		
	}
	
	//회원가입
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinPOST(MemberVO member) throws Exception{
		
		logger.info("join 진입");
		
		// 회원가입 서비스 실행
		memberservice.memberJoin(member);
		
		logger.info("join Service 성공");
		
		return "redirect:/main";
		
	}
	

	//로그인 페이지 이동
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public void joinGET() {
		
		logger.info("로그인 페이지 진입");
	
	
	}
	
	// 아이디 중복 검사
		@RequestMapping(value = "/memberIdChk", method = RequestMethod.POST)
		@ResponseBody
		public String memberIdChkPOST(String memberId) throws Exception{
			
			logger.info("memberIdChk() 진입");
			
			int result = memberservice.idCheck(memberId);
			
			logger.info("결과값 = " + result);
			
			if(result != 0) {
				
				return "fail";	// 중복 아이디가 존재
				
			} else {
				
				return "success";	// 중복 아이디 x
				
			}
			
		
	    }
		
		/* 이메일 인증 */
		@RequestMapping(value="/mailCheck", method=RequestMethod.GET)
		@ResponseBody
		public String mailCheckGET(String email) throws Exception{
			
			/* 뷰(View)로부터 넘어온 데이터 확인 */
			logger.info("이메일 데이터 전송 확인");
			logger.info("이메일 : " + email);
					
			/* 인증번호(난수) 생성 */
			Random random = new Random();
			int checkNum = random.nextInt(888888) + 111111;
			logger.info("인증번호 " + checkNum);
			
			/* 이메일 보내기 */
			String setFrom = "area153@naver.com";
			String toMail = email;
			String title = "회원가입 인증 이메일 입니다.";
			String content = 
					"홈페이지를 방문해주셔서 감사합니다." +
					"<br><br>" + 
					"인증 번호는 " + checkNum + "입니다." + 
					"<br>" + 
					"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";		
			
			try {
				
				MimeMessage message = mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				helper.setFrom(setFrom);
				helper.setTo(toMail);
				helper.setSubject(title);
				helper.setText(content,true);
				mailSender.send(message);
				
			}catch(Exception e) {
				e.printStackTrace();
			}		
			
			
			String num = Integer.toString(checkNum);
			return num;
			
		}
	
		
		 /* 로그인 */
	    @RequestMapping(value="login", method=RequestMethod.POST)
	    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{
	        
	        //System.out.println("login 메서드 진입");
	       // System.out.println("전달된 데이터 : " + member);
	        
	    	  HttpSession session = request.getSession(); // 두 가지의 변수를 선언 및 초기화를 진행할 것.
	    	                                             //먼저 session을 사용하기 위해 session 변수를 선언하고 request.getSession()으로 초기화.
	    	  MemberVO lvo = memberservice.memberLogin(member);
	    	  

	          if(lvo == null) {                                // 일치하지 않는 아이디, 비밀번호 입력 경우
	              
	              int result = 0;
	              rttr.addFlashAttribute("result", result);
	              return "redirect:/member/login";
	              
	          }
	          
	          session.setAttribute("member", lvo);             // 일치하는 아이디, 비밀번호 경우 (로그인 성공)
	          
	          return "redirect:/main";
	    	
	        
	    }
	 
	    /* 메인페이지 로그아웃 */ // 우리는 사용자의 정보를 "member"라고 네이밍 한 session을 삭제해야함. HttpSession api 문서를 보면 우리 목적에 맞는 session을 제거할 수 있는 메서드가 2개("invalidate()", "removeAttribute()") 있습니다. "invalidate()" 메서드의 경우 세션 전체를 무효화하는 메서드. "removeAttribute()"의 경우 특정 이름으로 네이밍 한 session 객체를 타기팅하여 삭제하는 메서드.

	   // 로그아웃을 할 경우 사용될 세션이 없기 때문에 저는 "invalidate()" 메서드를 사용하였습니다. 아래와 같이 session을 제거하는 코드와 메서드 실행 후 main페이지로 이동할 수 있도록 return문을 작성함.
	    @RequestMapping(value="logout.do", method=RequestMethod.GET)
	    public String logoutMainGET(HttpServletRequest request) throws Exception{
	        
	        logger.info("logoutMainGET메서드 진입");
	        
	        HttpSession session = request.getSession();
	        
	        session.invalidate();
	        
	        return "redirect:/main";        
	        
	    }
	    
	    /* 비동기방식 로그아웃 메서드 */
	    @RequestMapping(value="logout.do", method=RequestMethod.POST)
	    @ResponseBody
	    public void logoutPOST(HttpServletRequest request) throws Exception{
	        
	        logger.info("비동기 로그아웃 메서드 진입");
	        
	        HttpSession session = request.getSession();
	        
	        session.invalidate();
	        
	    }
	 
	 
}