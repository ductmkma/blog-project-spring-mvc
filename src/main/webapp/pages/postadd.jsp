<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@include file="header.jsp" %>
 <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Quản lý bài viết
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Trang chủ</a></li>
        <li><a href="#">Quản lý bài viết</a></li>
        <li class="active">Quản lý bài viết</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">

      <!-- Default box -->
      <div class="box">
        <div class="box-header with-border">
          <h3 class="box-title">Thêm mới bài viết</h3>
        </div>
       <div class="box-body container">
						<div class="row">
							<form action="" method="POST" role="form">
								<div class="col-md-8">
									<div class="col-md-12">
										<input type="text" placeholder="Enter title" name="" id="input" class="form-control input-group" title="">
										<br>
										<p>Permalink:</p>
									</div>
									<div class="col-md-12">
										<textarea  class="form-control" id="postcontent" name="postcontent" rows="90" cols="80">
										</textarea>
									</div>
								</div>
								<div class="col-md-3">
									<div class="panel-group">
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a style="color:white" data-toggle="collapse" href="#collapse1">
													Publish</a>
												</h4>
											</div>
											<div id="collapse1" class="panel-collapse collapse in">
												<div class="panel-body form-group">
													<i class="fa fa-thumb-tack" aria-hidden="true"></i><b>&nbsp&nbspStatus</b>
													<select class="form-control">
														<option value="">Draft</option>
													</select><br>
													<i class="fa fa-eye" aria-hidden="true"></i><b>&nbsp&nbspVisility</b>
													<select class="form-control">
														<option value="">Draft</option>
													</select><br>
													<i class="fa fa-calendar" aria-hidden="true"></i><b>&nbsp&nbspPublish</b>
													<select class="form-control">
														<option value="">Draft</option>
													</select><br>
													<button type="submit" class="btn btn-primary" ><i class="fa fa-paper-plane" aria-hidden="true"></i> Publish</button>
												</div>
											</div>
										</div>
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h4 class="panel-title">
													<i class="fa fa-list-ul" aria-hidden="true"></i><a style="color:white" data-toggle="collapse" href="#collapse2">
													Categories</a>
												</h4>
											</div>
											<div id="collapse2" class="panel-collapse collapse in">
												<div class="panel-body form-group">
													<ul class="nav nav-tabs">
														<li class="active"><a data-toggle="tab" href="#allcate">All categories</a></li>
														<li><a data-toggle="tab" href="#mostused">Most used</a></li>
													</ul>

													<div class="tab-content">
														<div id="allcate" class="tab-pane fade in active">
															<div class="form-check">
																<label>
																	<input type="checkbox"  name="check" checked> <span class="label-text">Option 01</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 02</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 03</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 04</span>
																</label>
															</div>
														</div>
														<div id="mostused" class="tab-pane fade">
															<div class="form-check">
																<label>
																	<input type="checkbox"  name="check" checked> <span class="label-text">Option 01</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 02</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 03</span>
																</label>
															</div>
															<div class="form-check">
																<label>
																	<input type="checkbox" name="check"> <span class="label-text">Option 04</span>
																</label>
															</div>
														</div>
													</div>
													<a href="#"><i class="fa fa-plus" aria-hidden="true"></i> Add new categories</a>
												</div>
											</div>	
										</div>
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h4 class="panel-title">
													<i class="fa fa-tags" aria-hidden="true"></i><a style="color:white" data-toggle="collapse" href="#collapse3">
													Tags</a>
												</h4>
											</div>
											<div id="collapse3" class="panel-collapse collapse in">
												<div class="panel-body form-group">
													<input  type="text" id="tagsinput" value="" data-role="tagsinput">
													<button type="submit" class="btn btn-sm btn-primary">Add</button>
													<a href="#">Choose most tags used</a>
												</div>
											</div>	
										</div>
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h4 class="panel-title">
													<i class="fa fa-picture-o" aria-hidden="true"></i><a style="color:white" data-toggle="collapse" href="#collapse4">
													Image</a>
												</h4>
											</div>
											<div id="collapse4" class="panel-collapse collapse in">
												<div class="panel-body form-group">
												  	<div id="file-upload-form" class="uploader row">
														<input id="file-upload" type="file" name="avata" accept="image/*" />
														<label for="file-upload" id="file-drag"> <img id="file-image" src="#" alt="Preview" class="hidden">
															<div id="start">
																<i class="fa fa-picture-o" aria-hidden="true"></i>
																<div>Select a file or drag here</div>
																<div id="notimage" class="hidden">Please select an image</div>
																<span id="file-upload-btn" class="btn btn-primary">Select
																	a file</span>
															</div>
															<div id="response" class="hidden">
																<div id="messages"></div>
																<progress class="progress" id="file-progress" value="0">
																	<span>0</span>%
																</progress>
															</div>
														</label>
													</div>
												</div>
											</div>	
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
        <!-- /.box-body -->
        <!-- /.box-footer-->
      </div>
      <!-- /.box -->

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
<%@include file="footer.jsp" %>
<script type="text/javascript">
	$(document).ready(function(){
		//ckeditor
		CKEDITOR.replace('postcontent')
		//Tạo slug tự động
		$("#name").on('keyup',function(){
			 var title, slug;
	         //Lấy text từ thẻ input title 
	         title = $("#name").val();

	         //Đổi chữ hoa thành chữ thường
	         slug = title.toLowerCase();

	         //Đổi ký tự có dấu thành không dấu
	         slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, 'a');
	         slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, 'e');
	         slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, 'i');
	         slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, 'o');
	         slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, 'u');
	         slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, 'y');
	         slug = slug.replace(/đ/gi, 'd');
	         //Xóa các ký tự đặt biệt
	         slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
	         //Đổi khoảng trắng thành ký tự gạch ngang
	         slug = slug.replace(/ /gi, "-");
	         //Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
	         //Phòng trường hợp người nhập vào quá nhiều ký tự trắng
	         slug = slug.replace(/\-\-\-\-\-/gi, '-');
	         slug = slug.replace(/\-\-\-\-/gi, '-');
	         slug = slug.replace(/\-\-\-/gi, '-');
	         slug = slug.replace(/\-\-/gi, '-');
	         //Xóa các ký tự gạch ngang ở đầu và cuối
	         slug = '@' + slug + '@';
	         slug = slug.replace(/\@\-|\-\@|\@/gi, '');
	         //In slug ra textbox có id “slug”
	         if(title!=""){
	        	 $("#slug").val(slug+'-'+random());
	         }else{
	        	 $("#slug").val("");
	         }
	         
		});
		function random(){
			var text = "";
			  var possible = "0123456789";
			  for (var i = 0; i < 5; i++){
                text += possible.charAt(Math.floor(Math.random() * possible.length));
			  }
		   return text;
		}
	});
</script>
<script type="text/javascript" src='<c:url value="/static/bootstrap-input-tags/bootstrap-tagsinput.js"></c:url>'></script>