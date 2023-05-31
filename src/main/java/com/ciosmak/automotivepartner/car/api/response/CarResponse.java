package com.ciosmak.automotivepartner.car.api.response;

public class CarResponse
{
    private final Long id;
    private final String registrationNumber;
    private final Integer mileage;
    private final Boolean blocked;

    //TODO lombok
    //TODO raczej tylko id zwracamy

    public CarResponse(Long id, String registrationNumber, Integer mileage, Boolean blocked)
    {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.mileage = mileage;
        this.blocked = blocked;
    }

    public Long getId()
    {
        return id;
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
