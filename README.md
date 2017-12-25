# cybersecuritybase-project
Template for the first course project.

## Issue 1: 2013-A3-Cross-Site Scripting (XSS)
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the input field labeled Address: <script>alert('XSS vulnerability!')</script>
4. Press the Submit button
5. The javascript in the xss attack is executed on the confirmation page and an alert message is shown

## Issue 2: 2013-A4-Insecure Direct Object References
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Click the link labeled "List future events" at the bottom of the page
4. Notice that the url in the browsers address field is http://localhost:8080/files/events.txt
5. Change the url in the browsers address field to http://localhost:8080/files/passwords.txt and press enter
6. The secret password information is shown

## Issue 3: 2013-A7-Missing Function Level Access Control
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=admin and Password=admin
3. Notice the text "Admin only, click here to list who has signed up" that is shown in the bottom of the page when the user named "admin" is logged in
4. Click the link in the text and notice that the new page url in the browser address field is http://localhost:8080/list
5. Click the browsers back button to get back to the page at url http://localhost:8080/form
6. Press the Logout button in the top of the page
7. Login with User=ted and Password=ted
8. Notice that the text "Admin only, click here to list who has signed up" is _not_ shown in the bottom of the page when the user named "ted" is logged in
9. Change the url in the browsers address field to http://localhost:8080/list (which we took note of earlier)
10. The page is shown even though the logged in user is not an "admin"

## Issue 4: 2013-A8-Cross-Site Request Forgery (CSRF)
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the input field labeled Address: <script>document.addEventListener("DOMContentLoaded", function() {var form = document.createElement("form");form.setAttribute('method',"post");form.setAttribute('action',"/form");form.setAttribute('id',"csrfForm");var name = document.createElement("input");name.setAttribute('type',"text");name.setAttribute('name',"name");name.setAttribute('value',"CSRF");var address = document.createElement("input");address.setAttribute('type',"text");address.setAttribute('name',"address");address.setAttribute('value',"vulnerability");form.appendChild(name);form.appendChild(address);document.getElementsByTagName('body')[0].appendChild(form);document.getElementById('csrfForm').submit();});</script>
4. Press the Submit button
5. The javascript in the xss attack is executed on the confirmation page which triggers the CSRF
6. Change the url in the browsers address field to http://localhost:8080/list and verify that a user named CSRF with the address vulnerability is signed up

