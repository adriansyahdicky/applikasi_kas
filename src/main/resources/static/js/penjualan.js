var parameter;

$(document).ready(function () {
        getPembelian();

        $("#show-hide-content").addClass("show");
        $("#show-hide-content2").addClass("hide");
        $('.select2').select2();

        parameter = {
             idpembelian : null,
             tipe_pembayaran: null
        }


})

function getPembelian(){
    var url = get_uri() + '/api/pembelian/getPembelians';
    $('#tblPenjualan').DataTable( {
            "ajax": {
                "url": url,
                "dataSrc": ""
            },
            "columns": [
                { "data": "tanggal" },
                { "data": "pesanan.customer.nameCustomer" },
                { "data": "status" },
                {
                   "data": 'id',
                   "bSortable": false,
                   "mRender":function(data, type, row){
                       var htmlStr = "";
                       htmlStr += '<button onclick="getPenjualan('+data+');" class="btn btn-sm btn-success">Get Pembelian</button>';
                       return htmlStr;
                   }
                }
            ]
        } );
}

function getPenjualan(id){
        $("#itemPenjualan > tbody tr").empty();
        $("#show-hide-content").addClass("hide");
        $("#show-hide-content2").removeClass("hide");
        $("#show-hide-content2").addClass("show");

        $.ajax({
                type:"GET",
                url:get_uri() + "/api/pembelian/getPembelianDetail/"+id,
                contentType:"application/json",
                success:function(data){

                    $("#tanggal_penjualan").val(data.tanggal);
                    $("#id_pembelian").val(data.pembelian_details[0].pembelian.id);
                    for(var i=0; i<data.pembelian_details.length; i++){
                        $("#itemPenjualan > tbody:last-child").append("<tr> <td class='nama_barang'>"+data.pembelian_details[i].nameBarang+"</td> <td class='qty_barang'>"+data.pembelian_details[i].qty+"</td> <td class='harga_barang'>"+data.pembelian_details[i].harga+"</td>  </tr>");
                    }
                }
            })
}

$("#btnSavePenjualan").click(function(){

    if($("#tipe_pembayaran").val() === "" || $("#tipe_pembayaran").val() === null){
        alert("Tipe Pembayaran Belum Dipilih");
        return false;
    }
            parameter.idpembelian = parseInt($("#id_pembelian").val());
            parameter.tipe_pembayaran = $("#tipe_pembayaran").val();

            $.ajax({
                  type:"POST",
                  url:get_uri() + "/api/penjualan/savePenjualan",
                  data:JSON.stringify(parameter),
                  contentType:"application/json",
                            success:function(data){
                                var obj = JSON.parse(data);
                                if(obj.status === "Success"){
                                     $.toast({
                                       heading: 'Success',
                                       text: 'Data Penjualan Berhasil Disimpan',
                                       position: 'top-right',
                                       stack: false,
                                       icon: 'success'
                                     });
                                     $("#itemPenjualan > tbody tr").empty();
                                     $("#show-hide-content").removeClass("hide");
                                     $("#show-hide-content").addClass("show");
                                     $("#show-hide-content2").addClass("hide");
                                     $("#tblPenjualan").DataTable().ajax.reload();
                                }
                                else{
                                   $.toast({
                                       heading: 'Error',
                                       text: obj.message,
                                       position: 'top-right',
                                       stack: false,
                                       icon: 'error'
                                   });
                                    $("#itemPenjualan > tbody tr").empty();
                                    $("#show-hide-content").removeClass("hide");
                                    $("#show-hide-content").addClass("show");
                                    $("#show-hide-content2").addClass("hide");
                                    $("#tblPenjualan").DataTable().ajax.reload();
                               }
                      }
                  })

})