app.service("uploadService",function($http){


	// 上传图片
	this.uploadFile = function(){

		// 向后台传递数据:
		var formData = new FormData();


		// 向formData中添加数据:
		formData.append("file",file.files[0]);

		//<form    encod="multipart/formData"> 更改请求头

			//</form>

		
		return $http({
			method:'post',
			url:'../upload/uploadFile.do',
			data:formData,
			headers:{'Content-Type':undefined} ,// Content-Type : text/html  text/plain
			transformRequest: angular.identity
		});
	}
	
});