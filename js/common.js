var currentUrl = window.location.href;

// Check if the URL contains a specific string
if (currentUrl.indexOf('sign') !== -1) {
    $("#header-placeholder").load("header.html?v=1.1");
} else {
    $("#header-placeholder").load("header2.html?v=1.1", function(response, status, xhr) {
        if (status != "error") {
            console.log($("#profileBtn"));
            $("#profileBtn").attr("href", "profile.html?userId=" + localStorage.getItem('userId'));
            $("#profileBtn").text(localStorage.getItem('name'));
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

// Using numeric.js library for numerical methods
// https://numericjs.com/

function polynomialRegression(data, order) {
  let X = [];
  let y = [];

  for (let i = 0; i < data.length; ++i) {
    let row = [];
    for (let j = 0; j <= order; ++j) {
      row.push(Math.pow(data[i][0], j));
    }
    X.push(row);
    y.push([data[i][1]]);
  }

  let Xt = numeric.transpose(X);
  let XtX = numeric.dot(Xt, X);
  let Xty = numeric.dot(Xt, y);
  console.log(XtX);
  console.log(Xty);

  let coef = numeric.solve(XtX, Xty);
  return coef;
}

// Function to calculate y for a given x using the polynomial coefficients
function evaluatePolynomial(coefficients, x) {
  return coefficients.reduce((sum, coefficient, index) => {
    return sum + coefficient * Math.pow(x, index);
  }, 0);
}

function drawChart(chartData, chartDataPoints, myLineChart) {
  var ctx = $('#myChart')[0].getContext('2d');
  if (myLineChart) {
    myLineChart.destroy();
  }
  myLineChart = new Chart(ctx, {
      type: 'line',
      data: {
          labels: ["June", "July", "August", "September", "October", "November", "December"],
          datasets: [
            // Dataset for the line
            {
              label: 'Forecast',
              data: chartData,
              fill: false,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1,
              borderDash: [10, 5]
            },
            // Dataset for individual points
            {
              label: 'History Completed Orders',
              data: chartDataPoints,
              fill: false,
              borderColor: 'rgb(255, 99, 132)',
              backgroundColor: 'rgb(255, 99, 132)',
              pointRadius: 5,
              showLine: false // No line for this dataset
            }
          ]
      },
      options: {
          scales: {
              y: {
                  beginAtZero: false,
                  suggestedMax: Math.max(Math.max(...chartData), Math.max(...chartDataPoints)) + 2
              }
          }
      }
  });
  return myLineChart;
}

$(document).ready(function() {
    $(document).on('click', '#logout', function(event) {
        event.preventDefault();
        localStorage.clear();
        window.location.href = 'signin.html';
    });
});

