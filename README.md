# To do Task
A Test for verify

Steps :
1. Login to Facebook
2. Call Get FB Auth https://www.facebook.com/v17.0/dialog/oauth?client_id={ClientID}&redirect_uri=http://localhost:8080/oa/login&state={"{st={AnyState},ds={AnyState}}"}
3. List Todo 
- GET http://localhost:8080/task/listtodo/1
4. Create task
- POST http://localhost:8080/task/createtask?uid=1
- post with text plain within the body for the content
5. Mark complete task
- POST http://localhost:8080/task/complete/{TodoID}/{UserId}
- POST http://localhost:8080/task/complete/7/1
6. Remove task
- POST http://localhost:8080/task/remove/{TodoID}/{UserId}
- POST http://localhost:8080/task/remove/7/1
  
