package com.ciosmak.automotivepartner.car.domain;

public class Car
{
    private Long id;
    private String registrationNumber;
    private Integer mileage;
    private Boolean blocked;

    public Car(String registrationNumber, Integer mileage, Boolean blocked)
    {
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.blocked = blocked;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRegistrationNumber()
    {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public Boolean getBlocked()
    {
        return blocked;
    }

    public void setBlocked(Boolean blocked)
    {
        this.blocked = blocked;
    }
}
