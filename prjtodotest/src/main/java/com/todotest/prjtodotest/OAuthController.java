package com.todotest.prjtodotest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.todotest.prjtodotest.Class.Utils;
import com.todotest.prjtodotest.Class.clsFBToken;
import com.todotest.prjtodotest.Class.clsFBuserProfile;

@RestController
@RequestMapping("/oa")
public class OAuthController {

    @Value("${security.fb.clientId}")
    private String FBclientId;

    
    @Value("${security.fb.secret}")
    private String FBSecret;
    
    @Value("${DBContext.IP}")
    private String DbIp;

    @Value("${DBContext.UserName}")
    private String DBUserName;

    @Value("${DBContext.Password}")
    private String DBPassword;

    @Autowired
    void SetConty(){
        Utils.SetConStr(DbIp, DBUserName, DBPassword);

    }

     @RequestMapping(value = "login", method = RequestMethod.GET)
     public ResponseEntity<Object> test(@RequestParam String code) {
        
       

        String result = "Login Fail !";
        String Id = new String();
        String Name = new String();
        try{
            if(code.length() > 0)
            {
                RestTemplate restTemplate = new RestTemplate();

                //Get bearer token
                String BearerURL = "https://graph.facebook.com/v17.0/oauth/access_token?client_id="+FBclientId+"&redirect_uri=http://localhost:8080/oa/login&client_secret="+FBSecret+"&code="+code;
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

                HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
                ResponseEntity<clsFBToken> respFBbearer = restTemplate.exchange(BearerURL, HttpMethod.GET, entity, clsFBToken.class);
                String FBToken = respFBbearer.getBody().access_token;
                int ExpirySecond =  respFBbearer.getBody().expires_in;
                if(FBToken.length() > 0)
                {
                    String GetProfileURL = "https://graph.facebook.com/v17.0/me?access_token="+FBToken; 
                    
                    HttpHeaders headers2 = new HttpHeaders();
                    headers2.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    headers2.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

                    HttpEntity<String> entity2 = new HttpEntity<>("parameters", headers);
                    ResponseEntity<clsFBuserProfile> respFB = restTemplate.exchange(GetProfileURL, HttpMethod.GET, entity2, clsFBuserProfile.class);
                    
                    Id = respFB.getBody().id;
                    Name = respFB.getBody().name;
                    //insert user & session 
                    int UserId = new SQLConnector().InsUser(Id, 1);
                    if(UserId > 0)
                    {
                        result = "Login success";// , " + Id + " " + Name;   

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                        LocalDateTime ExpiredDate = LocalDateTime.now().plusSeconds(ExpirySecond);  

                        new SQLConnector().InsToken(UserId, code,FBToken, ExpiredDate);
                    }
                    
                    
                }
            }
        }
        catch(Exception e)
        {
            result = "Please login !";
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public boolean CheckToken(int UserId, String BearerToken)
    {
        return new SQLConnector().GetToken(UserId,BearerToken);
    }
}
