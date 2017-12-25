# cybersecuritybase-project
Template for the first course project.
## Issue 1: 2013-A3-Cross-Site Scripting (XSS)
Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the address input field: <script>alert('XSS vulnerability!')</script>ssss
4. Press the Submit button
5. The javascript in the xss attack is executed on the confirmation page and a alert message is shown
