var parameter;

$(document).ready(function () {
        getCustomers();

        parameter = {
             id : null,
             nameCustomer : null,
             noHandphone : null,
             alamat: null,
             noRekening: null
        }
})

function getCustomers(){
    var url = get_uri() + '/api/customer/getCustomers';
    $('#tblCustomer').DataTable( {
            "ajax": {
                "url": url,
                "dataSrc": ""
            },
            "columns": [
                { "data": "nameCustomer" },
                { "data": "noHandphone" },
                { "data": "alamat" },
                {
                   "data": 'id',
                   "bSortable": false,
                   "mRender":function(data, type, row){
                       var htmlStr = "";
                       htmlStr += '<button onclick="findCustomerById('+data+');" class="btn btn-sm btn-primary">Edit</button>&nbsp;';
                       htmlStr += '<button onclick="deleteCustomer('+data+');" class="btn btn-sm btn-danger">Delete</button>';
                       return htmlStr;
                   }
                }
            ]
        } );
}

$("#addCustomer").click(function(){
    $("#modal-customer").modal("show");
    clearForm();
})

$("#btnSaveCustomer").click(function(){
    parameter.nameCustomer = $("#name_customer").val();
    parameter.noHandphone = $("#no_handphone").val();
    parameter.alamat = $("#alamat").val();
    parameter.noRekening = $("#no_rekening").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/customer/createCustomer",
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
                    text: 'Data Customer Berhasil Disimpan',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#tblCustomer").DataTable().ajax.reload();
                $("#modal-customer").modal("hide");
            }
        }
    })
})

function findCustomerById(id){
    $.ajax({
            type:"GET",
            url:get_uri() + "/api/customer/getById/"+id,
            contentType:"application/json",
            success:function(data){
                $("#id_customer").val(data.id);
                $("#name_customer_edit").val(data.nameCustomer);
                $('#alamat_edit').val(data.alamat);
                $('#no_handphone_edit').val(data.noHandphone);
                $('#no_rekening_edit').val(data.noRekening);
                $("#modal-customer-edit").modal("show");
            }
        })
}

function clearForm(){
    $("#name_customer").val("");
    $("#no_handphone").val("");
    $("#no_rekening").val("");
    $("#alamat").val("");
}

$("#btnEditCustomer").click(function(){
    parameter.id = $("#id_customer").val();
    parameter.nameCustomer = $("#name_customer_edit").val();
    parameter.noHandphone = $("#no_handphone_edit").val();
    parameter.alamat = $("#alamat_edit").val();
    parameter.noRekening = $("#no_rekening_edit").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/customer/updateCustomer",
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
                    text: 'Data Customer Berhasil Dirubah',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#tblCustomer").DataTable().ajax.reload();
                $("#modal-customer-edit").modal("hide");
            }
        }
    })
})

function deleteCustomer(id) {
    swal({
        title: "Are you sure?",
        text: "Deleted Customer !",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: "POST",
                    url: get_uri() + "/api/customer/deleteCustomer/" + id,
                    success: function (data) {
                        debugger;
                        var obj = JSON.parse(data);
                        swal("Message", obj.status, "success").then((value) => {
                            if (value) {
                                 $("#tblCustomer").DataTable().ajax.reload();
                            }
                        });
                    }
                })
            }
        });
}