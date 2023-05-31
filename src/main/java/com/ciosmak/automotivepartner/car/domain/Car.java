package com.ciosmak.automotivepartner.car.domain;

public class Car
{
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean blocked;
    private Long id;

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

    public Integer getMileage()
    {
        return mileage;
    }

    public Boolean getBlocked()
    {
        return blocked;
    }
}
