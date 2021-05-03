function changePaymentMethod(id) {
    var creditCardImg = document.getElementById("creditcard-img")
    var creditCardForm = document.getElementById("credit-card-form-container")
    var paypalImg = document.getElementById("paypal-img")
    var paypalForm = document.getElementById("paypal-form-container")

    var selectElement = document.getElementById(id)

    if (selectElement.options[selectElement.selectedIndex].text === "Paypal") {
        creditCardImg.style.display = "none"
        creditCardForm.style.display = "none"
        paypalImg.style.display = "block"
        paypalForm.style.display = "block"
    } else if (selectElement.options[selectElement.selectedIndex].text === "Credit Card") {
        creditCardImg.style.display = "block"
        creditCardForm.style.display = "block"
        paypalImg.style.display = "none"
        paypalForm.style.display = "none"
    }


}

function addSpace(id) {
    var value = document.getElementById(id).value;

    if (value.length < 16) {
        if ((value.length+1) % 5 === 0 ) {
            value += " "
        }

    }
    document.getElementById(id).value = value
}

function addSlash(id) {
    var value = document.getElementById(id).value;

    if (value.length === 2) {
        value += "/"
    }
    document.getElementById(id).value = value
}