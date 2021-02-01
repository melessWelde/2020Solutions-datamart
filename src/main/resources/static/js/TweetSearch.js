
$(document).ready(function(){

//$('.datepicker').datepicker({
//    clearBtn: true
//});

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
            

  searchTweets();
});

$("#btnClear").click(function(){
  clearSearch();
});

loadLatestTweets();
});



function loadLatestTweets(){

console.log('loadLatestTweets');

$.ajax({
  url: "/datamart/tweets",
  type: 'GET',
  success: function(data) {
    var dataSet = {
      "data": data
    };
	  console.log(dataSet);
	
	$('#tblTweets').DataTable( {
    "destroy": true,
		data: data,
		searching: false,
         "columns": [
                    { "data": "createdDt","width": "20%" },
          { "data": "userName" },
          { "data": "hashTags" },
          { "data": "tweetText" ,"width": "50%"},
           { "data": "retweetCount","width": "3%" },
           { "data": "favouriteCount","width": "3%" },
					{ 
						"data": "tweetUrl","width": "20%",
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
    , "sType": "date" 
    , "mRender": function(d, type, full) {
      d = new Date(d);      
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      return [month,day, year].join('/');
    }  
  }
  ]
    } );


  }
});

}
function clearSearch(){

console.log('clear');

var table = $('#tblTweets').DataTable();
table.clear().draw();
var username =  $("#txtUserName").val('');
var handlename =  $("#txtHandleName").val('');
var tagtext =  $("#txtHashTag").val('');
var fromdate =$("#txtFrom").val('');
var todate =$("#txtTo").val('');
}

function searchTweets(){

console.log('searchPosts');

var table = $('#tblTweets').DataTable();
table.clear().draw();
var username =  $("#txtUserName").val();
var handlename =  $("#txtHandleName").val();
var tagtext =  $("#txtHashTag").val();
var fromdate =$("#txtFrom").val();
var todate =$("#txtTo").val();
var URL="/datamart/searchtweets?username="+username+"&tagtext="+tagtext+"&fromdate="+fromdate+"&todate="+todate+"&handlename="+handlename;
 console.log(URL);
 
$.ajax({
  url: URL,
  type: 'GET',
  success: function(data) {
 
    var dataSet = {
      "data": data
    };
	  console.log(dataSet);
 

	$('#tblTweets').DataTable( {
    "destroy": true,
    data: data,
		searching: false,
           "columns": [
                    { "data": "createdDt","width": "20%" },
          { "data": "userName" },
          { "data": "hashTags" },
          { "data": "tweetText" ,"width": "50%"},
           { "data": "retweetCount","width": "3%" },
           { "data": "favouriteCount","width": "3%" },
					{ 
						"data": "tweetUrl","width": "20%",
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
    , "sType": "date"
    , "mRender": function(d, type, full) {
      d = new Date(d);      
      month = '' + (d.getMonth() + 1),
      day = '' + d.getDate(),
      year = d.getFullYear();

      if (month.length < 2)
        month = '0' + month;
      if (day.length < 2)
        day = '0' + day;

      return [month,day, year].join('/');
    }  
  }
  ]
    } );

    $("#tblTweets").mark(username);
     $("#tblTweets").mark(tagtext);
  } 
  ,
  error: function (xhr, ajaxOptions, thrownError) {
        
      }

});

}



