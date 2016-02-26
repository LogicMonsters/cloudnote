
//get the value of cookie

function cookie(name){    

   var cookieArray=document.cookie.split("; "); //get pairs of cookie  

   for (var i=0;i<cookieArray.length;i++){    

      var arr=cookieArray[i].split("=");       //separate name and value    

      if(arr[0]==name)return unescape(arr[1]);   

   } 

   return ""; 

} 



function getCookie(objName){

    var arrStr = document.cookie.split("; ");

    for(var i = 0;i < arrStr.length;i ++){

        var temp = arrStr[i].split("=");

        if(temp[0] == objName) return unescape(temp[1]);

   } 

}

 

function addCookie(objName,objValue,objHours){   

    var str = objName + "=" + escape(objValue);

    if(objHours > 0){                               

        var date = new Date();

        var ms = objHours*3600*1000;

        date.setTime(date.getTime() + ms);

        str += "; expires=" + date.toGMTString();

   }

   document.cookie = str;

}

 

function SetCookie(name,value)

{

    var Days = 30; 

    var exp = new Date();   

    exp.setTime(exp.getTime() + Days*24*60*60*1000);

    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();

}

function getCookie(name) 

{

    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));

     if(arr != null) return unescape(arr[2]); return null;

 

}

function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) {
    	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
    }
}

