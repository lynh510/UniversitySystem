<jsp:useBean id="helper" class="com.system.Helper" scope="page" />
<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#navbar-ex-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse" id="navbar-ex-collapse">

			<form method="post" action="/idea/search">
				<input type="search" name="search"><input type="button"
					value="Search">
			</form>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" aria-expanded="true"> <img alt=""
						class="img-circle" id="userpicture"
						src="${welcome.person_picture}" width="30" height="30"> <span
						class="hidden-xs"><b id="username">${welcome.person_name}
						</b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/student/login"><i class="fa fa-fw fa-user"></i>
								Student login</a></li>
						<li><a href="/stafft/login"><i class="fa fa-fw fa-user"></i>
								Staff login</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>