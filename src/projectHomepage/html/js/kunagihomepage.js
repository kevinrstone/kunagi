var kunagihp = {};

kunagihp.subscribe = function(baseUrl) {
    var email = prompt('Please enter your email for subscription');
    if (!email) return;
    window.location = baseUrl + email;
}

kunagihp.requestinvoice = function() {
    var amount = $("#amountslider").slider("value");
    var wish = kunagihp.getWish();
    var subject = "Kunagi invoice request";
    text = "Please send me an invoice for a voluntary payment for Kunagi. My address is:\n\n";
    text += "<name or company>\n";
    text += "<address>\n";
    text += "<country>\n\n";
    text += "Amount: " + amount + " EUR\n\n";
    if (wish) text += "I would like you to spend more time on: " + wish;
    var url = "mailto:support@kunagi.org?subject=" + encodeURIComponent(subject);
    url += "&body=" + encodeURIComponent(text);
    window.open(url,'_blank');
    window.location = "download-file.html";
}

kunagihp.paypal = function() {
    var amount = $("#amountslider").slider("value");
    var tax = "0";
    var itemName = "Kunagi";
    var wish = kunagihp.getWish();
    if (wish) itemName += ": " + wish;
    var url = "https://www.paypal.com/cgi-bin/webscr/?cmd=_xclick"
    url += "&business=wi@koczewski.de";
    url += "&currency_code=EUR"
    url += "&amount="+amount;
    url += "&tax="+tax;
    url += "&item_name=" + encodeURIComponent(itemName);
    url += "&no_note=0";
    url += "&no_shipping=1";
    url += "&image_url=" + encodeURIComponent("http://kunagi.org/img/kunagi.png");
    url += "&return=" + encodeURIComponent("http://kunagi.org/download-file.html");
    url += "&cancel_return=" + encodeURIComponent("http://kunagi.org/payment.html");
    window.location = url;
}

kunagihp.getWish = function () {
    var value = $('input[name=wish]:checked', '#wishform').val()
    if (value === 'custom') value = $('#customwish').val();
    return value;
}

$(function() {
    $("#amountslider").slider({
        value: 10,
        min: 3,
        max: 99,
        step: 1,
        slide: function(event, ui) {
            $("#amount").val(ui.value + " EUR");
        }
    });
    $("#amount").val($("#amountslider").slider("value") + " EUR");
});
