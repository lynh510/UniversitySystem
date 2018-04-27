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
					class="img-circle" id="userpicture" src="${admin.person_picture}"
					width="30" height="30"> <span class="hidden-xs"></span><b
					id="username"> ${admin.person_name} </b>
			</a>
				<ul class="dropdown-menu">
					<li><a href="/admin/manage_user"><i
							class="fa fa-fw fa-suitcase"></i> Manage User</a></li>
					<li><a href="/admin/statistic"><i
							class="fa fa-fw fa-bar-chart-o"></i> System Statistics</a></li>
					<li><a href="#"><i class="fa fa-fw fa-user"></i> Edit
							Profile</a></li>
					<li><a href="#"><i class="fa fa-fw fa-cog"></i> Change
							Password</a></li>
					<li class="divider"></li>
					<li><a href="/admin/logout"><i
							class="fa fa-fw fa-power-off"></i> Logout</a></li>
				</ul></li>
		</ul>
	</div>
</div>