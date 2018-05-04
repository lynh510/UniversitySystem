<div id="navbar"
	class="navbar navbar-default navbar-fixed-top navbar-inverse">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#navbar-ex-collapse">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
	</div>
	<div class="collapse navbar-collapse" id="navbar-ex-collapse">
		<ul class="nav navbar-nav navbar-right">
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown" aria-expanded="true"> <img alt=""
					class="img-circle" id="userpicture"
					src="${qaCoordinator.person_picture}" width="30" height="30">
					<span class="hidden-xs"></span><b id="username">${qaCoordinator.person_name}
				</b>
			</a>
				<ul class="dropdown-menu">
					<li><a href="/qacoordinator/dashboard"><i
							class="fa fa-fw fa-plus"></i> Dash board</a></li>
					<li><a href="/qacoordinator/send_email"><i
							class="fa fa-fw fa-send"></i> Send email</a></li>
					<li><a href="/qacoordinator/edit_account"><i
							class="fa fa-fw fa-user"></i> Edit Profile</a></li>
					<li><a href="/qacoordinator/change_password"><i
							class="fa fa-fw fa-cog"></i> Change Password</a></li>
					<li class="divider"></li>
					<li><a href="/qacoordinator/logout"><i
							class="fa fa-fw fa-power-off"></i> Logout</a></li>
				</ul></li>
		</ul>
	</div>
</div>