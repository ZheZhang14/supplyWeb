var currentUrl = window.location.href;

// Check if the URL contains a specific string
if (currentUrl.indexOf('sign') !== -1) {
    $("#header-placeholder").load("header.html?v=1.1");
} else {
    $("#header-placeholder").load("header2.html?v=1.1", function(response, status, xhr) {
        if (status != "error") {
            console.log($("#profileBtn"));
            $("#profileBtn").attr("href", "profile.html?userId=" + localStorage.getItem('userId'));
            $("#profileBtn").text(localStorage.getItem('username'));
        }
    });
}

// Function to calculate MD5 hash of a password
function md5Password(password) {
    return CryptoJS.MD5(password).toString();
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

function getParamValue(k) {
  const queryParams = new URLSearchParams(window.location.search);
  var v = null;
  if (queryParams.size == 0) {
    return v;
  }
  queryParams.forEach((value, key) => {
    if (key == k) {
        v = value;
    }
  });
  return v;
}

$(document).ready(function() {
    $(document).on('click', '#logout', function(event) {
        event.preventDefault();
        localStorage.clear();
        window.location.href = 'signin.html';
    });
});

