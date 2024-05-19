<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>EMI</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <div class="container">
  <h1>EMI Calculator</h1><br>
  
  <label for="loanAmount">Loan Amount:</label>
  <input type="text" id="loanAmount"><br>

  <label for="interestRate">Interest Rate:</label>
  <input type="text" id="interestRate"><br>

  <label for="loanTerm">Loan Term(in months):</label>
  <input type="text" id="loanTerm"><br><br>

  <button onclick="calculateEMI()">Calculate EMI</button><br>
  <div id="result"></div>
  </div>
  <script src="app.js"></script>
</body>
</html>
