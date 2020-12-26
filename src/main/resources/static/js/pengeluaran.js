var parameter;

$(document).ready(function () {

        var date = new Date();
        var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());

        parameter = {
                tanggal : null,
                keterangan : null,
                total: null
        }

      $('#tanggal').datepicker({
            autoclose: true,
            todayHighlight: true,
            format: 'yyyy-mm-dd'
      })

      $( '#tanggal' ).datepicker( 'setDate', today );
});

$("#btnSave").click(function(){
    parameter.tanggal = $("#tanggal").val();
    parameter.keterangan = $("#keterangan").val();
    parameter.total = $("#total_pembayaran").val();

    $.ajax({
        type:"POST",
        url:get_uri() + "/api/pengeluaran/savePengeluaran",
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

                $("#keterangan").val("");
                $("#total_pembayaran").val("");
            }
            else{
                $.toast({
                    heading: 'Success',
                    text: 'Data Pengeluaran Berhasil Disimpan',
                    position: 'top-right',
                    stack: false,
                    icon: 'success'
                })
                $("#keterangan").val("");
                $("#total_pembayaran").val("");
            }
        }
    })
})