package com.librarytest.librarytest.Services;

public class EmailService {
    public String sendEmail(String to,String title,String body){
        if(!to.isEmpty()){
            return "Send Email Successeful";
        }
        return "Send Email Failed";
    }
}
