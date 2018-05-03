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
				<li class="dropdown dropdown-notification open"><a
					class="dropdown-toggle" href="javascript:;" data-toggle="dropdown"
					data-hover="dropdown" data-close-others="true" aria-expanded="true">
						<i class="fa fa-bell-o"></i> <span class="badge badge-default">
							3 </span>
				</a>
					<ul class="dropdown-menu">
						<li class="external">
							<!-- <h3>
								<span class="bold">12 pending</span> notifications
							</h3>  -->
							<a href="page_user_profile_1.html"> 
								<span class="label label-sm label-icon label-info"> <i class="fa fa-bullhorn"></i></span>     view all
							</a>
						</li>
						<!-- <li>
							<ul class="dropdown-menu-list">
								<li><a href="javascript:;"> <span class="time">just
											now</span> <span class="details"> <span
											class="label label-sm label-icon label-success"> <i
												class="fa fa-plus"></i>
										</span> New user registered.
									</span>
								</a></li>
								<li><a href="javascript:;"> <span class="time">3
											mins</span> <span class="details"> <span
											class="label label-sm label-icon label-danger"> <i
												class="fa fa-bolt"></i>
										</span> Server #12 overloaded.
									</span>
								</a></li>
								<li><a href="javascript:;"> <span class="time">10
											mins</span> <span class="details"> <span
											class="label label-sm label-icon label-warning"> <i
												class="fa fa-bell-o"></i>
										</span> Server #2 not responding.
									</span>
								</a></li>
								<li><a href="javascript:;"> <span class="time">14
											hrs</span> <span class="details"> <span
											class="label label-sm label-icon label-info"> <i
												class="fa fa-bullhorn"></i>
										</span> Application error.
									</span>
								</a></li>
							</ul>
						</li> -->
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" aria-expanded="true"> <img alt=""
						class="img-circle" id="userpicture"
						src="${welcome.person_picture}" width="30" height="30"> <span
						class="hidden-xs"><b id="username">${welcome.person_name}
						</b></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="/staff/edit_account"><i class="fa fa-fw fa-user"></i> Edit
								Profile</a></li>
						<li><a href="/staff/change_password"><i
								class="fa fa-fw fa-cog"></i> Change Password</a></li>
						<li class="divider"></li>
						<li><a href="/staff/logout"><i
								class="fa fa-fw fa-power-off"></i> Logout</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>