
var iPhoneVertical = Array(null,320,"css/responsive/phonevertical.css?"+Date());
var iPhoneHorizontal = Array(321,767,"css/responsive/phonehorizontal.css?"+Date());
var iPad = Array(768,1000,"css/responsive/ipad.css?"+Date());
var dekstop = Array(1001,null,"css/responsive/desktop.css?"+Date());

jQuery(document).ready(function(){
	jQuery(window).bind("resize", resizeWindow);
	function resizeWindow(e){
		var newWindowWidth = jQuery(window).width();
		//alert(newWindowWidth);
		if(newWindowWidth >= dekstop[0]){
			loadCufon();
			if(jQuery("#style-responsive").attr("href") == dekstop[2])return;
			jQuery("#style-responsive").attr({href : dekstop[2]});
			jQuery("#navi").attr("class","navi");
		}else if(newWindowWidth >= iPad[0] && newWindowWidth <= iPad[1]){
			if(jQuery("#style-responsive").attr("href") == iPad[2])return;
			jQuery("#style-responsive").attr({href : iPad[2]});
			jQuery("#navi").attr("class","navi resizednavi");
		}else if(newWindowWidth >= iPhoneHorizontal[0] && newWindowWidth <= iPhoneHorizontal[1]){
			if(jQuery("#style-responsive").attr("href") == iPhoneHorizontal[2])return;
			jQuery("#style-responsive").attr({href : iPhoneHorizontal[2]});
			jQuery("#navi").attr("class","navi resizednavi");
		}else if(newWindowWidth <= iPhoneVertical[1]){
			if(jQuery("#style-responsive").attr("href") == iPhoneVertical[2])return;
			jQuery("#style-responsive").attr({href : iPhoneVertical[2]});
			jQuery("#navi").attr("class","navi resizednavi");
		}
		Cufon.refresh();
		setTimeout(function (){Cufon.refresh();}, 100);
		setTimeout(function (){Cufon.refresh();}, 500);
	}
	resizeWindow();
});