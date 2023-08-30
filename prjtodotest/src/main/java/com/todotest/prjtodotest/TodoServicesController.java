package com.todotest.prjtodotest;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.todotest.prjtodotest.Class.Utils;
import com.todotest.prjtodotest.Class.clsFBToken;
import com.todotest.prjtodotest.Class.clsFBuserProfile;
import com.todotest.prjtodotest.Class.clsTodo;
@RestController
@Validated
@RequestMapping("/task")
public class TodoServicesController {  



    @RequestMapping(value = "createtask", method = RequestMethod.POST)
    public ResponseEntity<Object> createTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String Auth,@RequestBody String Todesc, @RequestParam(name = "uid") int UserId ) {
        SQLConnector SQLcon = new SQLConnector();
        String result = new String();

        if(new OAuthController().CheckToken(UserId, Auth) == true)
        {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }

        if(Todesc.isEmpty() || Todesc.length() > 300)
        {
            result = "To do description cannot be empty or more than 300 characters";
        }
        else
        {
            result = SQLcon.CreateTodo(UserId, Todesc, 1 );
        }
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @RequestMapping(value = "complete/{id}/{uid}", method = RequestMethod.POST)
    public ResponseEntity<Object> complete(@RequestHeader(HttpHeaders.AUTHORIZATION) String Auth, @PathVariable int id,@PathVariable int uid) {
        int st = 2;
        String result = new String();
        if(new OAuthController().CheckToken(uid, Auth) == true)
        {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        /*SQLConnector SQLcon = new SQLConnector();
       
        if(Utils.isNumeric(String.valueOf(id)) && Utils.isNumeric(String.valueOf(st)) && st > 0 && st < 3)
        {
            result = SQLcon.SetTodoStatus(id, uid, st);
        }
        else {
            result = "Update Task Fail !";
        }*/
        result = UpdateTask(id, st, uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "remove/{id}/{uid}", method = RequestMethod.POST)
    public ResponseEntity<Object> remove(@RequestHeader(HttpHeaders.AUTHORIZATION) String Auth, @PathVariable int id,@PathVariable int uid) {
        int st = 3;
        String result = new String();
        if(new OAuthController().CheckToken(uid, Auth) == true)
        {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        result = UpdateTask(id, st, uid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    private String UpdateTask(int id, int st, int uid)
    {
        String result = new String();
        try{
            SQLConnector SQLcon = new SQLConnector();
        
            if(Utils.isNumeric(String.valueOf(id)) && Utils.isNumeric(String.valueOf(st)) && st > 0 && st < 4)
            {
                result = SQLcon.SetTodoStatus(id, uid, st);
            }
            else {
                result = "Update Task Fail !";
            }        

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            result = " E 125 " + ex.getMessage();
        }
        return result;

    }


    @RequestMapping(value = "listtodo/{uid}", method = RequestMethod.GET)
    public ResponseEntity<Object> listTodo(@RequestHeader(HttpHeaders.AUTHORIZATION) String Auth,@PathVariable int uid ) {
        SQLConnector SQLcon = new SQLConnector();
        if(new OAuthController().CheckToken(uid, Auth) == true)
        {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }

        List<clsTodo> lstTodo = SQLcon.GetTodo(uid);    
        return new ResponseEntity<>(lstTodo, HttpStatus.OK);

    }
 @RequestMapping(value = "testrun", method = RequestMethod.GET)
    public ResponseEntity<Object> testrun() {
       
        return new ResponseEntity<>("", HttpStatus.OK);

    }

}
