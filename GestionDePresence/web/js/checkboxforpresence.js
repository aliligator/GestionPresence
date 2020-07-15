function disablebutton() {
        if (document.getElementById("check").checked) {
                document.getElementById("ok").disabled = false;
                alert("checkbox is checked");
        } else {
                document.getElementById("ok").disabled = true;
        }
}