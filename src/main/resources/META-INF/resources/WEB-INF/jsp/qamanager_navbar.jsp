<div class="navbar navbar-default navbar-fixed-top navbar-inverse">
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
					src="${qaManager.person_picture}" width="30" height="30"> <span
					class="hidden-xs"></span><b id="username">
						${qaManager.person_name} </b>
			</a>
				<ul class="dropdown-menu">
					<li><a href="/tag/addCategory"><i class="fa fa-fw fa-plus"></i>
							Add new tag</a></li>
					<li><a href="/tag/listCategory"><i
							class="fa fa-fw fa-suitcase"></i> Tag management</a></li>
					<li><a href="/qamanager/contributions"><i
							class="fa fa-fw fa-download"></i> Download contributions</a></li>
					<li><a href="/qamanager/edit_account"><i
							class="fa fa-fw fa-user"></i> Edit Profile</a></li>
					<li><a href="/qamanager/change_password"><i
							class="fa fa-fw fa-cog"></i> Change Password</a></li>
					<li class="divider"></li>
					<li><a href="/qamanager/logout"><i
							class="fa fa-fw fa-power-off"></i> Logout</a></li>
				</ul></li>
		</ul>
	</div>
</div>