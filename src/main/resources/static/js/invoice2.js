$(document).ready(function () {
        getPenjualans();
})

function getPenjualans(){
    var url = get_uri() + '/api/penjualan/getPenjualans';
    $('#tblPenjualan').DataTable( {
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
                       htmlStr += '<a href="http://localhost:8787/api/penjualan/exportinvoice2/'+data+'" class="btn btn-primary">Export Invoice</>';
                       return htmlStr;
                   }
                }
            ]
        } );
}
