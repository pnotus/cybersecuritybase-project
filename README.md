# cybersecuritybase-project
Template for the first course project.
## Issue 1: 2013-A3-Cross-Site Scripting (XSS)
Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the address input field: <script>alert('XSS vulnerability!')</script>ssss
4. Press the Submit button
5. The javascript in the xss attack is executed on the confirmation page and an alert message is shown

## Issue 2: 2013-A4-Insecure Direct Object References
Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Click the link labeled "List future events" at the bottom of the page
4. Change the url in the browsers address field from http://localhost:8080/files/events.txt to http://localhost:8080/files/passwords.txt and press enter
5. The secret password information is shown

## Issue 5: 2013-A7-Missing Function Level Access Control
Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=admin and Password=admin
3. Notice the text "Admin only, click here to list who has signed up" in the bottom of the page
4. Click the link in the text

## Issue 4: 2013-A8-Cross-Site Request Forgery (CSRF)
Steps to reproduce:
1. 1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
