<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="/Community/js/jquery.js" type="text/javascript"></script>
<script src="/Community/js/modernizr-1.6.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/Community/css/reset.css" media="screen" />
<link rel="stylesheet" type="text/css" href="/Community/css/style.css" media="screen" />
<!--Superfish-->
<link rel="stylesheet" type="text/css" href="/Community/css/superfish.css" media="screen" />
<script type="text/javascript" src="/Community/js/hoverIntent.js"></script>
<script type="text/javascript" src="/Community/js/superfish.js"></script>
<style type="text/css">
.editor-error {
    color:red;
    font-size:125%;
}
.editor-success {
    color:green;
    font-size:125%;
}
</style>
<script type="text/javascript"> function supports_input_placeholder() { var i = document.createElement('input'); return 'placeholder' in i; } if(!supports_input_placeholder()) { var fields = document.getElementsByTagName('INPUT'); for(var i=0; i < fields.length; i++) { if(fields[i].hasAttribute('placeholder')) { fields[i].defaultValue = fields[i].getAttribute('placeholder'); fields[i].onfocus = function() { if(this.value == this.defaultValue) this.value = ''; }; fields[i].onblur = function() { if(this.value == '') this.value = this.defaultValue; }; } } } </script>
<link href="/Community/css/jquery.ui.theme.css" type="text/css" rel="stylesheet" />
<link href="/Community/css/jquery.ui.datepicker.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.ui-datepicker-next,.ui-datepicker-prev {
	display:none;
}
</style>
<link href="/Community/css/dataTables.css" type="text/css" rel="stylesheet" />
<link href="/Community/css/TableTools_JUI.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="/Community/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/Community/js/ZeroClipboard.js"></script>
<script type="text/javascript" charset="utf-8" src="/Community/js/TableTools.min.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="/Community/images/favicon.ico">
<script type="text/javascript">
$(document).ready( function () {
	var dontSort = [];
	var dontExport=[];
	if (dontSort["table_id"] === undefined) {
		dontSort["table_id"] = [];
		dontExport["table_id"]=[];
	}
	var i=0;
	$('#table_id thead th').each(function () {			
		if ( $(this).hasClass( 'no-sort' )) {
			dontSort["table_id"].push( { "bSortable": false } );
		} else {
			dontSort["table_id"].push( null );
			dontExport["table_id"].push(i);
		}
		i++;
	} );
	var oTable =$('#table_id').dataTable({
		"sDom": 'T<"clear">lfrtip',
		"oTableTools": {},
		"aoColumns": dontSort["table_id"]
	});
});
</script>