package swing.shadow.util;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class RippleEffect {

    private final Component component;
    private Color rippleColor = new Color(255, 255, 255);
    private List<Effect> effects;

    public RippleEffect(Component component) {
        this.component = component;
        init();
    }

    private void init() {
        // Initialize the default TimingSource
        SwingTimerTimingSource timingSource = new SwingTimerTimingSource();
        Animator.setDefaultTimingSource(timingSource); // Correct method to set the default timing source
        timingSource.init();

        effects = new ArrayList<>();
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    addEffect(e.getPoint());
                }
            }
        });
    }

    public void addEffect(Point location) {
        effects.add(new Effect(component, location));
    }

    public void render(Graphics g, Shape contain) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Effect effect : effects) {
            effect.render(g2, contain);
        }
    }

    private class Effect {

        private final Component component;
        private final Point location;
        private Animator animator;
        private float animate;

        public Effect(Component component, Point location) {
            this.component = component;
            this.location = location;
            init();
        }

        private void init() {
            animator = new Animator.Builder()
                    .setDuration(500, java.util.concurrent.TimeUnit.MILLISECONDS)
                    .setInterpolator(new org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator(0.5, 0.5))
                    .addTarget(new TimingTargetAdapter() {
                        public void timingEvent(float fraction) {
                            animate = fraction;
                            component.repaint();
                        }

                        public void end() {
                            effects.remove(Effect.this);
                        }
                    })
                    .build();
            animator.start();
        }

        public void render(Graphics2D g2, Shape contain) {
            Area area = new Area(contain);
            area.intersect(new Area(getShape(getSize(contain.getBounds2D()))));
            g2.setColor(rippleColor);
            float alpha = 0.3f;
            if (animate >= 0.7f) {
                double t = animate - 0.7f;
                alpha = (float) (alpha - (alpha * (t / 0.3f)));
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(area);
        }

        private Shape getShape(double size) {
            double s = size * animate;
            double x = location.getX();
            double y = location.getY();
            return new Ellipse2D.Double(x - s, y - s, s * 2, s * 2);
        }

        private double getSize(Rectangle2D rec) {
            double size;
            if (rec.getWidth() > rec.getHeight()) {
                size = (location.getX() < rec.getWidth() / 2)
                        ? rec.getWidth() - location.getX()
                        : location.getX();
            } else {
                size = (location.getY() < rec.getHeight() / 2)
                        ? rec.getHeight() - location.getY()
                        : location.getY();
            }
            return size + (size * 0.1f);
        }
    }

    public void setRippleColor(Color rippleColor) {
        this.rippleColor = rippleColor;
    }

    public Color getRippleColor() {
        return rippleColor;
    }
}