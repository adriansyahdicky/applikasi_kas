var parameter;

$(document).ready(function () {
    loadDate();
    parameter = {
                    tglMasuk : null,
                    tglKeluar : null,
            }
});

function loadDate() {
    $("#date_from").kendoDatePicker({
        format: "yyyy-MM-dd"
    });

    $("#date_to").kendoDatePicker({
        format: "yyyy-MM-dd"
    });
}

$("#btn-search-fuel").click(function(){

    if(($("#date_from").val() === null || $("#date_from").val()==="") && ($("#date_to").val() === null || $("#date_to").val()==="")){
        alert("Date From dan Date To Harus Diisi terlebih dahulu");
    }
    else{

        parameter.tglMasuk = $("#date_from").val();
        parameter.tglKeluar = $("#date_to").val();
        GenerateGrid(parameter);

    }

})

function GenerateGrid(parameter) {
    $("#gridview").empty();
    $("#gridview").append("<div id='gvLaporanPenjualan' style='width:100%;'></div>");

        var dataSource = new kendo.data.DataSource({
            transport: {
                read: {
                    url: get_uri() + "/api/penjualan/laporanpenjualan/"+parameter.tglMasuk+"/"+parameter.tglKeluar,
                    contentType: "application/json;charset=UTF-8",
                    method: "POST"
                },
                 parameterMap: function (data) {
                            // Mapping between Spring data pagination and kendo UI pagination parameters
                            // Pagination
                            var serverUrlParams = {
                              // pagination
                              size: data.pageSize,
                              page: data.page = data.page - 1// as Spring page starts from 0
                            };

                            // Sorting
                            if (data.sort && data.sort.length > 0)
                              serverUrlParams.sort = data.sort[0].field + ',' + data.sort[0].dir;

                            return serverUrlParams;
                }
            },
            batch: true,
            page: 0,
            pageSize: 20,
            serverPaging: true,
            schema: {
                data: "data",
                total: "total_rows",
            }
        });

        grid = $("#gvLaporanPenjualan").kendoGrid({
            dataSource: dataSource,
            sortable: false,
            scrollable: true,
            toolbar: ['excel'],
            //height: 500,
            pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: [{
                title: "No",
                template: '<div align="center">#= ++No #</div>',
                width: 50
            },{
                field: "id",
                hidden: true
            },{
                field: "tanggal",
                title: "Tanggal",
                filterable: false,
                width: 300
            }, {
                field: "pembelian.pesanan.customer.nameCustomer",
                title: "Customer",
                filterable: false,
                width: 300
            },
            {
                            field: "pembelian.pesanan.customer.alamat",
                            title: "Alamat",
                            filterable: false,
                            width: 300
                        },
                        {
                                                    field: "pembelian.pesanan.customer.noHandphone",
                                                    title: "No Handphone",
                                                    filterable: false,
                                                    width: 100
                                                },
            {
                            field: "status",
                            title: "Status",
                            filterable: false,
                            width: 250
            }],
            dataBound: function (e) {
            },
            dataBinding: function () {
                No = (this.dataSource.page() - 1) * this.dataSource.pageSize();
            }
        }).data("kendoGrid");

}