package cn.vusv.njsutil;

import cn.nukkit.block.BlockID;
import cn.nukkit.entity.mob.EntityMob;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector2;
import cn.nukkit.nbt.tag.CompoundTag;

public class FloatTextEntity extends EntityMob {
    private int survivalTick = 50;
    private int NetWorkId = 61;

    @Deprecated
    public FloatTextEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    public int getSurvivalTick() {
        return survivalTick;
    }

    public void setSurvivalTick(int survivalTick) {
        this.survivalTick = survivalTick;
    }

    @Override
    public int getNetworkId() {
        return this.NetWorkId;
    }


    public float getWidth() {
        return 0.1F;
    }

    public float getLength() {
        return 0.1F;
    }

    public float getHeight() {
        return 0.1F;
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        return false;
    }

    @Override
    public float getGravity() {// 重力
        return 0.04f;
    }

    @Override
    public float getDrag() {
        return 0.02f;
    }

    public void lookAt(Position pos) {
        double xdiff = pos.x - this.x;
        double zdiff = pos.z - this.z;
        double angle = Math.atan2(zdiff, xdiff);
        double yaw = ((angle * 180) / Math.PI) - 90;
        double ydiff = pos.y - this.y;
        Vector2 v = new Vector2(this.x, this.z);
        double dist = v.distance(pos.x, pos.z);
        angle = Math.atan2(dist, ydiff);
        double pitch = ((angle * 180) / Math.PI) - 90;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    @Override
    public boolean onUpdate(int currentTick) {
        if (survivalTick > 0) {
            survivalTick--;
        } else {
            this.close();
        }
        //if (isInsideOfFire() || isInsideOfLava()) {
        //    this.kill();
        //}
        int bid = this.level.getBlockIdAt((int) this.x, (int) this.boundingBox.getMaxY(), (int) this.z, 0);
        if (bid == BlockID.FLOWING_WATER || bid == BlockID.STILL_WATER
                || (bid = this.level.getBlockIdAt((int) this.x, (int) this.boundingBox.getMaxY(), (int) this.z, 1)) == BlockID.FLOWING_WATER
                || bid == BlockID.STILL_WATER
        ) {
            //item is fully in water or in still water
            this.motionY -= this.getGravity() * -0.015;
        } else if (this.isInsideOfWater()) {
            this.motionY = this.getGravity() - 0.06; //item is going up in water, don't let it go back down too fast
        } else {
            this.motionY -= this.getGravity(); //item is not in water
        }
        this.move(this.motionX, this.motionY, this.motionZ);
        double friction = 1 - this.getDrag();
        if (this.onGround && (Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionZ) > 0.00001)) {
            friction *= this.getLevel().getBlock(this.temporalVector.setComponents((int) Math.floor(this.x), (int) Math.floor(this.y - 1), (int) Math.floor(this.z))).getFrictionFactor();
        }
        this.motionX *= friction;
        this.motionY *= 1 - this.getDrag();
        this.motionZ *= friction;
        if (this.onGround) {
            this.motionY *= -0.5;
        }
        this.updateMovement();
        return super.onUpdate(currentTick);
    }

    @Override
    public void close() {
        this.setNameTagVisible(false);
        this.setNameTagAlwaysVisible(false);
        super.close();
    }
}
