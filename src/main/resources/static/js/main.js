function init() {
    autoSetDarkMode();
    autoDeleteAlerts();
}

function autoSetDarkMode() {
    ;(function () {
        const htmlElement = document.querySelector("html")
        if (htmlElement.getAttribute("data-bs-theme") === 'auto') {
            function updateTheme() {
                document.querySelector("html").setAttribute("data-bs-theme",
                                                            window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light")
            }

            window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', updateTheme)
            updateTheme()
        }
    })()
}

function autoDeleteAlerts() {
    var alerts = document.querySelectorAll("#alerts .alert:not([data-persist='true'])");

    alerts.forEach(function (alerts) {
        setTimeout(function () {
            alerts.remove();
        }, 5000);
    });
}

function addAlert(code, message, type) {
    var alertBox = document.getElementById("alerts");
    alertBox.innerHTML += '<div class="alert alert-' + type + '"><div class="container"><p class="mb-0">' + message + '</p></div></div>';
    autoDeleteAlerts();
}

function validateFormLab3() {
    var validateInputs  = document.querySelectorAll("input.validate-input");
    for (var i = 0; i < validateInputs.length; i++) {
        if (/[^a-zA-Z0-9\s]/.test(validateInputs[i].value)) {
            alert("Input contains special characters. Please remove them.");
            return false;
        }
    }

    return true;
}

