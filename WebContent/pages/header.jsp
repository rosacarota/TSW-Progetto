<%@ page import="control.utente.Login" %>

<% Integer tipoUtente = (Integer) request.getSession().getAttribute("tipoUtente"); %>
<script type="text/javascript">
	let contextPath = '<%= request.getContextPath() %>'
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
	<nav class="stickynavbar">
	<a href="#" class = "whiTee a">whiTee</a>
	<a href="${pageContext.request.contextPath}/Catalogo" class="a">Home</a>
	<div class="nav-right">
	    <%-------------- Blocco utente non registrato ----------%>
	    <% if (tipoUtente == null) { %>
			<label><input placeholder="Cerca..." class="inputSearch" name="searchText" type="text"></label>
<%--    		<button id="srcBtn">--%>
<%--    			<img alt="search" src="${pageContext.request.contextPath}/images/system/search.png" id="srcImg">--%>
<%--    		</button>--%>
	    <a href="${pageContext.request.contextPath}/pages/login.jsp" id="login-icon" class="a">Login</a>
	    <% } %>
	    <%------------------------------------------------------%>
	
	    <%-------------- Blocco utente registrato ----------%>
	    <% if (tipoUtente != null && tipoUtente.equals(Login.REGISTRATO)) { %>
	    <form id="searchForm" action="#" method="#">
	    	<input placeholder="Cerca..." class="inputSearch" name="searchText" type="text">
<%--    		<button id="srcBtn">--%>
<%--    			<img alt="search" src="${pageContext.request.contextPath}/images/system/search.png" id="srcImg">--%>
<%--    		</button>--%>
	    </form>
	    <a href="${pageContext.request.contextPath}/pages/profilo.jsp" id="login-icon">Profilo</a>
	    <% } %>
	    <%--------------------------------------------------%>
	
	    <%-------------- Blocco admin ----------%>
	    <% if (tipoUtente != null && tipoUtente.equals(Login.ADMIN)) { %>
	    <a href="${pageContext.request.contextPath}/Logout" id="">Logout</a>
	    <% } %>
	    <%--------------------------------------%>

	    <!-- L'admin è l'unico a non vedere il carrello -->
	    <% if (tipoUtente == null || !tipoUtente.equals(Login.ADMIN)) { %>
			<script src="https://cdn.lordicon.com/bhenfmcm.js" integrity="sha384-VY539ll5TIagHE4WlmKaJKJ4gKxfKtGxK0MgVqVuFG4RXvATOK4KWfapoPR/PE9K" crossorigin="anonymous"></script>
	    	<lord-icon src="https://cdn.lordicon.com/lpddubrl.json" trigger="hover" colors="primary:#e88c30,secondary:#30c9e8" stroke="85" class="cart" onclick="location.href = '${pageContext.request.contextPath}/pages/carrello.jsp';"></lord-icon>
	    <% } %>
	    
		<div id="mySidenav" class="sidenav">
			<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
			<a href="#">About</a>
			<a href="#">Services</a>
			<a href="#">Clients</a>
			<a href="#">Contact</a>
		</div>
		
		<div id="main">
		  	<span id="open" style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span>
		</div>
		
		<script>
			function openNav() {
			  	document.getElementById("mySidenav").style.width = "250px";
			  	document.getElementById("main").style.marginRight = "250px";
			  	document.getElementById("open").style.display="none";
			}
			
			function closeNav() {
			  	document.getElementById("mySidenav").style.width = "0";
			  	document.getElementById("main").style.marginRight = "0";
			  	document.getElementById("open").style.display= "block";
			}
		</script>
	</div>
</nav>