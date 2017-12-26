# cybersecuritybase-project
Template for the first course project.

## Issue 1: 2013-A3-Cross-Site Scripting (XSS)
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the input field labeled Address: <script>alert('XSS vulnerability!')</script>
4. Press the Submit button
5. The javascript in the XSS attack is executed on the confirmation page and an alert message is shown

### How to fix:
This vulnerability is an example of Reflected Server XSS. Server XSS is caused by including untrusted data in an HTML response. The easiest and strongest defense against Server XSS in most cases is context-sensitive server side output encoding. In this specific case a good solution would be to...

## Issue 2: 2013-A4-Insecure Direct Object References
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Click the link labeled "List future events" at the bottom of the page
4. Notice that the URL in the browsers address field is http://localhost:8080/files/events.txt
5. Change the URL in the browsers address field to http://localhost:8080/files/passwords.txt and press enter
6. The secret password information is shown

### How to fix:

## Issue 3: 2013-A7-Missing Function Level Access Control
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=admin and Password=admin
3. Notice the text "Admin only, click here to list who have signed up" that is shown at the bottom of the page when the user named "admin" is logged in
4. Click the link in the text and notice that the new page URL in the browser address field is http://localhost:8080/list
5. Click the browsers back button to get back to the page at URL http://localhost:8080/form
6. Press the Logout button at the top of the page
7. Login with User=ted and Password=ted
8. Notice that the text "Admin only, click here to list who have signed up" is _not_ shown at the bottom of the page when the user named "ted" is logged in
9. Change the URL in the browsers address field to http://localhost:8080/list (which we took note of earlier)
10. The page is shown even though the logged in user is not an "admin"

### How to fix:

## Issue 4: 2013-A8-Cross-Site Request Forgery (CSRF)
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=ted and Password=ted
3. Enter the following in the input field labeled Address: <script>document.addEventListener("DOMContentLoaded", function() {var form = document.createElement("form");form.setAttribute('method',"post");form.setAttribute('action',"/form");form.setAttribute('id',"csrfForm");var name = document.createElement("input");name.setAttribute('type',"text");name.setAttribute('name',"name");name.setAttribute('value',"CSRF");var address = document.createElement("input");address.setAttribute('type',"text");address.setAttribute('name',"address");address.setAttribute('value',"vulnerability");form.appendChild(name);form.appendChild(address);document.getElementsByTagName('body')[0].appendChild(form);document.getElementById('csrfForm').submit();});</script>
4. Press the Submit button
5. The javascript in the XSS attack is executed on the confirmation page which triggers the CSRF
6. Change the URL in the browsers address field to http://localhost:8080/list and verify that a user named CSRF with the address vulnerability is signed up

### How to fix:

## Issue 5: 2013-A1-SQL Injection
### Steps to reproduce:
1. Go to http://localhost:8080
2. Login with User=admin and Password=admin
3. Click the link in the text "Admin only, click here to list who have signed up" that is shown at the bottom of the page
4. Enter the following in the input field: Roger%' OR address LIKE 'free
5. Press the button labeled "Filter by name starting with"
6. Notice that the SQL injection has filtered the response for both names beginning with "Roger" _and_ addresses beginning with "free"

### How to fix:
