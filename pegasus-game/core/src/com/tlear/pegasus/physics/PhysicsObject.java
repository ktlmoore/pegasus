package com.tlear.pegasus.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PhysicsObject {

	/* Linear */
	private Vector2 pos;				// The position of the object in space
	private Vector2 velocity;			// The velocity of the object									v
	private Vector2 acceleration;		// The acceleration of the object								a
	private Vector2 force;				// The amount of force being enacted on the object				F
	private float mass;					// The mass of the object										m
	private Vector2 momentum;			// The linear momentum of the object							p
	private float maxVelocity;			// The maximum velocity of the object				
	
	/* Rotational */
	private float radius;				// The radius of gyration of the object							k
	private float momentOfInertia;		// The moment of inertia of the object							I
	private float torque;				// The amount of torque the object is coming under				T
	private float angularVelocity;		// The angular velocity											omega
	private float angularAcceleration;	// The angular acceleration										alpha
	private float angle;				// The angle that the physis object has relative to the x-axis
	private float maxAngularVelocity;	// The maximum angular velocity of the object
	
	
	
	public PhysicsObject() {
		
		pos = new Vector2(0, 0);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 0);
		force = new Vector2(0, 0);
		mass = 2.0f;
		momentum = new Vector2(0, 0);
		radius = 10;
		momentOfInertia = 0;
		angularVelocity = 0;
		angularAcceleration = 0;
		angle = 0;
		torque = 0;
		
		maxVelocity = 15;
		maxAngularVelocity = 6;
		
		
	}
	
	/* Getters */
	public Vector2 getPos() {
		// Returns the position in space of the object
		return new Vector2(pos);
	}
	public float getAngle() {
		// Returns the angle of the object relative to the x-axis
		return angle;
	}
	public Vector2 getVelocity() {
		// Returns the velocity of the object
		return new Vector2(velocity);
	}
	
	/* Setters */
	public void zeroVelocity() {
		velocity = new Vector2(0, 0);
	}
	public void setVelocity(Vector2 vel) {
		velocity = new Vector2(vel);
	}
	public void addForce(Vector2 f) {
		// Add force to the total force on this object
		force.add(f);
	}
	public void zeroForce() {
		// Zeroes the amount of force on this object
		force = new Vector2(0, 0);
	}
	public void setPos(Vector2 p) {
		// Sets the position of the object
		pos = new Vector2(p);
	}
	public void setMass(float m) {
		mass = m;
	}
	public void setRadius(float r) {
		radius = r;
	}
	public void addTorque(float t) {
		torque += t;
	}
	public void zeroTorque() {
		torque = 0;
	}
	/* Update */
	public void update() {
		// Update acceleration to force / mass	a_t = F_t / m_t
		acceleration = new Vector2(force);
		acceleration.scl(Gdx.graphics.getDeltaTime() / mass);
		System.out.println("ACCELERATION: " + acceleration);
		System.out.println("FORCE: " + force);
		System.out.println("MASS: " + mass);
		// Update velocity by acceleration v_t = v_t-1 + a_t
		velocity.add(acceleration);
		if (velocity.len() > maxVelocity) {
			velocity.sub(acceleration);
		}
		System.out.println("VELOCITY: " + velocity);
		// Update position by velocity (x, y)_t = (x, y)_t-1 + v_t
		pos.add(velocity);
		System.out.println("POS: " + pos);
		// Update momentum to mass * velocity	p_t = m_t * v_t
		momentum = new Vector2(velocity);
		momentum.scl(mass);
		System.out.println("MOMENTUM: " + momentum);
		// Update moment of intertia to m*k*k
		momentOfInertia = mass * radius * radius;
		System.out.println("MOMENT: " + momentOfInertia);
		// Update angular velocity to torque / moment of inertia 	(alpha)_t = T_t / I_t
		angularAcceleration = torque / momentOfInertia;
		System.out.println("TORQUE: " + torque);
		System.out.println("ANGULAR ACCELERATION: " + angularAcceleration);
		// Update angular velocity by angular acceleration	(omega)_t+1 = (omega)_t + (alpha)_t 
		angularVelocity += angularAcceleration;
		if (Math.abs(angularVelocity) > maxAngularVelocity) {
			angularVelocity -= angularAcceleration;
		}
		if (angularAcceleration == 0) {
			angularVelocity *= 0.95f;
		}
		System.out.println("ANGULAR VELOCITY: " + angularVelocity);
		// Update angle by angular velocity	(theta)_t+1 = (theta)_t + (omega)_t+1
		angle += angularVelocity;
		angle %= 360;
		System.out.println("ANGLE: " + angle);
		force = new Vector2();
		torque = 0;
		
	}
	
}
