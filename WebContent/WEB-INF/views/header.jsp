<%@page import="org.tfds.AppConstants"%>
<%@page import="org.tfds.beans.Account"%>
<head>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.10/css/all.css" integrity="sha384-+d0P83n9kaQMCwj8F4RJB66tzIwOKmrdb46+porD/OvrJ+37WqIM7UoBtwHO6Nlg"
        crossorigin="anonymous">
		<%
			// Show the error string if there is one
			String errorString = (String) session.getAttribute(AppConstants.ERROR_STRING);
                                                
			if (errorString != null) {
			%>
			<script>
//				alert("${errorString}")
			</script>
			<%
                        	
			// Remove the error string
			session.removeAttribute(AppConstants.ERROR_STRING);
			}
		%>
</head>
            <div class="col-md-2 d-none d-md-block bg-light sidebar">
                <div>
                    <img class="rounded mx-auto d-block img-thumbnail" style = "width:50%" src="${pageContext.request.contextPath }/img/logo.png" alt="Logo">
                </div>
                <div class="sidebar-sticky">
                    <ul class="nav flex-column">
						<%
                		Account userAcc = (Account) session.getAttribute(AppConstants.SESSION_ACCOUNT);
                        if(userAcc != null){
                        	
                     
						if(userAcc.getAccountType().equals(Account.AccountType.CUSTOMER)){
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/myprofile">
								<i class = "fa fa-user-circle"></i>
                                Profile
                            </a>
                            <span class="sr-only">
                                (current)
                            </span>
                        </li>
						<li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/myprofile/dates">
								<i class = "fa fa-heart"></i>
                                Dates
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/myprofile/likes">
								<i class = "fa fa-thumbs-up"></i>
                                Likes
                            </a>
                        </li>
                        <% } %>
                        
                        <%
						if(!userAcc.getAccountType().equals(Account.AccountType.CUSTOMER)){
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/employee">
								<i class = "fa fa-home"></i>
                                Dashboard
                            </a>
                        </li>
                        
                        <%} %>
                                                <%
						if(userAcc.getAccountType().equals(Account.AccountType.CUSTOMER)){
                        %>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/stats">
								<i class = "fa fa-area-chart"></i>
                                Stats
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/customer/settings">
								<i class = "fa fa-gear"></i>
                                Settings
                            </a>
                        </li>
                        <% } %>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/profile-search/">
								<i class = "fa fa-search"></i>
                                Search
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/logout">
								<i class = "fa fa-share-square"></i>
                                Logout
                            </a>
                        </li>
                        <% } %>
                    </ul>
                </div>
            </div>