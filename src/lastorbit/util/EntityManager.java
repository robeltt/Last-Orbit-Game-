package lastorbit.util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lastorbit.entities.Entity;


public class EntityManager<T extends Entity> {

    private final List<T> entities;

    public EntityManager() {
        this.entities = new ArrayList<>();
    }

    public void add(T entity) {
        entities.add(entity);
    }

    public void updateAll() {
        Iterator<T> it = entities.iterator();
        while (it.hasNext()) {
            T entity = it.next();
            entity.update();
            if (!entity.isAlive()) {
                it.remove();
            }
        }
    }

    public void renderAll(Graphics2D g) {
        for (T entity : entities) {
            entity.render(g);
        }
    }

    public List<T> getEntities() {
        return entities;
    }

    public int count() {
        return entities.size();
    }
}