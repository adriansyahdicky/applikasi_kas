var itemArray = [];
var parameter;

$(document).ready(function () {

        var date = new Date();
        var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());

        parameter = {
                tanggal : null,
                customer : null,
                pesananDetailDTOS: null
        }

      $('#tanggal').datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy-mm-dd'
      })

      $( '#tanggal' ).datepicker( 'setDate', today );

      $("#cboCustomer").select2({
                  placeholder: "-Selected Customer-",
                  minimumInputLength: 2,
                  ajax:{
                      url: get_uri() + '/api/customer/searchCustomerByName',
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

      $(document).on('click', '.btn-remove', function(){
          debugger;
          var idnamabarang = $(this).attr("id");
          var index = -1;
          var filteredObj = itemArray.find(function(item, i){
            debugger;
            var cek_nama_barang = idnamabarang;
            if(item.idRow === cek_nama_barang){
              index = i;
              return i;
            }
          });
          if(itemArray.length === 1){
            itemArray = [];
          }
          else{
            itemArray.splice(index,index);
          }
          $("#row"+idnamabarang+"").remove();
      })
})

$("#btnAdd").click(function(){
    var nameBarang = $("#name_barang").val();
    var qty = $("#txtQtyItem").val();
    if(nameBarang === null || nameBarang === undefined || nameBarang === ""){
        alert("Nama Barang Tidak Boleh Kosong");
    }
    else if(qty === null || qty === undefined || qty === ""){
        alert("Qty Harus Disi");
    }
    else{
        const escapeRegExp = (string) => {
           return string.replace(/[& ]/g, '_');
        }
        var idrow = escapeRegExp(nameBarang);

        if(itemArray.length != 0){
               var cekNameBarang = $.grep(itemArray, function (n, i) {
                   return n.nameBarang == nameBarang;
               });

               if(cekNameBarang.length === 0 ){
                    $("#itemPesanan").append("<tr id='row"+idrow+"'> <td><span>"+nameBarang+"</span></td> <td><span>"+$("#txtQtyItem").val()+"</span></td> <td><button type='button' class='btn btn-danger btn-remove' id="+idrow+">X</button></td> </tr>");
                      itemArray.push({
                         'idRow': idrow,
                         'nameBarang' : nameBarang,
                         'qty': $("#txtQtyItem").val()
                      });
               }
               else{
                     alert("Nama Barang Sudah Ada Di Daftar Item");
               }
        }
        else{
            $("#itemPesanan").append("<tr id='row"+idrow+"'> <td><span>"+nameBarang+"</span></td> <td><span>"+$("#txtQtyItem").val()+"</span></td> <td><button type='button' class='btn btn-danger btn-remove' id="+idrow+">X</button></td> </tr>");
               itemArray.push({
                 'idRow': idrow,
                 'nameBarang' : nameBarang,
                 'qty': $("#txtQtyItem").val()
               });
        }

        $("#name_barang").val("");
        $("#txtQtyItem").val("");
    }
});

$("#btnSave").click(function(){
    if(itemArray.length === 0){
        alert("Silahkan Isi Item Terlebih Dahulu !");
    }
    else if($("cboCustomer").val() === "" && $("cboCustomer").val() === null){
        alert("Customer Belum Dipilih !");
    }
    else{
        parameter.tanggal = $("#tanggal").val();
        parameter.customer = parseInt($("#cboCustomer").val());
        parameter.pesananDetailDTOS = itemArray;

        $.ajax({
              type:"POST",
              url:get_uri() + "/api/pesanan/savePesanan",
              data:JSON.stringify(parameter),
              contentType:"application/json",
                        success:function(data){
                            var obj = JSON.parse(data);
                            if(obj.status === "Success"){
                                 $.toast({
                                   heading: 'Success',
                                   text: 'Data Pesanan Berhasil Disimpan',
                                   position: 'top-right',
                                   stack: false,
                                   icon: 'success'
                                 });
                                 $("#itemPesanan > tr").empty();
                                 itemArray = [];
                                 var customerSelect = $('#cboCustomer');
                                                             var option = new Option("", "", true, true);
                                                             customerSelect.append(option).trigger('change');
                                                             customerSelect.trigger({
                                                                type: 'select2:select'
                                                             });
                            }
                            else{
                               $.toast({
                                   heading: 'Error',
                                   text: obj.message,
                                   position: 'top-right',
                                   stack: false,
                                   icon: 'error'
                               });
                               $("#itemPesanan > tr").empty();
                               itemArray = [];
                               var customerSelect = $('#cboCustomer');
                               var option = new Option("", "", true, true);
                               customerSelect.append(option).trigger('change');
                               customerSelect.trigger({
                                    type: 'select2:select'
                               });
                           }
                  }
              })
    }
})