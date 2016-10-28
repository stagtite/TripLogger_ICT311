package com.example.caj015.triplogger;


import java.util.UUID;

//Defines setters and getters, and fills the Options with values from the database
public class Opt
{
    private UUID optionId;
    private String oName;
    private String oId;
    private String oGender;
    private String oEmail;
    private String oComment;

    public Opt()
    {
        this(UUID.randomUUID());
    }

    public Opt(UUID id)
    {
        optionId = id;
    }

    public UUID getOptSet()
    {
        return optionId;
    }

    public String getoName()
    {
        return oName;
    }

    public void setoName(String name)
    {
        oName = name;
    }

    public String getoId()
    {
        return oId;
    }

    public void setoId(String id)
    {
        oId = id;
    }

    public String getoGender()
    {
        return oGender;
    }

    public void setoGender(String gender)
    {
        oGender = gender;
    }

    public String getoEmail()
    {
        return oEmail;
    }

    public void setoEmail(String email)
    {
        oEmail = email;
    }

    public String getoComment()
    {
        return oComment;
    }

    public void setoComment(String comment)
    {
        oComment = comment;
    }
}