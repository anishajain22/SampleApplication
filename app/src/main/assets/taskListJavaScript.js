window.androidObj = function AndroidClass(){};

class Wrapper{
	constructor(){

	}
	setFunc(key,value){
		Android.setValue(key,value);
		return;
	}
	getFunc(res){
	    //window.setInterval(getFunc,5000);
	    nativeText.innerHTML = res;
	    return;
	}
}
wrapObj = new Wrapper();

var inputContainer = document.createElement("p");
var input = document.createElement("INPUT");
input.setAttribute("type", "text");
var inputText = document.createTextNode("Enter Task Title");
inputContainer.appendChild(inputText);
inputContainer.appendChild(input);

var statusContainer = document.createElement("p");
var inputStatus =document.createElement("INPUT");
inputStatus.setAttribute("type", "text");
var inputStatusText = document.createTextNode("Enter Task Status");
statusContainer.appendChild(inputStatusText);
statusContainer.appendChild(inputStatus);

var buttonContainer = document.createElement("p");
var button = document.createElement("button");
button.innerHTML = "Save";
button.style.width = "150px";
button.style.height = "30px";
button.addEventListener ("click", function() {
  wrapObj.setFunc(input.value,inputStatus.value);
  setEmpty();
});
buttonContainer.appendChild(button);

var textContainer = document.createElement("p");
var nativeText = document.createElement("TEXTAREA");
nativeText.innerHTML="My Tasks";
nativeText.rows = 20;
nativeText.cols = 20;
textContainer.appendChild(nativeText);

document.body.appendChild(inputContainer);
document.body.appendChild(statusContainer);
document.body.appendChild(buttonContainer);
document.body.appendChild(textContainer);


function updateFromApp(message){
    nativeText.nodeValue = message;
}

function setEmpty(){
    input.value="";
    inputStatus.value="";
}

