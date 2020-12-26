var parameter;

$(document).ready(function () {
        getSuppliers();

        parameter = {
             id : null,
             nameSupplier : null,
             noHandphone : null,
             alamat: null
        }
})

function getSuppliers(){
    var url = get_uri() + '/api/supplier/getSuppliers';
    $('#tblSupplier').DataTable( {
            "ajax": {
                "url": url,
                "dataSrc": ""
            },
            "columns": [
                { "data": "nameSupplier" },
                { "data": "noHandphone" },
                { "data": "alamat" },
                {
                   "data": 'id',
                   "bSortable": false,
                   "mRender":function(data, type, row){
                       var htmlStr = "";
                       htmlStr += '<button onclick="findSupplierById('+data+');" class="btn btn-sm btn-primary">Edit</button>&nbsp;';
                       htmlStr += '<button onclick="deleteSupplier('+data+');" class="btn btn-sm btn-danger">Delete</button>';
                       return htmlStr;
                   }
                }
            ]
        } );
}

$("#addSupplier").click(function(){
    $("#modal-supplier").modal("show");
    clearForm();
})

$("#btnSaveSupplier").click(function(){
    parameter.nameSupplier = $("#name_supplier").val();
    parameter.noHandphone = $("#no_handphone").val();
    parameter.alamat = $("#alamat").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/supplier/createSupplier",
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
                    text: 'Data Supplier Berhasil Disimpan',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#tblSupplier").DataTable().ajax.reload();
                $("#modal-supplier").modal("hide");
            }
        }
    })
})

function findSupplierById(id){
    $.ajax({
            type:"GET",
            url:get_uri() + "/api/supplier/getById/"+id,
            contentType:"application/json",
            success:function(data){
                $("#id_supplier").val(data.id);
                $("#name_supplier_edit").val(data.nameSupplier);
                $('#alamat_edit').val(data.alamat);
                $('#no_handphone_edit').val(data.noHandphone);

                $("#modal-supplier-edit").modal("show");
            }
        })
}

function clearForm(){
    $("#name_supplier").val("");
    $("#no_handphone").val("");
    $("#alamat").val("");
}

$("#btnEditSupplier").click(function(){
    parameter.id = $("#id_supplier").val();
    parameter.nameSupplier = $("#name_supplier_edit").val();
    parameter.noHandphone = $("#no_handphone_edit").val();
    parameter.alamat = $("#alamat_edit").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/supplier/updateSupplier",
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
                    text: 'Data Supplier Berhasil Dirubah',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#tblSupplier").DataTable().ajax.reload();
                $("#modal-supplier-edit").modal("hide");
            }
        }
    })
})

function deleteSupplier(id) {
    swal({
        title: "Are you sure?",
        text: "Deleted Supplier !",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willDelete) => {
            if (willDelete) {
                $.ajax({
                    type: "POST",
                    url: get_uri() + "/api/supplier/deleteSupplier/" + id,
                    success: function (data) {
                        var obj = JSON.parse(data);
                        swal("Message", obj.status, "success").then((value) => {
                            if (value) {
                                 $("#tblSupplier").DataTable().ajax.reload();
                            }
                        });
                    }
                })
            }
        });
}