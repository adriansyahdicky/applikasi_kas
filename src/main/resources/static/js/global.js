function get_uri(){
    return window.location.origin;
}

function formatRupiah(angka){
            var	number_string = angka.toString(),
            	sisa 	= number_string.length % 3,
            	rupiah 	= number_string.substr(0, sisa),
            	ribuan 	= number_string.substr(sisa).match(/\d{3}/g);

            if (ribuan) {
            	separator = sisa ? ',' : '';
            	rupiah += separator + ribuan.join(',');
            }
			return rupiah;
		}



function ShowHideModal(modalName) {
    if ($("#" + modalName + "").hasClass('in')) {

        $("#" + modalName + "").modal('hide');
    } else {
        $("#" + modalName + "").modal('show');
    }
}