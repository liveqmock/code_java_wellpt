<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/pt/common/header.jsp"%>


<body>
	<!-- topbar starts -->
	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">

				<a class="brand" href="index.jsp"> <img alt="welloa logo"
					src="${ctx}/resources/pt/img/logo20.png" /> <span>OA</span></a>

				<!-- theme selector starts -->
				<div class="btn-group pull-right theme-container">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-tint"></i><span class="hidden-phone"> Change
							Theme / Skin</span> <span class="caret"></span>
					</a>
					<ul class="dropdown-menu" id="themes">
						<li><a data-value="classic" href="#"><i
								class="icon-blank"></i> Classic</a></li>
						<li><a data-value="cerulean" href="#"><i
								class="icon-blank"></i> Cerulean</a></li>
						<li><a data-value="cyborg" href="#"><i class="icon-blank"></i>
								Cyborg</a></li>
						<li><a data-value="redy" href="#"><i class="icon-blank"></i>
								Redy</a></li>
						<li><a data-value="journal" href="#"><i
								class="icon-blank"></i> Journal</a></li>
						<li><a data-value="simplex" href="#"><i
								class="icon-blank"></i> Simplex</a></li>
						<li><a data-value="slate" href="#"><i class="icon-blank"></i>
								Slate</a></li>
						<li><a data-value="spacelab" href="#"><i
								class="icon-blank"></i> Spacelab</a></li>
						<li><a data-value="united" href="#"><i class="icon-blank"></i>
								United</a></li>
					</ul>
				</div>
				<!-- theme selector ends -->

				<!-- user dropdown starts -->
				<div class="btn-group pull-right">
					<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
						<i class="icon-user"></i><span class="hidden-phone"> admin</span>
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Profile</a></li>
						<li class="divider"></li>
						<li><a href="login.jsp">Logout</a></li>
					</ul>
				</div>
				<!-- user dropdown ends -->

				<div class="top-nav nav-collapse">
					<!-- 顶部导航 -->
					<ul class="nav">
						<li class="active"><a href="index.jsp">首页</a></li>
						<li><a href="ui.jsp">ui元素</a></li>
						<li><a href="form.jsp">表单</a></li>
						<li><a href="table.jsp">表格</a></li>
						<li>
							<form class="navbar-search pull-left">
								<input placeholder="Search" class="search-query span2"
									name="query" type="text">
							</form>
						</li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>
	<!-- topbar ends -->
	<!--內容区域 流式布局 -->
	<div class="container-fluid">
		<div class="row-fluid">

			<!-- 左侧菜单 span2-->
			<div class="span2 main-menu-span">
				<div class="well nav-collapse sidebar-nav">
					<!-- 列表导航 -->
					<ul class="nav nav-tabs nav-stacked main-menu">
						<li class="nav-header hidden-tablet">Main</li>
						<li><a class="ajax-link" href="index.jsp"><i
								class="icon-home"></i><span class="hidden-tablet"> 首页</span></a></li>
						<li><a class="ajax-link" href="ui.jsp"><i
								class="icon-eye-open"></i><span class="hidden-tablet">
									ui元素</span></a></li>
						<li><a class="ajax-link" href="form.jsp"><i
								class="icon-edit"></i><span class="hidden-tablet"> 表单</span></a></li>
						<li><a class="ajax-link" href="table.jsp"><i
								class="icon-align-justify"></i><span class="hidden-tablet">
									表格</span></a></li>
						<li><a href="jqtable.jsp"><i class="icon-ban-circle"></i><span
								class="hidden-tablet"> jqgrid表格</span></a></li>
						<li><a href="login.jsp"><i class="icon-lock"></i><span
								class="hidden-tablet"> 登录</span></a></li>
					</ul>

				</div>
				<!--/.well -->
			</div>
			<!--/span2-->
			<!-- 左侧菜单 ends -->


			<!-- 核心内容 span10-->
			<div id="content" class="span10">
				<!-- content starts -->


				<div>
					<ul class="breadcrumb">
						<li><a href="index.jsp">系统</a> <span class="divider">/</span></li>
						<li><a href="form.jsp">表单</a></li>
					</ul>
				</div>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> Form Elements
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal">
								<fieldset>
									<legend>Datepicker, Autocomplete, WYSIWYG</legend>
									<div class="control-group">
										<label class="control-label" for="typeahead">Auto
											complete </label>
										<div class="controls">
											<input type="text" class="span6 typeahead" id="typeahead"
												data-provide="typeahead" data-items="4"
												data-source='["Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Dakota","North Carolina","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"]'>
											<p class="help-block">Start typing to activate auto
												complete!</p>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="date01">Date input</label>
										<div class="controls">
											<input type="text" class="input-xlarge datepicker"
												id="date01" value="02/16/12">
										</div>
									</div>

									<div class="control-group">
										<label class="control-label" for="fileInput">File
											input</label>
										<div class="controls">
											<input class="input-file uniform_on" id="fileInput"
												type="file">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="textarea2">Textarea
											WYSIWYG</label>
										<div class="controls">
											<textarea class="cleditor" id="textarea2" rows="3"></textarea>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Save
											changes</button>
										<button type="reset" class="btn">Cancel</button>
									</div>
								</fieldset>
							</form>

						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->


				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> Form Elements
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="focusedInput">Focused
											input</label>
										<div class="controls">
											<input class="input-xlarge focused" id="focusedInput"
												type="text" value="This is focused…">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Uneditable input</label>
										<div class="controls">
											<span class="input-xlarge uneditable-input">Some value
												here</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="disabledInput">Disabled
											input</label>
										<div class="controls">
											<input class="input-xlarge disabled" id="disabledInput"
												type="text" placeholder="Disabled input here…" disabled="">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="optionsCheckbox2">Disabled
											checkbox</label>
										<div class="controls">
											<label class="checkbox"> <input type="checkbox"
												id="optionsCheckbox2" value="option1" disabled="">
												This is a disabled checkbox
											</label>
										</div>
									</div>
									<div class="control-group warning">
										<label class="control-label" for="inputWarning">Input
											with warning</label>
										<div class="controls">
											<input type="text" id="inputWarning"> <span
												class="help-inline">Something may have gone wrong</span>
										</div>
									</div>
									<div class="control-group error">
										<label class="control-label" for="inputError">Input
											with error</label>
										<div class="controls">
											<input type="text" id="inputError"> <span
												class="help-inline">Please correct the error</span>
										</div>
									</div>
									<div class="control-group success">
										<label class="control-label" for="inputSuccess">Input
											with success</label>
										<div class="controls">
											<input type="text" id="inputSuccess"> <span
												class="help-inline">Woohoo!</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="selectError3">Plain
											Select</label>
										<div class="controls">
											<select id="selectError3">
												<option>Option 1</option>
												<option>Option 2</option>
												<option>Option 3</option>
												<option>Option 4</option>
												<option>Option 5</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="selectError">Modern
											Select</label>
										<div class="controls">
											<select id="selectError" data-rel="chosen">
												<option>Option 1</option>
												<option>Option 2</option>
												<option>Option 3</option>
												<option>Option 4</option>
												<option>Option 5</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="selectError1">Multiple
											Select / Tags</label>
										<div class="controls">
											<select id="selectError1" multiple data-rel="chosen">
												<option>Option 1</option>
												<option selected>Option 2</option>
												<option>Option 3</option>
												<option>Option 4</option>
												<option>Option 5</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="selectError2">Group
											Select</label>
										<div class="controls">
											<select data-placeholder="Your Favorite Football Team"
												id="selectError2" data-rel="chosen">
												<option value=""></option>
												<optgroup label="NFC EAST">
													<option>Dallas Cowboys</option>
													<option>New York Giants</option>
													<option>Philadelphia Eagles</option>
													<option>Washington Redskins</option>
												</optgroup>
												<optgroup label="NFC NORTH">
													<option>Chicago Bears</option>
													<option>Detroit Lions</option>
													<option>Green Bay Packers</option>
													<option>Minnesota Vikings</option>
												</optgroup>
												<optgroup label="NFC SOUTH">
													<option>Atlanta Falcons</option>
													<option>Carolina Panthers</option>
													<option>New Orleans Saints</option>
													<option>Tampa Bay Buccaneers</option>
												</optgroup>
												<optgroup label="NFC WEST">
													<option>Arizona Cardinals</option>
													<option>St. Louis Rams</option>
													<option>San Francisco 49ers</option>
													<option>Seattle Seahawks</option>
												</optgroup>
												<optgroup label="AFC EAST">
													<option>Buffalo Bills</option>
													<option>Miami Dolphins</option>
													<option>New England Patriots</option>
													<option>New York Jets</option>
												</optgroup>
												<optgroup label="AFC NORTH">
													<option>Baltimore Ravens</option>
													<option>Cincinnati Bengals</option>
													<option>Cleveland Browns</option>
													<option>Pittsburgh Steelers</option>
												</optgroup>
												<optgroup label="AFC SOUTH">
													<option>Houston Texans</option>
													<option>Indianapolis Colts</option>
													<option>Jacksonville Jaguars</option>
													<option>Tennessee Titans</option>
												</optgroup>
												<optgroup label="AFC WEST">
													<option>Denver Broncos</option>
													<option>Kansas City Chiefs</option>
													<option>Oakland Raiders</option>
													<option>San Diego Chargers</option>
												</optgroup>
											</select>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Save
											changes</button>
										<button class="btn">Cancel</button>
									</div>
								</fieldset>
							</form>

						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> Form Elements
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
							<form class="form-horizontal">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="prependedInput">Prepended
											text</label>
										<div class="controls">
											<div class="input-prepend">
												<span class="add-on">@</span><input id="prependedInput"
													size="16" type="text">
											</div>
											<p class="help-block">Here's some help text</p>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="appendedInput">Appended
											text</label>
										<div class="controls">
											<div class="input-append">
												<input id="appendedInput" size="16" type="text"><span
													class="add-on">.00</span>
											</div>
											<span class="help-inline">Here's more help text</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="appendedPrependedInput">Append
											and prepend</label>
										<div class="controls">
											<div class="input-prepend input-append">
												<span class="add-on">$</span><input
													id="appendedPrependedInput" size="16" type="text"><span
													class="add-on">.00</span>
											</div>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="appendedInputButton">Append
											with button</label>
										<div class="controls">
											<div class="input-append">
												<input id="appendedInputButton" size="16" type="text">
												<button class="btn" type="button">Go!</button>
											</div>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="appendedInputButtons">Two-button
											append</label>
										<div class="controls">
											<div class="input-append">
												<input id="appendedInputButtons" size="16" type="text">
												<button class="btn" type="button">Search</button>
												<button class="btn" type="button">Options</button>
											</div>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Checkboxes</label>
										<div class="controls">
											<label class="checkbox inline"> <input
												type="checkbox" id="inlineCheckbox1" value="option1">
												Option 1
											</label> <label class="checkbox inline"> <input
												type="checkbox" id="inlineCheckbox2" value="option2">
												Option 2
											</label> <label class="checkbox inline"> <input
												type="checkbox" id="inlineCheckbox3" value="option3">
												Option 3
											</label>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">File Upload</label>
										<div class="controls">
											<input type="file">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Radio buttons</label>
										<div class="controls">
											<label class="radio"> <input type="radio"
												name="optionsRadios" id="optionsRadios1" value="option1"
												checked=""> Option one is this and that—be sure to
												include why it's great
											</label>
											<div style="clear: both"></div>
											<label class="radio"> <input type="radio"
												name="optionsRadios" id="optionsRadios2" value="option2">
												Option two can be something else and selecting it will
												deselect option one
											</label>
										</div>
									</div>
									<div class="form-actions">
										<button type="submit" class="btn btn-primary">Save
											changes</button>
										<button class="btn">Cancel</button>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->

				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->

		<hr>

			<div class="modal hide fade" id="myModal">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3>Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here settings can be configured...</p>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
						class="btn btn-primary">Save changes</a>
				</div>
			</div>

			<%@ include file="/pt/common/footer.jsp"%>
	</div>
	<!--/.fluid-container-->

	
</body>
</html>