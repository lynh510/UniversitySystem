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
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" aria-expanded="true"> <img alt=""
						class="img-circle" id="userpicture"
						src="${welcome.person_picture}" width="30" height="30"> <span
						class="hidden-xs"><b id="username">${welcome.person_name}
						</b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a
							href="/student/activities/${helper.encryptID(welcome.id)}/1"><i
								class="fa fa-fw fa-history"></i> View Activity Log</a></li>
						<li><a href="/student/submit_idea"><i
								class="fa fa-fw fa-plus"></i> Add new idea</a></li>
						<li><a href="/student/edit_account"><i class="fa fa-fw fa-user"></i> Edit
								Profile</a></li>
						<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
								Password</a></li>
						<li class="divider"></li>
						<li><a href="/student/logout"><i
								class="fa fa-fw fa-power-off"></i> Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>