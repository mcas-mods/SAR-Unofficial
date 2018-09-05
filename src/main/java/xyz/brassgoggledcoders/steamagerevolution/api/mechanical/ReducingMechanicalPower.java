package xyz.brassgoggledcoders.steamagerevolution.api.mechanical;

public class ReducingMechanicalPower extends MechanicalPower {
    private int tickingRate;
    private int tickDownAmount;
    private int lastTickTime;

    public ReducingMechanicalPower() {
        this(5, 20);
    }

    public ReducingMechanicalPower(int tickingRate, int tickDownAmount) {
        this.tickingRate = tickingRate;
        this.tickDownAmount = tickDownAmount;
    }

    public void tick() {
        lastTickTime++;
        if (lastTickTime >= tickingRate) {
            torque -= tickDownAmount;
            if (torque < 0) {
                torque = 0;
            }
            speed -= tickDownAmount;
            if (speed < 0) {
                speed = 0;
            }
        }
    }

    @Override
    public void setPower(int torque, int speed) {
        super.setPower(torque, speed);
        lastTickTime = 0;
    }
}
