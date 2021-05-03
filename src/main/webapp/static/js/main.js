function incrementQuantity(id) {
    var name = id.split('-');
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    value++;
    document.getElementById(name[0] + "-quantity-input").value = value;

    var item = document.getElementById(name[0] + "-price").innerHTML.split(" ",1);
    var split = parseFloat(item[0]);
    document.getElementById(name[0] + "-itemValue").innerHTML = (value * split) + " USD";
}

function incrementQuantityWhileInCart(id) {
    var name = id.split('-');
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    value++;
    document.getElementById(name[0] + "-quantity-input").value = value;
}

function decrementQuantity(id) {
    var name = id.split('-');
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    var item = document.getElementById(name[0] + "-price").innerHTML.split(" ",1);
    var split = parseFloat(item[0]);
    if (value <= 1) {
        document.getElementById(name[0] + "-quantity-input").value = 1;
        document.getElementById(name[0] + "-itemValue").innerHTML = (value * split) + " USD";
    } else {
        value--;
        document.getElementById(name[0] + "-quantity-input").value = value;
        document.getElementById(name[0] + "-itemValue").innerHTML = (value * split) + " USD";
    }
}

function decrementQuantityWhileInCart(id) {
    var name = id.split('-');
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    if (value <= 0) {
        document.getElementById(name[0] + "-quantity-input").value = 0;
    } else {
        value--;
        document.getElementById(name[0] + "-quantity-input").value = value;
    }

}

function getQuantity(id) {
    var name = id.split('-');
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    var link = document.getElementById(name[0] + "-add-to-cart-button");
    var newLink = (link.href + "&quantity=" + value)
    link.setAttribute("href", newLink)
}

function deleteFromCart(id) {
    var name = id.split('-');
    document.getElementById(name[0] + "-quantity-input").value = "0";
    var value = parseInt(document.getElementById(name[0] + "-quantity-input").value);
    var link = document.getElementById(name[0] + "-delete-from-cart");
    var newLink = (link.href + "&quantity=" + value);
    link.setAttribute("href", newLink);
}

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.getElementById("logo").style.height = "7rem";
        document.getElementById("logo").style.margin = "auto 39%";
    } else {
        document.getElementById("logo").style.height = "18rem";
        document.getElementById("logo").style.margin = "auto 24%";
    }
}