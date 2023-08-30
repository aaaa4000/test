#Before Start
Please install SQL server and create a database, run script(database, store procedures and LookUpData) or Use the backup provided

# To do Task
A Test for verify

Steps :
1. Login to Facebook
2. Call Get FB Auth https://www.facebook.com/v17.0/dialog/oauth?client_id={ClientID}&redirect_uri=http://localhost:8080/oa/login&state={"{st={AnyState},ds={AnyState}}"}
3. List Todo 
- GET http://localhost:{portid}/task/listtodo/1
4. Create task
- POST http://localhost:{portid}/task/createtask?uid=1
- post with text plain within the body for the content
5. Mark complete task
- POST http://localhost:{portid}/task/complete/{TodoID}/{UserId}
- POST http://localhost:{portid}/task/complete/7/1
6. Remove task
- POST http://localhost:{portid}/task/remove/{TodoID}/{UserId}
- POST http://localhost:{portid}/task/remove/7/1

*In our case the port id is 8080

# How to test
Program consist of Junit test , please execute the test program for several prepare test case like :
- Test login
- Test login fail
- Create task with wrong http method
- Create task
- Complete task
- Remove task
- list task

# How to create docker build
1. Open dockers or any CLI
2. Perform "mvn clean install"
3. Execute docker build by inserting "docker-compose build"
4. Make sure docker is succesfully create by using the command "docker images" , in this case the image name is set to be prjtodotest
5. To run application in docket , "docker-compose up"
6. For docker setting , you may change it in Dockerfile and Docker-compose.yml
