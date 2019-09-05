package gdx.kapotopia.Game1;

import java.util.Random;

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
    public boolean checkCollision(Entity target, Entity projectile) {
        if (target.isCollision(projectile)) {
            /* collision !!! */
            processCollision(target, projectile);
            return true;
        }
        return false;
    }

    /**
     *
     * @param target
     * @param projectile
     */
    public void processCollision(Entity target, Entity projectile) {
        if(target instanceof MireilleBasic) {
            final MireilleBasic mireille = (MireilleBasic) target;
            if(projectile instanceof Virus) {
                final Virus virus = (Virus) projectile;
                if(virus.isIST()) {
                    mireille.increaseScore();
                }else{
                    mireille.decreaseLife();
                    mireille.resetPosition();
                }
                virus.changeVirusType();
            }
        }
    }
}