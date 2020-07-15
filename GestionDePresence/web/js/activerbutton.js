function disablebutton() {
        if (document.getElementById("check").checked) {
                document.getElementById("ok").disabled = false;
        } else {
                document.getElementById("ok").disabled = true;
        }
}

