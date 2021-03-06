<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/member/login2.css">
<link rel="stylesheet" href="/resources/css/member/loginform.css?after">
 <style>
@import url('https://fonts.googleapis.com/css2?family=Jua&display=swap');
</style>
</head>
<body>

          <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" style = "font-family: 'Jua', sans-serif; font-size : x-large;" href="/main">DW's Book Store</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href=main></a></li>
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href=member/login></a></li>
                        
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href=member/join></a></li>
                        </li>
                    </ul>
                    
                    
                    <div class="login_area">
			
				<!-- ????????? ?????? ?????? ?????? -->
				<c:if test = "${member == null }">
					<div class="login_button" style = "font-family: 'Jua', sans-serif;"><a href="main"></a></div>
					
								
				</c:if>				
				
				
				
			</div>
			
			
                </div>
            </div>
        </nav>
  

<div class="wrapper fadeInDown">
  <div id="formContent">
    <!-- Tabs Titles -->

  
    <!-- Login Form -->
   	<form id="login_form" method="post">
    <div class="id_wrap">
						<div class="id_input_box">
						<input class="id_input" placeholder="???????????? ??????????????? " name="memberId">
					</div>
					</div>
      <div class="pw_wrap">
					<div class="pw_input_box">
						<input class="pw_iput" placeholder="??????????????? ??????????????? " name="memberPw">
					</div>
				</div>
				
				<c:if test = "${result == 0 }">
					<div class = "login_warn">????????? ID ?????? ??????????????? ?????? ?????????????????????.</div>
				</c:if>
				
				<div class="login_button_wrap">
					<input type="button" class="login_button" value="?????????">
				</div>			
			</div>
    </form>

    
   <div id="formFooter">
      <a class="underlineHover" href="join" >????????????</a>
    </div>

  </div>
</div>

<!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Your Website 2022</p></div>
        </footer>

<script>

	/* ????????? ?????? ?????? ????????? */
	$(".login_button").click(function(){
		
		//alert("????????? ??????");
		
		/* ????????? ????????? ?????? ?????? */
		$("#login_form").attr("action", "/member/login.do");
		$("#login_form").submit();
		
	});

</script>

</body>
</html>