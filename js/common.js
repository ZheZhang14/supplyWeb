var currentUrl = window.location.href;

// Check if the URL contains a specific string
if (currentUrl.indexOf('sign') !== -1) {
    $("#header-placeholder").load("header.html");
} else {
    $("#header-placeholder").load("header2.html");
}

// Function to calculate MD5 hash of a password
function md5Password(password) {
    return CryptoJS.MD5(password).toString();
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

$(document).ready(function() {
     $(document).on('click', '#logout', function(event) {
        event.preventDefault();
        localStorage.clear();
        window.location.href = 'signin.html';
    });
});

