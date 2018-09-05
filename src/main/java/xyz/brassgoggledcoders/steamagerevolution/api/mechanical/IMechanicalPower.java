package xyz.brassgoggledcoders.steamagerevolution.api.mechanical;

public interface IMechanicalPower {
    int getTorque();

    int getMinTorque();

    int getMaxTorque();

    int getSpeed();

    int getMinSpeed();

    int getMaxSpeed();

    int getPower();

    int getMinPower();

    int getMaxPower();

    void setPower(int torque, int speed);
}
