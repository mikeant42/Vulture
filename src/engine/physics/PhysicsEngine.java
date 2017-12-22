package engine.physics;

import engine.base.Node;
import engine.render.sprite.Sprite;

import java.util.List;

public interface PhysicsEngine {
    void integrate(List<Node> nodes);
}
