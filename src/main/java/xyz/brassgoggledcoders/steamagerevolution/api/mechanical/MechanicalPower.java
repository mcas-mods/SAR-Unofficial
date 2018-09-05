package xyz.brassgoggledcoders.steamagerevolution.api.mechanical;

public class MechanicalPower implements IMechanicalPower {
    protected int speed;
    protected int torque;

    private int maxSpeed = 1000;
    private int maxTorque = 1000;
    private int maxPower = maxSpeed * maxTorque;

    public MechanicalPower() {

    }

    @Override
    public int getTorque() {
        return torque;
    }

    @Override
    public int getMinTorque() {
        return 0;
    }

    @Override
    public int getMaxTorque() {
        return maxTorque;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getMinSpeed() {
        return 0;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public int getPower() {
        return speed * torque;
    }

    @Override
    public int getMinPower() {
        return 0;
    }

    @Override
    public int getMaxPower() {
        return maxPower;
    }

    @Override
    public void setPower(int torque, int speed) {
        if (torque > this.getMinTorque() && speed > this.getMinSpeed() && torque * speed > this.getPower()) {
            this.torque = torque;
            this.speed = speed;
        }
    }
}
