var FormValidation = function () {

    // basic validation
    var handleValidation1 = function() {
        // for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation

            var form1 = $('#form_update');
            var error1 = $('.alert-danger', form1);
            var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                messages: {
                	configPwd:{
                    	equalTo:"确认密码必须和新密码一致"
                    }
                },
                rules: {
                	realname: {
                        required: true,
                        maxlength:32
                    },
                    configPwd: {
                    	equalTo:"#password",
                    	maxlength:32
                    },
                    rank: {
                        required: true,
                        digits:true
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success1.hide();
                    error1.show();
                    App.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .closest('.form-group').removeClass('has-error'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success1.show();
                    error1.hide();
                    form[0].submit(); // submit the form
                }
            });


    }

    var handleValidation2 = function() {
        // for more info visit the official plugin documentation: 
            // http://docs.jquery.com/Plugins/Validation

            var form1 = $('#form_save');
            var error1 = $('.alert-danger', form1);
            var success1 = $('.alert-success', form1);

            form1.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block help-block-error', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",  // validate all fields including form hidden input
                messages: {
                	username:{
                		remote:"用户名已存在"
                	},
                	configPwd:{
                    	equalTo:"确认密码必须和新密码一致"
                    }
                },
                rules: {
                	username:{
                		required: true,
                        maxlength:100,
                		remote:{
		                    type:"POST",
		                    url:"v_check_username.do",             
		                    data:{
		                      veryCode:function(){return $("#username").val();}
		                    } 
		                   } 
                	},
                	realname: {
                        required: true,
                        maxlength:32
                    },
                    password:{
                    	 required: true,
                    	 maxlength:100,
                    },
                    configPwd: {
                    	 required: true,
                    	 equalTo:"#password",
                    	 maxlength:100,
                    },
                    rank: {
                        required: true,
                        digits:true
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit              
                    success1.hide();
                    error1.show();
                    App.scrollTo(error1, -200);
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.form-group').addClass('has-error'); // set error class to the control group
                },

                unhighlight: function (element) { // revert the change done by hightlight
                    $(element)
                        .closest('.form-group').removeClass('has-error'); // set error class to the control group
                },

                success: function (label) {
                    label
                        .closest('.form-group').removeClass('has-error'); // set success class to the control group
                },

                submitHandler: function (form) {
                    success1.show();
                    error1.hide();
                    form[0].submit(); // submit the form
                }
            });


    }



    return {
        //main function to initiate the module
        init: function () {
            handleValidation1();
            handleValidation2();
        }

    };

}();

jQuery(document).ready(function() {
    FormValidation.init();
});