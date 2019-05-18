package gdx.kapotopia.Game1;

import java.util.Random;

import gdx.kapotopia.Game4.Mireille;

/**
 * Manage collisions between two entities
 */
public final class CollisionManager {
    private static CollisionManager instance = new CollisionManager();
    private static Random randomGenerator = new Random();

    public static CollisionManager getInstance() {
        return instance;
    }

    private CollisionManager() {
    }

    /**
     * vérifie la collision entre deux artefacts, l'un "cible", l'autre "projectile".
     * Si c'est le cas, le calcul des impacts est lancé, puis la vérification de l'état
     * "alive" du target.
     *
     * @param target
     * @param projectile
     */
    public void checkCollision(Entity target, Entity projectile) {
        if (target.isCollision(projectile)) {
            /* collision !!! */
            processCollision(target, projectile);
        }
    }

    /**
     *
     * @param target
     * @param projectile
     */
    public void processCollision(Entity target, Entity projectile) {
        //projectile.decreaseLife(target.getImpactForce());
        //target.decreaseLife(projectile.getImpactForce());
        target.resetPosition();
        projectile.resetPosition();
        if(target instanceof MireilleBasic) {
            final MireilleBasic mireille = (MireilleBasic) target;
            mireille.decreaseLife();
        }
    }
}