package com.example.demo.dtos;

//esta clase sera la que nos devuelva la informacion con el token y el tipo que tengae ste
public class DtoAuthResponse {
    private String accesToken;
    private String tokenType="Bearer ";

    public DtoAuthResponse(String accesToken) {
        this.accesToken = accesToken;
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    
  
}
