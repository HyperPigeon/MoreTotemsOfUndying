package net.hyper_pigeon.moretotems.goals;

import net.hyper_pigeon.moretotems.entity.SummonedZombieEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FollowZombieSummonerGoal extends Goal {

    private final SummonedZombieEntity minion;
    private LivingEntity summoner;
    private final LevelReader world;
    private final double field_6442;
    private final PathNavigation navigation;
    private int field_6443;
    private final float maxDistance;
    private final float minDistance;
    private final boolean field_21078;

    public FollowZombieSummonerGoal(SummonedZombieEntity minion, LivingEntity summoner, LevelReader world, double speed, PathNavigation navigation,  float maxDistance, float minDistance,  boolean field_21078) {
        this.minion = minion;
        this.summoner = summoner;
        this.world = world;
        this.field_6442 = speed;
        this.navigation = navigation;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.field_21078 = field_21078;
    }


    @Override
    public boolean canUse() {

        LivingEntity livingEntity = this.minion.getSummoner();

        if (livingEntity == null) {
            return false;
        }
        else if (livingEntity.isSpectator()) {
            return false;
        }
        else if (this.minion.distanceToSqr(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        }
        else {
            this.summoner = livingEntity;
            return true;
        }

    }


    @Override
    public boolean canContinueToUse() {

        if (this.navigation.isDone()) {
            return false;
        } else {
            return this.minion.distanceToSqr(this.summoner) > (double)(this.maxDistance * this.maxDistance);
        }


    }


    public void tick(){
        this.minion.getLookControl().setLookAt(this.summoner, 10.0F, (float)this.minion.getXRot());
        if (--this.field_6443 <= 0) {
            this.field_6443 = 10;
            if (!this.minion.isPassenger()) {
                if (this.minion.distanceToSqr(this.summoner) >= 144.0D) {
                    this.method_23345();
                } else {
                    this.navigation.moveTo(this.summoner, this.field_6442);
                }

            }
        }



    }


    private void method_23345() {
        BlockPos blockPos = new BlockPos(this.summoner.blockPosition());

        for(int i = 0; i < 10; ++i) {
            int j = this.method_23342(-3, 3);
            int k = this.method_23342(-1, 1);
            int l = this.method_23342(-3, 3);
            boolean bl = this.method_23343(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }

    }


    private boolean method_23343(int i, int j, int k) {
        if (Math.abs((double)i - this.summoner.getX()) < 2.0D && Math.abs((double)k - this.summoner.getZ()) < 2.0D) {
            return false;
        } else if (!this.method_23344(new BlockPos(i, j, k))) {
            return false;
        } else {
            this.navigation.stop();
            return true;
        }
    }


    private boolean method_23344(BlockPos blockPos) {
        PathType pathNodeType = WalkNodeEvaluator.getPathTypeStatic(minion, new BlockPos.MutableBlockPos());
        if (pathNodeType != PathType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = this.world.getBlockState(blockPos.below());
            if (!this.field_21078 && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockPos2 = blockPos.subtract(new BlockPos(this.minion.blockPosition()));
                return this.world.noCollision(this.minion, this.minion.getBoundingBox().move(blockPos2));
            }
        }
    }


    private int method_23342(int i, int j) {
        return this.minion.getRandom().nextInt(j - i + 1) + i;
    }





}
