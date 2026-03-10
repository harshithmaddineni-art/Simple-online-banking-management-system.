
let chart;

function showLogin(){

document.getElementById("registerPage").classList.add("hidden");
document.getElementById("loginPage").classList.remove("hidden");

}


function showRegister(){

document.getElementById("loginPage").classList.add("hidden");
document.getElementById("registerPage").classList.remove("hidden");

}


function register(){

let username=document.getElementById("regUsername").value;
let password=document.getElementById("regPassword").value;

let account="AC"+Math.floor(100000+Math.random()*900000);

let user={

username:username,
password:password,
account:account,
balance:0,
transactions:[]

};

localStorage.setItem("bankUser",JSON.stringify(user));

alert("Account Created\nAccount Number: "+account);

showLogin();

}


function login(){

let username=document.getElementById("loginUsername").value;
let password=document.getElementById("loginPassword").value;

let user=JSON.parse(localStorage.getItem("bankUser"));

if(user.username===username && user.password===password){

document.getElementById("loginPage").classList.add("hidden");
document.getElementById("dashboard").classList.remove("hidden");

updateDashboard();
drawChart();

}

else{

alert("Invalid Login");

}

}


function logout(){

location.reload();

}



function updateDashboard(){

let user=JSON.parse(localStorage.getItem("bankUser"));

document.getElementById("accountHolder").innerText=user.username;
document.getElementById("accountNumber").innerText=user.account;
document.getElementById("balance").innerText=user.balance;

showTransactions();

}



function showSection(id){

document.getElementById("depositBox").classList.add("hidden");
document.getElementById("withdrawBox").classList.add("hidden");
document.getElementById("transferBox").classList.add("hidden");

document.getElementById(id).classList.remove("hidden");

}



function deposit(){

let amount=Number(document.getElementById("depositAmount").value);

let user=JSON.parse(localStorage.getItem("bankUser"));

user.balance+=amount;

let date=new Date().toLocaleString();

user.transactions.push({type:"Deposit",amount:amount,date:date});

localStorage.setItem("bankUser",JSON.stringify(user));

document.getElementById("depositAmount").value="";

updateDashboard();
drawChart();

}



function withdraw(){

let amount=Number(document.getElementById("withdrawAmount").value);

let user=JSON.parse(localStorage.getItem("bankUser"));

if(amount>user.balance){

alert("Insufficient Balance");
return;

}

user.balance-=amount;

let date=new Date().toLocaleString();

user.transactions.push({type:"Withdraw",amount:amount,date:date});

localStorage.setItem("bankUser",JSON.stringify(user));

document.getElementById("withdrawAmount").value="";

updateDashboard();
drawChart();

}



function transfer(){

let receiver=document.getElementById("transferAccount").value;
let amount=Number(document.getElementById("transferAmount").value);

let user=JSON.parse(localStorage.getItem("bankUser"));

if(amount>user.balance){

alert("Insufficient Balance");
return;

}

user.balance-=amount;

let date=new Date().toLocaleString();

user.transactions.push({

type:"Transfer",
amount:amount,
to:receiver,
date:date

});

localStorage.setItem("bankUser",JSON.stringify(user));

document.getElementById("transferAmount").value="";
document.getElementById("transferAccount").value="";

updateDashboard();
drawChart();

}



function showTransactions(){

let user=JSON.parse(localStorage.getItem("bankUser"));

let history=document.getElementById("history");

history.innerHTML="";

user.transactions.forEach(t=>{

let li=document.createElement("li");

if(t.type==="Deposit")

li.innerHTML="<span style='color:green'>+₹"+t.amount+"</span> | "+t.date;

else if(t.type==="Withdraw")

li.innerHTML="<span style='color:red'>-₹"+t.amount+"</span> | "+t.date;

else

li.innerHTML="<span style='color:orange'>Transfer ₹"+t.amount+"</span> → "+t.to+" | "+t.date;

history.appendChild(li);

});

}



function drawChart(){

let user=JSON.parse(localStorage.getItem("bankUser"));

let deposit=0;
let withdraw=0;
let transfer=0;

user.transactions.forEach(t=>{

if(t.type==="Deposit") deposit+=t.amount;
if(t.type==="Withdraw") withdraw+=t.amount;
if(t.type==="Transfer") transfer+=t.amount;

});

let ctx=document.getElementById("transactionChart");

if(chart){

chart.destroy();

}

chart=new Chart(ctx,{

type:"pie",

data:{

labels:["Deposit","Withdraw","Transfer"],

datasets:[{

data:[deposit,withdraw,transfer],

backgroundColor:["green","red","orange"]

}]

}

});

}

