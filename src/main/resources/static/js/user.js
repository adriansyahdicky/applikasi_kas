var parameter;

$(document).ready(function () {
        getUsers();

        parameter = {
             firstName : null,
             lastName : null,
             email: null,
             password: null,
             role: null
        }
})

function getUsers(){
    var url = get_uri() + '/api/user/getUsers';
    $('#tblUser').DataTable( {
            "ajax": {
                "url": url,
                "dataSrc": ""
            },
            "columns": [
                { "data": "email" },
                { "data": "firstName" },
                { "data": "lastName" },
                { "data": "roles[0].name" }

            ]
        } );
}

$("#addUser").click(function(){
    $("#modal-user").modal("show");
    clearForm();
})

$("#btnSaveUser").click(function(){

    parameter.firstName = $("#first_name").val();
    parameter.lastName = $("#last_name").val();
    parameter.email = $("#email").val();
    parameter.password = $("#password").val();
    parameter.role = $("#role").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/user/registration",
        data:JSON.stringify(parameter),
        contentType:"application/json",
        success:function(data){
            var obj = JSON.parse(data);
            if(obj.status === "failure"){
                $.toast({
                    heading: 'Error',
                    text: obj.message,
                    position: 'top-right',
                    stack: false,
                    icon: 'error'
                })
            }
            else{
                $.toast({
                    heading: 'Success',
                    text: 'Data User Berhasil Disimpan',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#tblUser").DataTable().ajax.reload();
                $("#modal-user").modal("hide");
            }
        }
    })
})


function clearForm(){
    $("#first_name").val("");
    $("#last_name").val("");
    $("#email").val("");
    $("#password").val("");
    $("#role").val("");
}
