function showTime()
{
    document.getElementById("currentTime").innerHTML = Date().toString();
}

showTime();
setInterval(function() { showTime(); }, 1000);