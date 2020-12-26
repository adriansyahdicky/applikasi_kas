var parameter;
var itemArray = [];

$(document).ready(function () {
        getPesananNew();

        $("#show-hide-content").addClass("show");
        $("#show-hide-content2").addClass("hide");

        parameter = {
             idpesanan : null,
             pembelianDetailDTOS: null
        }


})

function getPesananNew(){
    var url = get_uri() + '/api/pesanan/getPesanans';
    $('#tblPesananNew').DataTable( {
            "ajax": {
                "url": url,
                "dataSrc": ""
            },
            "columns": [
                { "data": "tanggal" },
                { "data": "customer.nameCustomer" },
                { "data": "status" },
                {
                   "data": 'id',
                   "bSortable": false,
                   "mRender":function(data, type, row){
                       var htmlStr = "";
                       htmlStr += '<button onclick="getPesanan('+data+');" class="btn btn-sm btn-success">Get Pesanan</button>';
                       return htmlStr;
                   }
                }
            ]
        } );
}

function getPesanan(id){
    $("#itemPembelian > tbody tr").empty();
    itemArray = [];
    $("#show-hide-content").addClass("hide");
    $("#show-hide-content2").removeClass("hide");
    $("#show-hide-content2").addClass("show");

    $.ajax({
            type:"GET",
            url:get_uri() + "/api/pesanan/getPesananDetail/"+id,
            contentType:"application/json",
            success:function(data){

                $("#tanggal_pembelian").val(data.tanggal);
                $("#id_pesanan").val(data.pesanan_details[0].pesanan.id);
                for(var i=0; i<data.pesanan_details.length; i++){
                    $("#itemPembelian > tbody:last-child").append("<tr> <td class='nama_barang'>"+data.pesanan_details[i].nameBarang+"</td> <td class='qty_barang'>"+data.pesanan_details[i].qty+"</td> <td class='harga_barang'><input type='text' id='harga_barang_beli'></input></td> <td><select class='form-control select2 cboSupplierPembelian' id='cbo_"+data.pesanan_details[i].id+"' style='width: 100%;'></select></td> <td><button type='button' class='btn btn-primary' onclick='updatePembelian($(this), "+data.pesanan_details[i].id+")'>Update</button></td> </tr>");
                    getCboSupplier("cbo_"+data.pesanan_details[i].id);
                }
            }
        })
}

function getCboSupplier(id){
    $("#"+id).select2({
                              placeholder: "-Selected Supplier-",
                              minimumInputLength: 2,
                              ajax:{
                                  url: get_uri() + '/api/supplier/searchSupplierByName',
                                  dataType: "json",
                                  type: "GET",
                                  delay: 250,
                                  data: function(params){
                                      return{
                                          q:params.term,
                                      }
                                  },
                                  processResults:function(data){
                                      return{
                                          results: data
                                      }
                                  },
                                  cache: true
                              }
                          });
}

function updatePembelian(e, id_cbo){
    var nama_barang = e.closest("tr").find("td:eq(0)").text();
    var qty_barang = e.closest("tr").find("td:eq(1)").text();
    var harga_beli = e.closest("tr").find("td:eq(2) input[type='text']").val();
    var supplier = parseInt($("#cbo_"+id_cbo).val());
    if(harga_beli === ""){
        alert("Harga Belim Belum Diisi");
        return false;
    }
    else if(isNaN(supplier)){
        alert("Supplier Belum Dipilih");
        return false;
    }

    if(itemArray.length != 0){
        var cekNameBarang = $.grep(itemArray, function (n, i) {
             return n.nameBarang == nama_barang;
        });

        if(cekNameBarang.length != 0){
            var index = -1;
            var filteredObj = itemArray.find(function(item, i){
                if(item.nameBarang === nama_barang){
                   index = i;
                   return i;
                }
            });

            itemArray.splice(index,index);
            itemArray.push({
               'nameBarang': nama_barang,
               'qty' : qty_barang,
               'harga': harga_beli,
               'supplier': supplier
            });
        }
        else{
             itemArray.push({
                'nameBarang': nama_barang,
                'qty' : qty_barang,
                'harga': harga_beli,
                'supplier': supplier
             });
        }
    }
    else{
        itemArray.push({
                'nameBarang': nama_barang,
                'qty' : qty_barang,
                'harga': harga_beli,
                'supplier': supplier
             });
    }

    alert("Sukses Update Barang");
}

$("#btnSavePembelian").click(function(){
    var rowCount = $("#itemPembelian > tbody tr").length;
    if(itemArray.length != rowCount){
        alert("Ada Item Pembelian yang belum di selesaikan prosesnya");
        return false;
    }
            parameter.idpesanan = parseInt($("#id_pesanan").val());
            parameter.pembelianDetailDTOS = itemArray;

            $.ajax({
                  type:"POST",
                  url:get_uri() + "/api/pembelian/savePembelian",
                  data:JSON.stringify(parameter),
                  contentType:"application/json",
                            success:function(data){
                                var obj = JSON.parse(data);
                                if(obj.status === "Success"){
                                     $.toast({
                                       heading: 'Success',
                                       text: 'Data Pembelian Berhasil Disimpan',
                                       position: 'top-right',
                                       stack: false,
                                       icon: 'success'
                                     });
                                     $("#itemPembelian > tbody tr").empty();
                                     itemArray = [];
                                     $("#show-hide-content").removeClass("hide");
                                     $("#show-hide-content").addClass("show");
                                     $("#show-hide-content2").addClass("hide");
                                     $("#tblPesananNew").DataTable().ajax.reload();
                                }
                                else{
                                   $.toast({
                                       heading: 'Error',
                                       text: obj.message,
                                       position: 'top-right',
                                       stack: false,
                                       icon: 'error'
                                   });
                                   $("#itemPembelian > tbody tr").empty();
                                   itemArray = [];
                                   $("#tblPesananNew").DataTable().ajax.reload();
                                   $("#show-hide-content").removeClass("hide");
                                   $("#show-hide-content").addClass("show");
                                   $("#show-hide-content2").addClass("hide");
                               }
                      }
                  })

})