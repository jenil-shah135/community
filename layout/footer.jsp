<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<footer id="footer" class="clearfix">
  <div class="shadow">
    <div class="center">
      <p>&copy; Copyright 2013. All Rights Reserved.</p>
    </div>
  </div>
</footer>
<script src="/Community/js/jquery.ui.core.js"></script>
<script src="/Community/js/jquery.ui.widget.js"></script>
<script src="/Community/js/jquery.ui.datepicker.js"></script>
<script>
    $(function() {
        $( "#datepicker" ).datepicker({
        	changeMonth: true,
            changeYear: true,
        	maxDate: -1 ,
        	showButtonPanel: true,
        	dateFormat:'yy-mm-dd'
        });
    });
</script>