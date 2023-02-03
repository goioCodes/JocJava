package joc1;

import java.awt.*;
import java.awt.geom.AffineTransform;

abstract class Ship extends PhysicsObject implements Collider {
    float maxHP;
    float HP;

    float maxSpeed;
    float thrustPower;
    float turningHelp = 0.03f;
    float airResistance;

    private Vec2 cannonOffset;
    private Vec2 cannonPos;
    private Vec2 cannonDir;
    float cannonLength;

    float bulletSpeed;
    float attackSpeed;
    double lastShotTime = -1000;

    Shape shipShape;
    Shape hitbox;

    Vec2 getCannonOffset() {
        return cannonOffset.clone();
    }

    void setCannonOffset(Vec2 cannonOffset) {
        this.cannonOffset = cannonOffset.clone();
    }

    Vec2 getCannonPos() {
        return cannonPos.clone();
    }

    void setCannonPos(Vec2 cannonPos) {
        this.cannonPos = cannonPos.clone();
    }

    Vec2 getCannonDir() {
        return cannonDir.clone();
    }

    void setCannonDir(Vec2 cannonDir) {
        this.cannonDir = cannonDir.clone();
    }

    Ship(Joc j, Vec2 position, float rotation, Vec2 scale, Vec2 speed,
         float maxSpeed, float thrustPower, float airResistance, Direction lookingAt, Vec2 cannonOffset,
         Vec2 cannonDir, float cannonLength, float bulletSpeed, float attackSpeed, Shape shipShape) {
        super(j, position, rotation, scale, speed);

        this.maxSpeed = maxSpeed;
        this.thrustPower = thrustPower;
        this.airResistance = airResistance;

        setCannonOffset(cannonOffset);
        setCannonPos(getPosition().add(cannonOffset));
        setCannonDir(cannonDir);
        this.cannonLength = cannonLength;

        this.bulletSpeed = bulletSpeed;
        this.attackSpeed = attackSpeed;

        this.shipShape = shipShape;
        updateCollider();
    }

    void fixedUpdate() {
        translate(getSpeed().scale((float) Time.deltaTime()));
        applyForce(getSpeed().normalized().scale(- maxSpeed * airResistance).scale((float) Time.deltaTime()));
        cannonPos = getPosition().add(cannonOffset);

        updateCollider();
    }

    void thrust(Vec2 dir) {
        float parallelSpeed = dir.dot(getSpeed());
        float boostedThrust = thrustPower;
        if (parallelSpeed < 0) {
            boostedThrust -= thrustPower*parallelSpeed*turningHelp;
        }
        applyForce(dir.scale(boostedThrust).scale((float) Time.deltaTime()));
        setSpeed(getSpeed().clamp(0, maxSpeed));
    }

    void pointCannonAt(Vec2 pos) {
        Vec2 dir = pos.sub(cannonPos).normalized();
        if (dir.norm2() == 0)
            return;
        cannonDir = dir;
    }

    AffineTransform getCannonModelMatrix() {
        return AffineTransform.getTranslateInstance(cannonOffset.x, cannonOffset.y);
    }

    @Override
    public Shape getCollider() {
        return hitbox;
    }

    @Override
    public void updateCollider() {
        hitbox = getModelMatrix().createTransformedShape(shipShape);
    }

    @Override
    public boolean colliderIsActive() {
        return !destroying;
    }
}
