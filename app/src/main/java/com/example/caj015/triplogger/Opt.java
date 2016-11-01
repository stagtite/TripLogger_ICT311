package com.example.caj015.triplogger;


import java.util.UUID;

//Defines setters and getters, and fills the Options with values from the database
public class Opt
{
    //VARIABLES
    private String oName;// = "Cody Johnson";
    private String oId;// = "1077261";
    private String oGender;// = "Male";
    private String oEmail;// = "example@domain.com";
    private String oComment;// = "I can't believe it";
    private final static Integer oIdent = 1;

    //IDENT
    public Integer getoIdent()
    {
        return oIdent;
    }

    //NAME
    public String getoName()
    {
        return oName;
    }

    public void setoName(String name)
    {
        oName = name;
    }

    //ID
    public String getoId()
    {
        return oId;
    }

    public void setoId(String id)
    {
        oId = id;
    }

    //GENDER
    public String getoGender()
    {
        return oGender;
    }

    public void setoGender(String gender)
    {
        oGender = gender;
    }

    //EMAIL
    public String getoEmail()
    {
        return oEmail;
    }

    public void setoEmail(String email)
    {
        oEmail = email;
    }

    //COMMENT
    public String getoComment()
    {
        return oComment;
    }

    public void setoComment(String comment)
    {
        oComment = comment;
    }
}