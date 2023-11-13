<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userid = (String) session.getAttribute("userid");
%>
<%
	Integer uniqueid = (Integer) session.getAttribute("uniqueid");
%>
<%@ page import="jun.board.dao.BoardDAO"%>
<%@ page import="jun.board.dto.BoardDTO"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>My Page</title>
<!-- Bootstrap CSS-->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
<style>
.navbar-nav .nav-item a:hover {
	background-color: lightgray;
	padding: 10px; /* 원하는 크기로 조정 */
	border-radius: 4px; /* 원하는 크기로 조정 */
}

.notice-link {
	color: #000; /* 링크 색상 설정 */
	text-decoration: none; /* 밑줄 제거 */
}

.notice-link:hover {
	color: #f00; /* 마우스 호버 시 색상 변경 */
	text-decoration: underline; /* 밑줄 추가 */
}
</style>

</head>
<body>
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container px-4 px-lg-5">
			<a class="navbar-brand" href="./MainView.do">사이트 명</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="./MainView.do">Home</a></li>
					<li class="nav-item"><a class="nav-link"
						href="./NoticeView.board">Notice</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" id="navbarDropdown" href="#"
						role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
						<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="./GoodsListView.do">All
									Products</a></li>
							<li><hr class="dropdown-divider" /></li>
							<li><a class="dropdown-item" href="#">Popular Items</a></li>
							<li><a class="dropdown-item" href="#">New Arrivals</a></li>
						</ul></li>
				</ul>
				<ul class="navbar-nav ms-auto py-4 py-lg-0">
					<%
						if (userid == null) {
					%>
					<!-- 로그아웃 상태일 때 -->
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./MemberLoginView.do">로그인</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./Sign-upView.do">회원가입</a></li>
					<%
						} else {
					%>
					<!-- 로그인 상태일 때 -->
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./Logout.do">로그아웃</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./MypageView.do">마이페이지</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./OrderCheckView.do">주문조회</a></li>
					<li class="nav-item"><a class="nav-link px-lg-3 py-2 py-lg-4"
						href="./CartView.do">장바구니 <span
							class="badge bg-dark text-white ms-1 rounded-pill">0</span></a></li>
					<%
						}
					%>

				</ul>
			</div>
		</div>
	</nav>

	<!-- Header -->
	<header class="bg-dark py-5"
		style="background-image: url('assets/img/home-bg.jpg')">
		<div class="container px-4 px-lg-5 my-5">
			<div class="text-center text-black">
				<h1 class="display-4 fw-bolder">공지사항</h1>

			</div>
		</div>
	</header>

	<!-- Section -->
	<section class="py-5">
		<div class="container px-4 px-lg-5 mt-5 d-flex justify-content-center">
			<!-- 공지사항 목록 -->
			<div class="col-lg-9">
				<h2 class="fw-bolder">공지사항</h2>
				<table class="table mt-4">
					<%
						BoardDAO boardDAO = new BoardDAO();
						ArrayList<BoardDTO> arrayList = boardDAO.boardSelectAll();
					%>
					<thead>
						<tr>
							<th style="width: 15%;">글 번호</th>
							<th style="width: 45%;">제목</th>
							<th style="width: 20%;">글쓴이</th>
							<th style="width: 20%;">작성일</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (BoardDTO board : arrayList) {
						%>
						<tr>
							<td><%=board.getNum()%></td>
							<td><a class="notice-link"
								href="./NoticeSelect.board?num=<%=board.getNum()%>"><%=board.getTitle()%></a>
							</td>
							<td><%=board.getUserid()%></td>
							<td><%=board.getWriteday()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<%
					if (userid != null && uniqueid != null && uniqueid == 100001) {
				%>
				<div class="mt-3 d-flex justify-content-end">
					<a href="./NoticeInsertView.board">
						<button type="button" class="btn btn-primary">공지 등록</button>
					</a>

				</div>
				<%
					}
				%>
			</div>
		</div>

	</section>


	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">© Your Website 2023. All
				rights reserved.</p>
		</div>
	</footer>

	<!-- Bootstrap core JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
