
$(document).ready(function(){

  $( "#txtTo" ).prop( "disabled", true );
  $("#txtFrom").change(function(){
      $( "#txtTo" ).prop( "disabled", false );
      $( "#txtTo" ).focus();
  });
  $("#txtTo").change(function(){
      $( "#txtTo" ).prop( "disabled", false );
     

      var from= new Date($('#txtFrom').val());
      var to= new Date($('#txtTo').val());
            if (from > to) {
            alert('To date should be later than the from date');
            $( "#txtTo" ).focus();
            $( "#txtTo" ).val('');
            }


  });

$("#btnSearch").click(function(){

          if ($('#txtFrom').val() !='' && $('#txtTo').val() =='') {
            alert('To date should not be empty');
             $( "#txtTo" ).focus();
            return;
            }

  searchPosts();
});

loadLatestPosts();
});


function loadLatestPosts(){

console.log('loadLatestPosts');

$.ajax({
  url: "/datamart/allLatestPosts",
  type: 'GET',
  success: function(data) {
    var dataSet = {
      "data": data
    };
	  console.log(dataSet);
	
	$('#tblFBPosts').DataTable( {
    "destroy": true,
		data: data,
		searching: false,
         "columns": [
                    { "data": "createdDate","width": "10%" },
					{ "data": "description" },
					{ "data": "message" },
					{ 
						"data": "link",
						 "render": function(data, type, row, meta){
						if(type === 'display'){
							data = '<a href="' + data + '" target="_blank" >' + 'Open' + '</a>';
						}

            return data;
         } 
				
				
					
				   }
				],
		"aoColumnDefs":[
  
  {
    "aTargets":[0]
    , "sType": "date" // type of the column
    , "mRender": function(d, type, full) {
      d = new Date(d);      
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      return [day, month, year].join('-');
    }  
  }
  ]
    } );


  }
});

}

function searchPosts(){

console.log('searchPosts');

var table = $('#tblFBPosts').DataTable();
table.clear().draw();

var SearchKey =  $("#txtSearchKey").val();
var DateFrom =$("#txtFrom").val();
var DateTo =$("#txtTo").val();
 console.log(SearchKey);
var URL="/datamart/searchPosts?SearchKey="+SearchKey+"&DateFrom="+DateFrom+"&DateTo="+DateTo;
 console.log(URL);
 
$.ajax({
  url: URL,
  type: 'GET',
  success: function(data) {
 
    var dataSet = {
      "data": data
    };
	  console.log(dataSet);
 

	$('#tblFBPosts').DataTable( {
    "destroy": true,
    data: data,
		searching: false,
         "columns": [
                    { "data": "createdDate","width": "10%" },
					{ "data": "description" },
					{ "data": "message" },
					{ 
						"data": "link",
						 "render": function(data, type, row, meta){
						if(type === 'display'){
							data = '<a href="' + data + '" target="_blank" >' + 'Open' + '</a>';
						}

            return data;
         } 
				
				
					
				   }
				],
		"aoColumnDefs":[
  
  {
    "aTargets":[0]
    , "sType": "date" // type of the column
    , "mRender": function(d, type, full) {
      d = new Date(d);      
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      return [day, month, year].join('-');
    }  
  }
  ]
    } );

    $("#tblFBPosts").mark(SearchKey);
  } 
  ,
  error: function (xhr, ajaxOptions, thrownError) {
        
      }

});

}



